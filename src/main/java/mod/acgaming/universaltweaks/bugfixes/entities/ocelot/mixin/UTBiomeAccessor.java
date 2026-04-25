package mod.acgaming.universaltweaks.bugfixes.entities.ocelot.mixin;

import java.util.List;

import net.minecraft.world.biome.Biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Biome.class)
public interface UTBiomeAccessor
{
    @Accessor("spawnableCreatureList")
    List<Biome.SpawnListEntry> getSpawnableCreatureList();

    @Accessor("spawnableMonsterList")
    List<Biome.SpawnListEntry> getSpawnableMonsterList();
}
