package mod.acgaming.universaltweaks.bugfixes.entities.ai.rails.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// MC-33359
// https://bugs.mojang.com/browse/MC-33359
@Mixin(value = Block.class, remap = false)
public abstract class UTMobsCrossingRailsMixin
{
    @Inject(method = "getAiPathNodeType(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/pathfinding/PathNodeType;", at = @At("HEAD"), cancellable = true)
    private void utGetAiPathNodeTypeOld(IBlockState state, IBlockAccess world, BlockPos pos, CallbackInfoReturnable<PathNodeType> cir)
    {
        ut$getAiPathNodeType(state, world, pos, cir);
    }

    @Inject(method = "getAiPathNodeType(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/EntityLiving;)Lnet/minecraft/pathfinding/PathNodeType;", at = @At("HEAD"), cancellable = true)
    private void utGetAiPathNodeTypeNew(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving entity, CallbackInfoReturnable<PathNodeType> cir)
    {
        ut$getAiPathNodeType(state, world, pos, cir);
    }

    @Unique
    private void ut$getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos, CallbackInfoReturnable<PathNodeType> cir)
    {
        if (state.getBlock().isBurning(world, pos)) cir.setReturnValue(PathNodeType.DAMAGE_FIRE);
        else if (state.getBlock() instanceof BlockRailBase) cir.setReturnValue(PathNodeType.WALKABLE);
    }
}
