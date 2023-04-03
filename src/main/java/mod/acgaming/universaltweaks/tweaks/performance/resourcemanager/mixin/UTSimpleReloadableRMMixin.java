package mod.acgaming.universaltweaks.tweaks.performance.resourcemanager.mixin;

import java.util.Map;

import net.minecraft.client.resources.FallbackResourceManager;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.util.ResourceLocation;

import mod.acgaming.universaltweaks.tweaks.performance.resourcemanager.ICheckableResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// Courtesy of Darkhax
@Mixin(SimpleReloadableResourceManager.class)
public class UTSimpleReloadableRMMixin implements ICheckableResourceManager
{
    @Shadow
    @Final
    private Map<String, FallbackResourceManager> domainResourceManagers;

    @Override
    public boolean hasResource(ResourceLocation location)
    {
        final FallbackResourceManager manager = this.domainResourceManagers.get(location.getNamespace());
        return manager instanceof ICheckableResourceManager && ((ICheckableResourceManager) manager).hasResource(location);
    }
}