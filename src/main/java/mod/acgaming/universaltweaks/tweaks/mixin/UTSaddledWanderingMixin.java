package mod.acgaming.universaltweaks.tweaks.mixin;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.passive.AbstractHorse;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityAIWander.class)
public class UTSaddledWanderingMixin
{
    @Shadow
    @Final
    protected EntityCreature entity;

    @Inject(method = "shouldExecute", at = @At("HEAD"), cancellable = true)
    public void utSaddledWandering(CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfig.tweaks.utSaddledWanderingToggle) return;
        if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTSaddledWandering ::: Should execute check");
        if (entity instanceof AbstractHorse && ((AbstractHorse) entity).isHorseSaddled()) cir.setReturnValue(false);
    }
}