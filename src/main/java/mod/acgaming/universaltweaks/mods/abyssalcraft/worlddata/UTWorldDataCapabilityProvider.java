package mod.acgaming.universaltweaks.mods.abyssalcraft.worlddata;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

// Courtesy of jchung01
public class UTWorldDataCapabilityProvider implements ICapabilityProvider
{
    @CapabilityInject(IUTWorldDataCapability.class)
    public static Capability<IUTWorldDataCapability> WORLD_DATA_CAP = null;

    private final IUTWorldDataCapability capability;

    public UTWorldDataCapabilityProvider()
    {
        capability = new UTWorldDataCapability();
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing enumFacing)
    {
        return capability == WORLD_DATA_CAP;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing enumFacing)
    {
        if(capability == WORLD_DATA_CAP)
            return (T) this.capability;

        return null;
    }
}
