package mod.acgaming.universaltweaks.tweaks.world.cavegen.mixin;

import net.minecraft.world.gen.MapGenCaves;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

// Courtesy of brunnh1lde
@Mixin(MapGenCaves.class)
public abstract class UTCaveGenMixin
{
    @ModifyExpressionValue(
        method = "recursiveGenerate",
        at = @At(value = "CONSTANT", args = "intValue=15", ordinal = 0))
    public int utCaveGenSize(int bound)
    {
        return UTConfigTweaks.WORLD.CAVE_GEN.utCaveGenSize;
    }

    @ModifyExpressionValue(
        method = "recursiveGenerate",
        at = @At(value = "CONSTANT", args = "intValue=7", ordinal = 0))
    public int utCaveGenRarity(int bound)
    {
        return UTConfigTweaks.WORLD.CAVE_GEN.utCaveGenRarity;
    }
}