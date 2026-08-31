package mod.acgaming.universaltweaks.mods.extrautilities.cursedearth.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

import com.rwtema.extrautils2.blocks.BlockCursedEarth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockCursedEarth.class, remap = false)
public abstract class UTCursedEarthMobPersistenceMixin
{
    @Inject(method = "spawnMobAsCursed", at = @At("RETURN"), remap = false)
    private static void ut$enableCursedEarthMobPersistence(Entity mob, CallbackInfoReturnable<Boolean> cir)
    {
        if (!cir.getReturnValueZ() || !(mob instanceof EntityLiving)) return;

        ((EntityLiving) mob).enablePersistence();
    }
}
