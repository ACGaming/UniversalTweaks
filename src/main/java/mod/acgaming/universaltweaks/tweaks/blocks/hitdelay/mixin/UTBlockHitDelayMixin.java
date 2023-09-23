package mod.acgaming.universaltweaks.tweaks.blocks.hitdelay.mixin;

import net.minecraft.client.multiplayer.PlayerControllerMP;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PlayerControllerMP.class)
public class UTBlockHitDelayMixin
{
    @ModifyConstant(method = "onPlayerDamageBlock", constant = @Constant(intValue = 5, ordinal = 1))
    public int utBlockHitDelay(int constant)
    {
        return UTConfigTweaks.BLOCKS.utBlockHitDelay;
    }
}