package mod.acgaming.universaltweaks.tweaks.entities.villagerharvest.mixin;

import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;

import mod.acgaming.universaltweaks.tweaks.entities.villagerharvest.UTVillagerHarvestUtils;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemSeeds;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityVillager.class)
public abstract class UTEntityVillagerMixin extends EntityAgeable
{
    @Shadow public InventoryBasic villagerInventory;

    public UTEntityVillagerMixin(World worldIn)
    {
        super(worldIn);
    }

    /**
     * @author Invadermonky
     * @reason Allows villagers to mate with modded food items. This method extends the
     *          previously hardcoded checks for potatoes and carrots.
     */
    @Inject(
        method = "getIsWillingToMate",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/item/ItemStack;isEmpty()Z"
        )
    )
    private void utMateWithModdedFood(boolean updateFirst, CallbackInfoReturnable<Boolean> cir,
                                      @Local(ordinal = 1) LocalBooleanRef booleanRef,
                                      @Local int index,
                                      @Local LocalRef<ItemStack> stackRef)
    {
        int requiredCount = UTVillagerHarvestUtils.getRequiredFoodCount(stackRef.get());
        if(requiredCount > 0) {
            booleanRef.set(true);
            this.villagerInventory.decrStackSize(index, requiredCount);
            //Setting the ItemStack to EMPTY to bypass vanilla logic in the case of overwrites.
            stackRef.set(ItemStack.EMPTY);
        }
    }

    /**
     * @author Invadermonky
     * @reason Allows villagers to pick up modded items that can either be replanted or used
     *          as food for breeding.
     */
    @ModifyReturnValue(method = "canVillagerPickupItem", at = @At("RETURN"))
    private boolean utPickupModdedItems(boolean original, @Local(argsOnly = true)Item itemIn)
    {
        return original || UTVillagerHarvestUtils.isSeedItem(itemIn) || UTVillagerHarvestUtils.isWhiteListedFood(itemIn);
    }

    /**
     * @author Invadermonky
     * @reason Expands the hardcoded food item count check to include configured items. This
     *          check is generally used when determining if a villager can breed using items
     *          in its inventory.
     */
    @Inject(
        method = "hasEnoughItems",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/item/ItemStack;isEmpty()Z"
        ),
        cancellable = true
    )
    private void utHasEnoughFoodItems(int multiplier, CallbackInfoReturnable<Boolean> cir, @Local ItemStack stack)
    {
        if(!stack.isEmpty())
        {
            int requiredAmount = UTVillagerHarvestUtils.getRequiredFoodCount(stack) * multiplier;
            if (requiredAmount > 0 && stack.getCount() >= requiredAmount)
            {
                cir.setReturnValue(true);
                cir.cancel();
            }
        }
    }

    /**
     * @author Invadermonky
     * @reason Expands the hardcoded seed check to include modded items that extend the {@link ItemSeeds} or
     *          {@link ItemSeedFood} classes when determining if a villager can replant a crop during the
     *          {@link net.minecraft.entity.ai.EntityAIHarvestFarmland} update.
     */
    @Inject(
        method = "isFarmItemInInventory",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/item/ItemStack;isEmpty()Z"
        ),
        cancellable = true
    )
    private void utIsSeedItemInInventory(CallbackInfoReturnable<Boolean> cir, @Local ItemStack stack)
    {
        if(UTVillagerHarvestUtils.isSeedItem(stack)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
