package mod.acgaming.universaltweaks.mods.compactmachines.spawns.mixin;

import java.util.Collections;
import java.util.List;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.dave.compactmachines3.misc.ConfigurationHandler;
import org.dave.compactmachines3.world.ChunkGeneratorMachines;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of jchung01
@Mixin(value = ChunkGeneratorMachines.class)
public class UTChunkGeneratorMachinesMixin
{
    /**
     * Another spot the CM config should control spawns; here for redundancy.
     */
    @ModifyReturnValue(method = "getPossibleCreatures", at = @At(value = "RETURN"))
    private List<Biome.SpawnListEntry> utCheckAllowedCreatures(List<Biome.SpawnListEntry> original, EnumCreatureType creatureType, BlockPos pos)
    {
        if ((creatureType.getPeacefulCreature() && ConfigurationHandler.MachineSettings.allowPeacefulSpawns) ||
            (!creatureType.getPeacefulCreature() && ConfigurationHandler.MachineSettings.allowHostileSpawns))
        {
            return original;
        }
        else return Collections.emptyList();
    }
}
