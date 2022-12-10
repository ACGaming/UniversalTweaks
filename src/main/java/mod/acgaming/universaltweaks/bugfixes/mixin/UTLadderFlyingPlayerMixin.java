package mod.acgaming.universaltweaks.bugfixes.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// MC-12829
// https://bugs.mojang.com/browse/MC-12829
@Mixin(EntityPlayer.class)
public class UTLadderFlyingPlayerMixin extends UTLadderFlyingMixin
{
    @Shadow
    public PlayerCapabilities capabilities;

    @Override
    public boolean utIsNotClimbing(boolean isSpectator)
    {
        if (!UTConfig.bugfixes.utLadderFlyingToggle) return isSpectator;
        if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTLadderFlyingPlayer ::: Player climb check");
        return isSpectator || capabilities.isFlying;
    }
}