package mod.acgaming.universaltweaks.tweaks.misc.armorcurve.mixin;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.DamageSource;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of Jackiecrazy
@Mixin(EntityLivingBase.class)
public abstract class UTValueUpdateMixin
{
    @Shadow
    public abstract IAttributeInstance getEntityAttribute(IAttribute attribute);

    @Inject(at = @At("HEAD"), method = "applyArmorCalculations")
    protected void utApplyArmorCalculations(DamageSource source, float damage, CallbackInfoReturnable<Float> cir)
    {
        if (!source.isUnblockable()) ((UTAttributeUpdater) (this.getEntityAttribute(SharedMonsterAttributes.ARMOR))).invokeFlagForUpdate();
    }
}