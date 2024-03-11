package mod.acgaming.universaltweaks.bugfixes.blocks.miningglitch.mixin;

import net.minecraft.network.NetHandlerPlayServer;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

// MC-118710
// https://bugs.mojang.com/browse/MC-118710
// Courtesy of mrgrim
@Mixin(NetHandlerPlayServer.class)
public class UTPlayerPacketsMixin
{
    @ModifyConstant(method = "processPlayer", constant = @Constant(intValue = 5, ordinal = 0))
    private int utModifyMaxPlayerMovementPacketsPerTick(int maxPackets)
    {
        if (!UTConfigBugfixes.BLOCKS.MINING_GLITCH.utMiningGlitchToggle) return maxPackets;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTPlayerPacketsMixin ::: Process player");
        if (maxPackets == 5) return UTConfigBugfixes.BLOCKS.MINING_GLITCH.utMiningGlitchPackets;
        else return maxPackets;
    }
}