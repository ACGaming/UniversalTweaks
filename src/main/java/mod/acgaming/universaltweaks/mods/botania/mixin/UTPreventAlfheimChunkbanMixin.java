package mod.acgaming.universaltweaks.mods.botania.mixin;

import mod.acgaming.universaltweaks.config.UTConfigMods;
import mod.acgaming.universaltweaks.util.UTAggregatedListProxy;
import vazkii.botania.common.Botania;
import vazkii.botania.api.recipe.RecipeElvenTrade;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.ILexicon;
import vazkii.botania.common.block.tile.TileAlfPortal;
import vazkii.botania.common.block.tile.TileMod;
import vazkii.botania.common.item.ItemLexicon;
import vazkii.botania.common.lexicon.LexiconData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.oredict.OreDictionary;
import com.google.common.collect.Multiset;
import com.google.common.collect.HashMultiset;

import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import javax.annotation.Nullable;

@Mixin(value = TileAlfPortal.class, remap = false)
public abstract class UTPreventAlfheimChunkbanMixin extends TileMod {
    @Shadow
    protected abstract void spawnItem(ItemStack stack);

    @Shadow
    protected abstract boolean consumeMana(@Nullable List<BlockPos> pylons, int totalCost, boolean close);

    @Shadow
    @Final
    @Mutable
    private List<ItemStack> stacksIn;

    @Unique
    private final UTAggregatedListProxy aggregatedStacks = new UTAggregatedListProxy();

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onCtor(CallbackInfo ci) {
        if (UTConfigMods.BOTANIA.utAlfheimPortalNBTFix) this.stacksIn = aggregatedStacks;
    }

    @Inject(method = "addItem", at = @At("HEAD"), cancellable = true)
	private void addItem(ItemStack stack, CallbackInfo ci) {
        if (!UTConfigMods.BOTANIA.utAlfheimPortalNBTFix) return;

        aggregatedStacks.add(stack);
        ci.cancel();
	}

    private Pair<Multiset<ItemStack>, Multiset<String>> aggregateRecipe(RecipeElvenTrade recipe) {
        Multiset<ItemStack> items = HashMultiset.create();
        Multiset<String> oredicts = HashMultiset.create();

        for (Object input : recipe.getInputs()) {
            if (input instanceof ItemStack) {
                items.add(((ItemStack) input).copy());
            } else if (input instanceof String) {
                oredicts.add((String) input);
            }
        }

        return Pair.of(items, oredicts);
    }

    private void consumeRecipe(Pair<Multiset<ItemStack>, Multiset<String>> recipe) {
        Multiset<ItemStack> requiredItems = recipe.getLeft();
        Multiset<String> requiredOredicts = recipe.getRight();

        // remove required items
        for (Multiset.Entry<ItemStack> entry : requiredItems.entrySet()) {
            ItemStack reqStack = entry.getElement();
            int reqCount = entry.getCount();

            // remove should be safe here since we already validated availability
            int previousCount = aggregatedStacks.removeCount(reqStack, reqCount);
            if (previousCount < reqCount) {
                Botania.LOGGER.warn("Internal state failure: less than required ItemStacks available when consuming Elven Trade recipe: " + recipe.toString());
                return;
            }
        }

        // remove required oredicts
        for (Multiset.Entry<String> entry : requiredOredicts.entrySet()) {
            String reqOredict = entry.getElement();
            int remaining = entry.getCount();

            List<ItemStack> validStacks = OreDictionary.getOres(reqOredict);
            for (ItemStack validStack : validStacks) {
                int removed = aggregatedStacks.removeCount(validStack, remaining);
                remaining -= removed;
                if (remaining <= 0) break;
            }

            if (0 < remaining) {
                Botania.LOGGER.warn("Internal state failure: less than required Oredict ItemStacks available when consuming Elven Trade recipe: " + recipe.toString());
                return;
            }
        }
    }

