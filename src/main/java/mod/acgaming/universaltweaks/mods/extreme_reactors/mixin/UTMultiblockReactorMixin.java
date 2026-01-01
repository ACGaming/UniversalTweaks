package mod.acgaming.universaltweaks.mods.extreme_reactors.mixin;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import erogenousbeef.bigreactors.common.multiblock.MultiblockReactor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = MultiblockReactor.class, remap = false)
public class UTMultiblockReactorMixin {

    /// java.lang.NullPointerException: Cannot invoke "net.minecraft.util.EnumFacing.func_82601_c()" because "☃" is null
    ///     at net.minecraft.util.math.BlockPos.offset(SourceFile:158)
    ///     at net.minecraft.util.math.BlockPos.offset(SourceFile:150)
    ///     at erogenousbeef.bigreactors.utils.FluidHelper.getTile(FluidHelper.java:50)
    ///     at erogenousbeef.bigreactors.utils.FluidHelper.getTile(FluidHelper.java:56)
    ///     at erogenousbeef.bigreactors.utils.FluidHelper.fillAdjacentHandler(FluidHelper.java:25)
    ///     at erogenousbeef.bigreactors.common.multiblock.MultiblockReactor.updateServer(MultiblockReactor.java:542)
    ///     at it.zerono.mods.zerocore.api.multiblock.MultiblockControllerBase.updateMultiblockEntity(MultiblockControllerBase.java:533)
    ///     at it.zerono.mods.zerocore.internal.common.MultiblockWorldRegistry.tickStart(MultiblockWorldRegistry.java:91)
    ///     at it.zerono.mods.zerocore.internal.common.MultiblockRegistry.tickStart(MultiblockRegistry.java:95)
    ///     at it.zerono.mods.zerocore.internal.common.MultiblockEventHandler.onWorldTick(MultiblockEventHandler.java:32)
    ///     at net.minecraftforge.fml.common.eventhandler.ASMEventHandler.invoke(ASMEventHandler.java:78)
    ///     at net.minecraftforge.fml.common.eventhandler.EventBus.post(EventBus.java:212)
    ///     at net.minecraftforge.fml.common.FMLCommonHandler.onPreWorldTick(FMLCommonHandler.java:298)
    ///     at net.minecraft.server.MinecraftServer.updateTimeLightAndEntities(MinecraftServer.java:752)
    ///     at net.minecraft.server.MinecraftServer.tick(MinecraftServer.java:668)
    ///     at net.minecraft.server.integrated.IntegratedServer.tick(IntegratedServer.java:185)
    ///     at net.minecraft.server.MinecraftServer.run(MinecraftServer.java:526)
    ///     at java.lang.Thread.run(Thread.java:1474)
    @WrapOperation(method = "updateServer", at = @At(value = "INVOKE", target = "Lerogenousbeef/bigreactors/utils/FluidHelper;fillAdjacentHandler(Lnet/minecraft/tileentity/TileEntity;Lnet/minecraft/util/EnumFacing;Lnet/minecraftforge/fluids/FluidStack;Z)I"))
    private int ut$preventNPE(TileEntity origin, EnumFacing facing, FluidStack fluid, boolean doFill, Operation<Integer> original) {
        return facing == null ? 0 : original.call(origin, facing, fluid, doFill);
    }
}
