package mod.acgaming.universaltweaks.tweaks.items.hardcorebuckets.mixin;

import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidUtil.class)
public class UTFluidUtilMixin
{
    @Inject(method = "tryPlaceFluid(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraftforge/fluids/capability/IFluidHandler;Lnet/minecraftforge/fluids/FluidStack;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FF)V"))
    private static void utFluidUtil(EntityPlayer player, World world, BlockPos pos, IFluidHandler fluidSource, FluidStack resource, CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfig.TWEAKS_ITEMS.utHardcoreBucketsToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTFluidUtil ::: Replace liquid");
        try
        {
            world.setBlockState(pos, resource.getFluid().getBlock().getDefaultState().withProperty(BlockLiquid.LEVEL, 1));
        }
        catch (Exception ignored) {}
    }
}