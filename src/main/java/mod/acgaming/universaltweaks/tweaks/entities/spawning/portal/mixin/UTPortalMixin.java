package mod.acgaming.universaltweaks.tweaks.entities.spawning.portal.mixin;

import java.util.Random;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of fonnymunkey
@Mixin(BlockPortal.class)
public abstract class UTPortalMixin
{
    @Inject(
            method = "updateTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockBreakable;updateTick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;Ljava/util/Random;)V", shift = At.Shift.AFTER),
            cancellable = true
    )
    public void utPortalUpdateTick(World worldIn, BlockPos pos, IBlockState state, Random rand, CallbackInfo ci)
    {
    	if (UTConfigTweaks.ENTITIES.utPortalSpawningToggle) ci.cancel();
    }
}