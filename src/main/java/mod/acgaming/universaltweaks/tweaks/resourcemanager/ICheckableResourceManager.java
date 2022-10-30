package mod.acgaming.universaltweaks.tweaks.resourcemanager;

import net.minecraft.util.ResourceLocation;

// Courtesy of Darkhax
public interface ICheckableResourceManager
{
    boolean hasResource(ResourceLocation location);
}