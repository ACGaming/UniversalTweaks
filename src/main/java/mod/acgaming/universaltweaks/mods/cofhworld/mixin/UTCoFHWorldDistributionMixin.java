package mod.acgaming.universaltweaks.mods.cofhworld.mixin;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;

import cofh.cofhworld.world.distribution.Distribution;
import java.util.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Distribution.class, remap = false)
public class UTCoFHWorldDistributionMixin
{
    @Inject(method = "generateFeature(Ljava/util/Random;IILnet/minecraft/world/World;ZZ)Z", at = @At("HEAD"), cancellable = true)
    public void utGenerateFeature(Random random, int chunkX, int chunkZ, World world, boolean hasVillage, boolean newGen, CallbackInfoReturnable<Boolean> cir)
    {
        if (world.getWorldType() == WorldType.FLAT) cir.setReturnValue(false);
    }
}
