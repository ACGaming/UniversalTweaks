package mod.acgaming.universaltweaks.mods.simpledifficulty.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.charles445.simpledifficulty.config.ModConfig;
import com.charles445.simpledifficulty.temperature.ModifierAltitude;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ModifierAltitude.class, remap = false)
public class UTModifierAltitudeMixin
{
    @Inject(method = "getWorldInfluence", at = @At(value = "RETURN"), cancellable = true)
    public void utModifierAltitude(World world, BlockPos pos, CallbackInfoReturnable<Float> cir)
    {
        if (!world.provider.isSurfaceWorld()) cir.setReturnValue(0.0F);

        float seaLevel = UTConfigMods.SIMPLE_DIFFICULTY.utAltitudeSeaLevel;
        float multiplier = pos.getY() > seaLevel ? (float) UTConfigMods.SIMPLE_DIFFICULTY.utAltitudeMultiplierAboveSeaLevel : (float) UTConfigMods.SIMPLE_DIFFICULTY.utAltitudeMultiplierBelowSeaLevel;

        cir.setReturnValue(-1.0F * (Math.abs(((seaLevel - pos.getY()) / seaLevel * ModConfig.server.temperature.altitudeMultiplier * multiplier) + 1.0F)));
    }
}