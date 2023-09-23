package mod.acgaming.universaltweaks.bugfixes.blocks.ladder.mixin;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// MC-12829
// https://bugs.mojang.com/browse/MC-12829
@Mixin(EntityPlayer.class)
public abstract class UTLadderFlyingPlayerMixin extends EntityLivingBase
{
    @Shadow
    public PlayerCapabilities capabilities;

    public UTLadderFlyingPlayerMixin(World worldIn)
    {
        super(worldIn);
    }

    @Override
    public boolean isOnLadder()
    {
        if (UTConfigBugfixes.BLOCKS.utLadderFlyingToggle)
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTLadderFlyingPlayer ::: Player climb check");
            if (capabilities.isFlying) return false;
        }
        return super.isOnLadder();
    }
}