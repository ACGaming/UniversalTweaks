package mod.acgaming.universaltweaks.mods.roots.soil.mixin;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import epicsquid.roots.block.BlockElementalSoil;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(value = BlockElementalSoil.class, remap = false)
public abstract class UTBlockElementalSoilMixin
{
    /**
     * @author WaitingIdly
     * @reason Don't schedule an update when a neighbor changes, as doing so makes redstone pulses grow crops.
     */
    @WrapWithCondition(method = "neighborChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;scheduleUpdate(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;I)V"), remap = true)
    private boolean utSkipNeighborUpdates(World instance, BlockPos pos, Block blockIn, int delay)
    {
        return false;
    }
}
