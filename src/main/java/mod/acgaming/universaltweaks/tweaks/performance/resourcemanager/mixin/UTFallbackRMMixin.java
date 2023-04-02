package mod.acgaming.universaltweaks.tweaks.performance.resourcemanager.mixin;

import java.util.List;

import net.minecraft.client.resources.FallbackResourceManager;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.util.ResourceLocation;

import mod.acgaming.universaltweaks.tweaks.performance.resourcemanager.ICheckableResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// Courtesy of Darkhax
@Mixin(FallbackResourceManager.class)
public class UTFallbackRMMixin implements ICheckableResourceManager
{
    @Shadow
    @Final
    protected List<IResourcePack> resourcePacks;

    @Override
    public boolean hasResource(ResourceLocation location)
    {
        return this.resourcePacks.stream().anyMatch(pack -> pack.resourceExists(location));
    }
}