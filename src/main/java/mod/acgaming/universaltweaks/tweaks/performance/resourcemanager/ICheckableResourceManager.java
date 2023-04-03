package mod.acgaming.universaltweaks.tweaks.performance.resourcemanager;

import net.minecraft.util.ResourceLocation;

// Courtesy of Darkhax
public interface ICheckableResourceManager
{
    boolean hasResource(ResourceLocation location);
}