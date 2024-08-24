package mod.acgaming.universaltweaks.mods.compactmachines.spawns.mixin;

import net.minecraft.world.WorldProvider;

import org.dave.compactmachines3.misc.ConfigurationHandler;
import org.dave.compactmachines3.world.WorldProviderMachines;
import org.spongepowered.asm.mixin.Mixin;

// Courtesy of jchung01
@Mixin(value = WorldProviderMachines.class)
public abstract class UTWorldProviderMachinesMixin extends WorldProvider
{
    /**
     * Let CM config set allowed spawns when the dim loads.
     * This helps server performance especially when both hostile and peaceful spawns are disabled.
     */
    @Override
    public void setAllowedSpawnTypes(boolean allowHostile, boolean allowPeaceful)
    {
        super.setAllowedSpawnTypes(ConfigurationHandler.MachineSettings.allowHostileSpawns, ConfigurationHandler.MachineSettings.allowPeacefulSpawns);
    }
}
