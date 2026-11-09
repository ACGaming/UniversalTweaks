package mod.acgaming.universaltweaks.bugfixes.world.endpodium.mixin;

import mod.acgaming.universaltweaks.config.UTConfigBugfixes;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.management.PlayerList;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// MC-6431
// https://bugs.mojang.com/browse/MC/issues/MC-6431
@Mixin(PlayerList.class)
public abstract class UTEndPodiumMixin
{
    @Inject(method = "recreatePlayerEntity", at = @At("RETURN"))
    private void ce$recreatePlayerEntity(EntityPlayerMP player, int dimension, boolean conqueredEnd, CallbackInfoReturnable<EntityPlayerMP> cir) {
        if (UTConfigBugfixes.WORLD.utEndPodiumEffectsFix || !conqueredEnd) {
            return;
        }

        EntityPlayerMP newPlayer = cir.getReturnValue();
        for (PotionEffect effect : player.getActivePotionEffects()) {
            newPlayer.addPotionEffect(new PotionEffect(effect));
        }
    }
}
