package mod.acgaming.universaltweaks.tweaks.entities.unsafesleeping.mixin;

import java.util.List;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayer.class)
public class UTUnsafeSleepingMixin
{
    /**
     * @reason apply the glowing potion effect to mobs making sleeping unsafe
     * @author WaitingIdly
     */
    @Inject(method = "trySleep", at = @At(value = "RETURN", ordinal = 5))
    private void utHighlightMobs(BlockPos bedLocation, CallbackInfoReturnable<EntityPlayer.SleepResult> cir, @Local List<EntityMob> list)
    {
        if (!UTConfigTweaks.ENTITIES.UNSAFE_SLEEPING.utApplyGlowingToUnsafeMobs) return;
        final int duration = UTConfigTweaks.ENTITIES.UNSAFE_SLEEPING.utGlowingTime;
        final boolean showParticles = UTConfigTweaks.ENTITIES.UNSAFE_SLEEPING.utShowGlowingParticles;
        list.forEach(mob -> mob.addPotionEffect(new PotionEffect(MobEffects.GLOWING, duration, 0, false, showParticles)));
    }

    /**
     * @reason allow sleeping if either the list is empty or the config to allow unsafe sleeping is set
     * @author WaitingIdly
     */
    @ModifyExpressionValue(method = "trySleep", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z"))
    private boolean utCheckUnsafeSleeping(boolean original)
    {
        return original || UTConfigTweaks.ENTITIES.UNSAFE_SLEEPING.utAllowUnsafeSleeping;
    }
}