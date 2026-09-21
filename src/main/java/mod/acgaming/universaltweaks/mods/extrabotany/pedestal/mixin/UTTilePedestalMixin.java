package mod.acgaming.universaltweaks.mods.extrabotany.pedestal.mixin;

import net.minecraft.world.World;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.meteor.extrabotany.common.block.tile.TilePedestal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = TilePedestal.class, remap = false)
public class UTTilePedestalMixin
{
    @WrapOperation(
        method = "update",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isDaytime()Z", remap = true),
        remap = true,
        require = 1)
    private boolean utFixFuelConversionDesync(World instance, Operation<Boolean> original)
    {
        return original.call(instance) && !instance.isRemote;
    }
}