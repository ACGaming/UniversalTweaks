package mod.acgaming.hkntweaks.bugfixes.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// MC-12829
// https://bugs.mojang.com/browse/MC-12829
@Mixin(EntityPlayer.class)
public class HkNLadderFlyingPlayerMixin extends HkNLadderFlyingMixin
{
    @Shadow
    public PlayerCapabilities capabilities;

    @Override
    public boolean hknIsNotClimbing(boolean isSpectator)
    {
        return isSpectator || (capabilities.isFlying);
    }
}