	public boolean matchesRecipe(Pair<Multiset<ItemStack>, Multiset<String>> recipe) {
        Multiset<ItemStack> requiredItems = recipe.getLeft();
        Multiset<String> requiredOredicts = recipe.getRight();

        // check required items
        for (Multiset.Entry<ItemStack> entry : requiredItems.entrySet()) {
            ItemStack reqStack = entry.getElement();
            int needed = entry.getCount();

            int available = aggregatedStacks.getCount(reqStack);
            if (available < needed) return false;
        }

        // check required oredicts
        for (Multiset.Entry<String> entry : requiredOredicts.entrySet()) {
            String reqOredict = entry.getElement();
            int needed = entry.getCount();

            List<ItemStack> validStacks = OreDictionary.getOres(reqOredict);

            int available = 0;
            for (ItemStack validStack : validStacks) available += aggregatedStacks.getCount(validStack);
            if (available < needed) return false;
        }

		return true;
	}

    @Inject(method = "resolveRecipes", at = @At("HEAD"), cancellable = true)
    private void resolveRecipes(CallbackInfo ci) {
        if (!UTConfigMods.BOTANIA.utAlfheimPortalNBTFix) return;

        // first, unlock lexicon knowledge if possible
        for (Multiset.Entry<ItemStack> entry : aggregatedStacks.getPairs()) {
            ItemStack itemStack = entry.getElement();
			if(!itemStack.isEmpty() && itemStack.getItem() instanceof ILexicon) {
				ILexicon lexicon = (ILexicon) itemStack.getItem();
				if (!lexicon.isKnowledgeUnlocked(itemStack, BotaniaAPI.elvenKnowledge)) {
					lexicon.unlockKnowledge(itemStack, BotaniaAPI.elvenKnowledge);
					ItemLexicon.setForcedPage(itemStack, LexiconData.elvenMessage.getUnlocalizedName());
                    spawnItem(itemStack.copy());

                    aggregatedStacks.removeCount(itemStack, 1);
					return;
				}
			}
        }

        // next, try to match elven trade recipes
        for (RecipeElvenTrade recipe : BotaniaAPI.elvenTradeRecipes) {
            Pair<Multiset<ItemStack>, Multiset<String>> aggregatedRecipe = aggregateRecipe(recipe);

            if (matchesRecipe(aggregatedRecipe)) {
                if (consumeMana(null, 500, false)) {
                    consumeRecipe(aggregatedRecipe);
                    for (ItemStack output : recipe.getOutputs()) spawnItem(output.copy());
				}

				break;
			}
		}

        ci.cancel();
	}

    // Replace writeToNBT to aggregate identical ItemStacks to reduce NBT size and prevent chunkbans
    @Inject(method = "writeToNBT", at = @At("HEAD"), cancellable = true, remap = true)
    private void writeToNBTAggregated(NBTTagCompound cmp, CallbackInfoReturnable<NBTTagCompound> cir) {
        if (!UTConfigMods.BOTANIA.utAlfheimPortalNBTFix) return;

        NBTTagCompound ret = super.writeToNBT(cmp);

        int j = 0;
        for (Multiset.Entry<ItemStack> pair : aggregatedStacks.getPairs()) {
            ret.setTag("stacksInSet" + j, pair.getElement().writeToNBT(new NBTTagCompound()));
            ret.setInteger("stacksInSet" + j + "Count", pair.getCount());
            j++;
        }
        ret.setInteger("stacksInSetCount", j);

        cir.setReturnValue(ret);
    }

    // Replace readFromNBT to handle aggregated stacks, while maintaining backwards compatibility
    @Inject(method = "readFromNBT", at = @At("HEAD"), cancellable = true, remap = true)
    private void readFromNBTAggregated(NBTTagCompound cmp, CallbackInfo ci) {
        if (!UTConfigMods.BOTANIA.utAlfheimPortalNBTFix) return;

        aggregatedStacks.clear();

        super.readFromNBT(cmp);

        // New tag with aggregated stacks
        int setCount = cmp.getInteger("stacksInSetCount");
        if (setCount > 0) {
            for (int i = 0; i < setCount; i++) {
                ItemStack stack = new ItemStack(cmp.getCompoundTag("stacksInSet" + i));
                int addCount = cmp.getInteger("stacksInSet" + i + "Count");
                aggregatedStacks.addCount(stack, addCount);
            }
        }

        // Old tag for backwards compatibility
        int oldCount = cmp.getInteger("stackCount");
        if (oldCount > 0) {
            for (int i = 0; i < oldCount; i++) {
                NBTTagCompound stackcmp = cmp.getCompoundTag("portalStack" + i);
                ItemStack stack = new ItemStack(stackcmp);
                aggregatedStacks.addCount(stack, 1);
            }
        }

        ci.cancel();
    }
}
