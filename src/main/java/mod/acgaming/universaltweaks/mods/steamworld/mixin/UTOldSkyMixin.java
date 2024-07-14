package mod.acgaming.universaltweaks.mods.steamworld.mixin;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import zaexides.steamworld.world.dimension.BiomeProviderSkyOfOld;

@Mixin(value = BiomeProviderSkyOfOld.class)
public class UTOldSkyMixin extends BiomeProvider
{
    /**
     * @author MCAdventureCity
     * @reason Fix StackOverflowError when entering the Sky of Old Dimension.
     */
    @Overwrite
    public Biome[] getBiomes(Biome[] oldBiomeList, int x, int z, int width, int depth) {
        return super.getBiomes(oldBiomeList, x, z, width, depth);
    }
}
