package mod.acgaming.universaltweaks.tweaks.misc.sound.broadcast.mixin;

import net.minecraft.entity.boss.EntityWither;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityWither.class)
public abstract class UTBroadcastSoundWitherMixin
{
    @Redirect(method = "updateAITasks", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playBroadcastSound(ILnet/minecraft/util/math/BlockPos;I)V"))
    public void utBroadcastSoundWither(World world, int id, BlockPos pos, int data)
    {
        world.playEvent(id, pos, data);
    }
}
