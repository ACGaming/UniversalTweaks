package mod.acgaming.universaltweaks.bugfixes.blocks.miningglitch.mixin;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// MC-5694
// https://bugs.mojang.com/browse/MC/issues/MC-5694
// Courtesy of gnembon
@Mixin(PlayerInteractionManager.class)
public abstract class UTMiningGlitchMixin
{
    @Shadow
    public EntityPlayerMP player;

    @Shadow
    public World world;

    @Inject(method = "onBlockClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;sendBlockBreakProgress(ILnet/minecraft/util/math/BlockPos;I)V"))
    public void utMiningGlitch(BlockPos pos, EnumFacing side, CallbackInfo ci)
    {
        this.player.connection.sendPacket(new SPacketBlockChange(this.world, pos));
    }
}
