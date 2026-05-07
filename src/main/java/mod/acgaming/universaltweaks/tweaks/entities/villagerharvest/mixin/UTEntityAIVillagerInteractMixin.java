package mod.acgaming.universaltweaks.tweaks.entities.villagerharvest.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;

import mod.acgaming.universaltweaks.tweaks.entities.villagerharvest.UTVillagerHarvestUtils;

import net.minecraft.entity.ai.EntityAIVillagerInteract;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityAIVillagerInteract.class)
public class UTEntityAIVillagerInteractMixin
{
    @Shadow EntityVillager villager;

    /**
     * @author Invadermonky
     * @reason Allows villagers to drop modded {@link ItemSeedFood} when other nearby villagers want
     *          additional food. This logic is usually used for villager breeding.
     */
    @Inject(
        method = "updateTask",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/item/ItemStack;isEmpty()Z"
        )
    )
    private void utDropModdedSeedFood(CallbackInfo ci, @Local int index, @Local(ordinal = 0)LocalRef<ItemStack> slotStackRef, @Local(ordinal = 1) LocalRef<ItemStack> dropStackRef)
    {
        ItemStack slotStack = slotStackRef.get();
        if(!slotStack.isEmpty() && UTVillagerHarvestUtils.isWhiteListedFood(slotStack) && slotStack.getCount() > 3)
        {
            int toDrop = slotStack.getCount() / 2;
            dropStackRef.set(slotStack.splitStack(toDrop));
            if(slotStack.isEmpty()) {
                this.villager.getVillagerInventory().setInventorySlotContents(index, ItemStack.EMPTY);
            }
            //Setting the ItemStack to EMPTY to bypass vanilla logic in the case of overwrites.
            slotStackRef.set(ItemStack.EMPTY);
        }
    }
}
