package mod.acgaming.universaltweaks.mods.electroblobswizardry.mixin;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import c4.conarm.client.models.ModelConstructsArmor;
import c4.conarm.lib.tinkering.TinkersArmor;
import mod.acgaming.universaltweaks.mods.electroblobswizardry.FixedConstructsArmoryModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TinkersArmor.class)
public class UTTinkersArmorMixin
{
    @Shadow(remap = false)
    protected ModelBiped model;

    @Inject(method = "getArmorModel", at = @At("HEAD"), remap = false)
    private void utGetArmorModel(EntityLivingBase entity, ItemStack stack, EntityEquipmentSlot slot, ModelBiped _default, CallbackInfoReturnable<ModelBiped> cir)
    {
        if (this.model == null || this.model.getClass() == ModelConstructsArmor.class)
        {
            this.model = new FixedConstructsArmoryModel(slot);
        }
    }
}
