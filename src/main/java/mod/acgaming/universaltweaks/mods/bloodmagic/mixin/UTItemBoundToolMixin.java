package mod.acgaming.universaltweaks.mods.bloodmagic.mixin;

import WayofTime.bloodmagic.item.ItemBoundTool;
import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.items.ItemHandlerHelper;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = ItemBoundTool.class, remap = false)
public class UTItemBoundToolMixin
{
    @Shadow(remap = true)
    public abstract float getDestroySpeed(ItemStack stack, IBlockState state);

    @Unique
    private NonNullList<ItemStack> harvestedStacks = NonNullList.create();

    /**
     * @author Invadermonky
     * @reason The default implementation of this method causes significant lag when harvesting large numbers of blocks.
     *
     * <p>
     *     The default implementation of this event is very performance unfriendly. When it is called by the individual tools,
     *     it will iterate over this event equal to a number of blocks based on the charge time. At its maximum, this can result
     *     in harvesting upwards of 1,330 blocks at once.
     * </p>
     *
     * <p>
     *     While the harvesting itself isn't really an issue, the problem is that the default {@link ItemBoundTool#sharedHarvest(ItemStack, World, EntityPlayer, BlockPos, IBlockState, boolean, int)}
     *     method attempts to insert every one of these items into the player's inventory individually, which can result
     *     in thousands of attempts to insert items into a player's inventory based on the blocks harvested. This causes
     *     a significant lag spike whenever this method fires, often freezing the game for a second or two.
     * </p>
     *
     * <p>This rewrite does two things differently:</p>
     *
     * <o1>
     *     <li>It fires the BlockHarvestEvent granting block tweaker mods access to the harvested materials</li>
     *     <li>It stores all drops in an external list, merges the similar items, and only then inserts them into the player's inventory.</li>
     * </o1>
     */
    @Overwrite
    protected void sharedHarvest(ItemStack stack, World world, EntityPlayer player, BlockPos pos, IBlockState state, boolean silkTouch, int fortune) {
        if(state.getBlockHardness(world, pos) != -1.0f) {
            float destroySpeed = this.getDestroySpeed(stack, state);
            if(destroySpeed > 1.1f && world.canMineBlockBody(player, pos)) {
                if(!player.isCreative()) {
                    NonNullList<ItemStack> drops = NonNullList.create();
                    if(silkTouch && state.getBlock().canSilkHarvest(world, pos, world.getBlockState(pos), player)) {
                        ItemStack silkDrop = state.getBlock().getSilkTouchDrop(state);
                        if(!silkDrop.isEmpty()) {
                            drops.add(silkDrop);
                        }
                    } else {
                        state.getBlock().getDrops(drops, world, pos, world.getBlockState(pos), fortune);
                    }
                    float chance = ForgeEventFactory.fireBlockHarvesting(drops, world, pos, state, fortune, 1.0f, silkTouch, player);
                    drops.removeIf(drop -> drop.isEmpty() || chance < world.rand.nextFloat());
                    this.harvestedStacks.addAll(drops);
                }

                state.getBlock().removedByPlayer(world.getBlockState(pos), world, pos, player, false);
            }
        }
    }

    /**
     * @author Invadermonky
     * @reason Handles player inventory insertion after the shared harvest loop fires. This was previously handled by the
     * {@link ItemBoundTool#sharedHarvest(ItemStack, World, EntityPlayer, BlockPos, IBlockState, boolean, int)} method.
     */
    @Inject(
        method = "onPlayerStoppedUsing",
        at = @At(
            value = "INVOKE",
            target = "LWayofTime/bloodmagic/item/ItemBoundTool;onBoundRelease(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;I)V",
            shift = At.Shift.AFTER
        ),
        remap = true
    )
    private void givePlayerHarvestedBlocks(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft, CallbackInfo ci, @Local EntityPlayer player) {
        List<ItemStack> mergedStacks = new ArrayList<>();

        this.harvestedStacks.stream().filter(drop -> !drop.isEmpty())
            .forEach(drop -> {
                for (ItemStack merged : mergedStacks) {
                    if (ItemHandlerHelper.canItemStacksStack(drop, merged))
                        merged.grow(drop.splitStack(drop.getCount()).getCount());

                    if(drop.isEmpty())
                        break;
                }

                if(!drop.isEmpty())
                    mergedStacks.add(drop.copy());
            });

        mergedStacks.forEach(mergedDrop -> ItemHandlerHelper.giveItemToPlayer(player, mergedDrop));
        this.harvestedStacks.clear();
    }
}
