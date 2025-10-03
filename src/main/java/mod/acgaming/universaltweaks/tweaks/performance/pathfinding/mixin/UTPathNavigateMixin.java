package mod.acgaming.universaltweaks.tweaks.performance.pathfinding.mixin;

import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import mod.acgaming.universaltweaks.tweaks.performance.pathfinding.NavigationCacheFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = PathNavigate.class)
public class UTPathNavigateMixin
{
    @WrapOperation(method = {"getPathToPos", "getPathToEntityLiving"}, at = @At(value = "NEW", target = "(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;I)Lnet/minecraft/world/ChunkCache;"))
    private ChunkCache utMarkCacheForNavigation(World worldIn, BlockPos posFromIn, BlockPos posToIn, int subIn, Operation<ChunkCache> original)
    {
        ChunkCache ret;
        NavigationCacheFlag.set(true);
        ret = original.call(worldIn, posFromIn, posToIn, subIn);
        NavigationCacheFlag.set(false);
        return ret;
    }

}
