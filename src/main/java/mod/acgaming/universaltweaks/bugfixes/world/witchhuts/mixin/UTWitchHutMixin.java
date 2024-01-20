package mod.acgaming.universaltweaks.bugfixes.world.witchhuts.mixin;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.gen.structure.StructureStart;

import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// MC-73051
// https://bugs.mojang.com/browse/MC-73051
// Courtesy of mrgrim, Xcom6000
@Mixin(StructureStart.class)
public abstract class UTWitchHutMixin
{
    @Inject(method = "writeStructureComponentsToNBT", at = @At(value = "CONSTANT", args = "stringValue=Children", ordinal = 0))
    public void callUpdateBoundingBox(int chunkX, int chunkZ, CallbackInfoReturnable<NBTTagCompound> ci)
    {
        if (UTConfigBugfixes.WORLD.utWitchStructuresToggle) this.updateBoundingBox();
    }

    @Shadow
    protected abstract void updateBoundingBox();
}