package mod.acgaming.universaltweaks.bugfixes.blocks.comparator.mixin;

import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// MC-12211
// https://bugs.mojang.com/browse/MC-12211
// Courtesy of mrgrim
@Mixin(BlockRedstoneComparator.class)
public abstract class UTComparatorTimingMixin extends BlockRedstoneDiode
{
    @Shadow
    @Final
    public static PropertyEnum<BlockRedstoneComparator.Mode> MODE;

    public UTComparatorTimingMixin(boolean powered)
    {
        super(powered);
    }

    @Redirect(method = "calculateOutput", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockRedstoneComparator;calculateInputStrength(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)I", ordinal = 1))
    public int utFixCompareModeOutput(BlockRedstoneComparator that, World worldIn, BlockPos posIn, IBlockState stateIn)
    {
        if (UTConfig.BUGFIXES_BLOCKS.utComparatorTimingToggle)
        {
            if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTComparatorTimingMixin ::: Calculate output");
            int inputStrength = this.calculateInputStrength(worldIn, posIn, stateIn);
            if (this.getPowerOnSides(worldIn, posIn, stateIn) > inputStrength) return 0;
            else return inputStrength;
        }
        else return this.calculateInputStrength(worldIn, posIn, stateIn);
    }

    @Inject(method = "shouldBePowered", at = @At("HEAD"), cancellable = true)
    public void utBetterShouldBePowered(World worldIn, BlockPos posIn, IBlockState stateIn, CallbackInfoReturnable<Boolean> cir)
    {
        if (UTConfig.BUGFIXES_BLOCKS.utComparatorTimingToggle)
        {
            if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTComparatorTimingMixin ::: Should be powered check");
            cir.setReturnValue(false);
            int inputStrength = this.calculateInputStrength(worldIn, posIn, stateIn);
            if (inputStrength > 0)
            {
                int sideStrength = this.getPowerOnSides(worldIn, posIn, stateIn);
                if (inputStrength > sideStrength) cir.setReturnValue(true);
                else if (inputStrength == sideStrength) cir.setReturnValue(stateIn.getValue(MODE) == BlockRedstoneComparator.Mode.COMPARE);
            }
        }
    }

    @Shadow
    protected abstract int calculateInputStrength(World worldIn, BlockPos pos, IBlockState state);
}