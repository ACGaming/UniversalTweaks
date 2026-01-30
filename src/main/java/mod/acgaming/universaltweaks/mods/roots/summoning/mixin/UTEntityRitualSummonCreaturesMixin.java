package mod.acgaming.universaltweaks.mods.roots.summoning.mixin;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import epicsquid.roots.entity.ritual.EntityRitualSummonCreatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(value = EntityRitualSummonCreatures.class, remap = false)
public abstract class UTEntityRitualSummonCreaturesMixin
{
    /**
     * @author WaitingIdly
     * @reason If the ritual is run over the void, it can enter a loop where it descends infinitely,
     * since blocks outside bounds are air blocks, and it runs until the first non-air block is found.
     * This makes it check if the block is valid too.
     */
    @WrapOperation(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isAirBlock(Lnet/minecraft/util/math/BlockPos;)Z", ordinal = 0, remap = true))
    private boolean utAirCheckIsValid(World instance, BlockPos pos, Operation<Boolean> original)
    {
        return instance.isValid(pos) && original.call(instance, pos);
    }

    /**
     * @author WaitingIdly
     * @reason If the ritual is run over the void, it can enter a loop where it descends infinitely,
     * since blocks outside bounds return the default value, "false", and its "!isSideSolid(solid, UP)".
     * This makes it check if the block is valid too.
     */
    @WrapOperation(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isSideSolid(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;)Z", remap = false), remap = true)
    private boolean utSolidCheckIsValid(World instance, BlockPos pos, EnumFacing side, Operation<Boolean> original)
    {
        return !instance.isValid(pos) || original.call(instance, pos, side);
    }
}
