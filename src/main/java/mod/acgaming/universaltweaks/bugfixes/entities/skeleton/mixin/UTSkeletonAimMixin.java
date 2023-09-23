package mod.acgaming.universaltweaks.bugfixes.entities.skeleton.mixin;

import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIAttackRangedBow;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityMob;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// MC-121706
// https://bugs.mojang.com/browse/MC-121706
@Mixin(EntityAIAttackRangedBow.class)
public abstract class UTSkeletonAimMixin<T extends EntityMob & IRangedAttackMob> extends EntityAIBase
{
    @Shadow
    @Final
    private T entity;

    @SuppressWarnings("ConstantConditions")
    @Inject(method = "updateTask", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/EntityLookHelper;setLookPositionWithEntity(Lnet/minecraft/entity/Entity;FF)V", shift = At.Shift.AFTER))
    public void utLookAtTarget(CallbackInfo ci)
    {
        if (!UTConfigBugfixes.ENTITIES.utSkeletonAimToggle) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTSkeletonAim ::: Look at target");
        this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 30.0F, 30.0F);
    }
}