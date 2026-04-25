package mod.acgaming.universaltweaks.bugfixes.entities.ocelot;

import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.bugfixes.entities.ocelot.mixin.UTBiomeAccessor;

// MC-1788
// https://bugs.mojang.com/browse/MC-1788
public class UTOcelotBiomeSpawnList
{
    public static void fixSpawnListEntries()
    {
        for (Biome biome : ForgeRegistries.BIOMES)
        {
            boolean removedMonster = ((UTBiomeAccessor) biome).getSpawnableMonsterList().removeIf(e -> e.entityClass == EntityOcelot.class);
            boolean addedCreature = ((UTBiomeAccessor) biome).getSpawnableCreatureList().stream().anyMatch(e -> e.entityClass == EntityOcelot.class);
            if (removedMonster && !addedCreature)
            {
                ((UTBiomeAccessor) biome).getSpawnableCreatureList().add(new Biome.SpawnListEntry(EntityOcelot.class, 2, 1, 1));
                UniversalTweaks.LOGGER.info("UTOcelotBiomeSpawnList ::: Fixed ocelot spawn list entry for biome {}", biome.getBiomeName());
            }
        }
    }
}
