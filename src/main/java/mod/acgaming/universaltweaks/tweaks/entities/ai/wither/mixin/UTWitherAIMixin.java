package mod.acgaming.universaltweaks.tweaks.entities.ai.wither.mixin;

import net.minecraft.entity.boss.EntityWither;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import zone.rong.mixinextras.injector.WrapWithCondition;

@Mixin(EntityWither.class)
public class UTWitherAIMixin
{
    @WrapWithCondition(method = "updateAITasks", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/boss/EntityWither;updateWatchedTargetId(II)V", ordinal = 3))
    public boolean utWitherAI(EntityWither instance, int targetOffset, int newId)
    {
        return !UTConfig.TWEAKS_ENTITIES.utWitherAIToggle;
    }
}