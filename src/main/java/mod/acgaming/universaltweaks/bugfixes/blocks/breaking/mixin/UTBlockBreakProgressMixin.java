package mod.acgaming.universaltweaks.bugfixes.blocks.breaking.mixin;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.math.MathHelper;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// MC-35078
// https://bugs.mojang.com/browse/MC-35078
@Mixin(PlayerControllerMP.class)
public abstract class UTBlockBreakProgressMixin
{
    @ModifyArg(method = {"clickBlock", "onPlayerDamageBlock"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;sendBlockBreakProgress(ILnet/minecraft/util/math/BlockPos;I)V"), index = 2)
    public int utBlockBreakProgress(int original)
    {
        return MathHelper.clamp(original + 1, 0, 9);
    }
}
