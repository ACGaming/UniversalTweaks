package mod.acgaming.universaltweaks.tweaks.blocks.fastflyblockbreaking.mixin;

import net.minecraft.entity.player.EntityPlayer;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityPlayer.class)
public class UTDigSpeedEntityPlayerMixin
{
    @ModifyExpressionValue(method = "getDigSpeed(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;)F", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;onGround:Z", opcode = Opcodes.GETFIELD, ordinal = 0))
    private boolean utCancelFlyPenalty(boolean original)
    {
        return true;
    }
}
