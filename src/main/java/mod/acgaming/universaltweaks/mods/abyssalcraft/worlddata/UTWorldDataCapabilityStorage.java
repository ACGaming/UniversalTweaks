package mod.acgaming.universaltweaks.mods.abyssalcraft.worlddata;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

// Courtesy of jchung01
public class UTWorldDataCapabilityStorage implements IStorage<IUTWorldDataCapability>
{
    public static final IStorage<IUTWorldDataCapability> INSTANCE = new UTWorldDataCapabilityStorage();

    // Not using persistent storage for World data.
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IUTWorldDataCapability> capability, IUTWorldDataCapability IUTWorldDataCapability, EnumFacing enumFacing)
    {
        return new NBTTagCompound();
    }

    // Not using persistent storage for World data.
    @Override
    public void readNBT(Capability<IUTWorldDataCapability> capability, IUTWorldDataCapability IUTWorldDataCapability, EnumFacing enumFacing, NBTBase nbtBase) {}
}