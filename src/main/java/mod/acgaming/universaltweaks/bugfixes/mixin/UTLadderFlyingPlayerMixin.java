package mod.acgaming.universaltweaks.bugfixes.mixin;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
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
        if (UTConfig.BUGFIXES_BLOCKS.utLadderFlyingToggle)
        {
            if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTLadderFlyingPlayer ::: Player climb check");
            if (capabilities.isFlying) return false;
        }
        return super.isOnLadder();
    }
}