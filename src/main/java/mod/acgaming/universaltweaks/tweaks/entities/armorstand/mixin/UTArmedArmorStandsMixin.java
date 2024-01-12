package mod.acgaming.universaltweaks.tweaks.entities.armorstand.mixin;

import net.minecraft.entity.item.EntityArmorStand;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityArmorStand.class)
public abstract class UTArmedArmorStandsMixin
{
    @Inject(method = "getShowArms", at = @At("HEAD"))
    public void utArmedArmorStands(CallbackInfoReturnable<Boolean> cir)
    {
        if (UTConfigTweaks.ENTITIES.utArmedArmorStandsToggle) this.setShowArms(true);
    }

    @Shadow
    protected abstract void setShowArms(boolean showArms);
}