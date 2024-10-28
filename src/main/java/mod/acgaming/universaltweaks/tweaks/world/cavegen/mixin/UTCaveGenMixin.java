package mod.acgaming.universaltweaks.tweaks.world.cavegen.mixin;

import net.minecraft.world.gen.MapGenCaves;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// Courtesy of brunnh1lde
@Mixin(MapGenCaves.class)
public abstract class UTCaveGenMixin
{
    @ModifyArg(method = "recursiveGenerate", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", ordinal = 2))
    public int utCaveGenSize(int bound)
    {
        return UTConfigTweaks.WORLD.CAVE_GEN.utCaveGenSize;
    }

    @ModifyArg(method = "recursiveGenerate", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", ordinal = 3))
    public int utCaveGenRarity(int bound)
    {
        return UTConfigTweaks.WORLD.CAVE_GEN.utCaveGenRarity;
    }
}