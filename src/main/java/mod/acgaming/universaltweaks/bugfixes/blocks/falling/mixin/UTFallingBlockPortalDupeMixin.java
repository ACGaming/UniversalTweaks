package mod.acgaming.universaltweaks.bugfixes.blocks.falling.mixin;

import net.minecraft.block.BlockEndPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of Blue-Beaker
@Mixin(BlockEndPortal.class)
public abstract class UTFallingBlockPortalDupeMixin
{
    @Inject(method = "onEntityCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;changeDimension(I)Lnet/minecraft/entity/Entity;"), cancellable = true)
    public void utOnEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity, CallbackInfo ci)
    {
        if (entity instanceof EntityFallingBlock) ci.cancel();
    }
}
