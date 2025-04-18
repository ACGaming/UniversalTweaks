package mod.acgaming.universaltweaks.tweaks.world.sealevel.mixin;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Biome.class)
public class UTSeaLevelBiomeMixin
{
    @ModifyConstant(method = "generateBiomeTerrain(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/world/chunk/ChunkPrimer;IID)V", constant = @Constant(intValue = 63))
    private int utGenerateBiomeTerrain(int constant, World world)
    {
        return world.getSeaLevel();
    }
}