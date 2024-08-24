package mod.acgaming.universaltweaks.mods.compactmachines.spawns.mixin;

import java.util.Collections;
import java.util.List;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import org.dave.compactmachines3.misc.ConfigurationHandler;
import org.dave.compactmachines3.world.ChunkGeneratorMachines;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

// Courtesy of jchung01
@Mixin(value = ChunkGeneratorMachines.class)
public class UTChunkGeneratorMachinesMixin
{
    @Shadow(remap = false)
    @Final
    private World world;

    /**
     * Another spot the CM config should control spawns; here for redundancy.
     * @reason Control mob spawn based on type
     * @author jchung01
     */
    @Overwrite
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
    {
        if ((creatureType.getPeacefulCreature() && ConfigurationHandler.MachineSettings.allowPeacefulSpawns) ||
            (!creatureType.getPeacefulCreature() && ConfigurationHandler.MachineSettings.allowHostileSpawns))
        {
            return this.world.getBiome(pos).getSpawnableList(creatureType);
        }
        else return Collections.emptyList();
    }
}
