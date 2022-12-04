package mod.acgaming.universaltweaks.tweaks.mixin;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.Logger;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundList;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.audio.SoundRegistry;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.ProgressManager.ProgressBar;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of Darkhax
@Mixin(SoundHandler.class)
public class UTAudioReloadMixin
{
    @Shadow
    @Final
    private static Logger LOGGER;
    @Shadow
    @Final
    private SoundManager sndManager;
    @Shadow
    @Final
    private SoundRegistry soundRegistry;

    @Inject(method = "onResourceManagerReload(Lnet/minecraft/client/resources/IResourceManager;)V", at = @At("HEAD"), cancellable = true)
    public void utOnResourceManagerReload(IResourceManager resourceManager, CallbackInfo info)
    {
        if (!UTConfig.tweaks.utDisableAudioDebugToggle) return;
        if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTAudioReloadMixin ::: Resource manager reload");
        this.soundRegistry.clearMap();
        final List<Tuple<ResourceLocation, SoundList>> soundLists = new LinkedList<>();
        this.loadSoundLists(resourceManager, soundLists);
        this.loadSounds(soundLists);
        this.sndManager.reloadSoundSystem();
        info.cancel();
    }

    public void loadSounds(List<Tuple<ResourceLocation, SoundList>> soundLists)
    {
        final ProgressBar resourcesBar = ProgressManager.push("Loading sounds", soundLists.size());
        for (final Tuple<ResourceLocation, SoundList> soundList : soundLists)
        {
            resourcesBar.step(soundList.getFirst().toString());
            try
            {
                this.loadSoundResource(soundList.getFirst(), soundList.getSecond());
            }
            catch (final RuntimeException e)
            {
                LOGGER.warn("Invalid sounds.json", e);
            }
        }
        ProgressManager.pop(resourcesBar);
    }

    public void loadSoundLists(IResourceManager resourceManager, List<Tuple<ResourceLocation, SoundList>> soundLists)
    {
        for (final String s : resourceManager.getResourceDomains())
        {
            try
            {
                for (final IResource managerSoundList : resourceManager.getAllResources(new ResourceLocation(s, "sounds.json")))
                {
                    for (final Entry<String, SoundList> entry : this.getSoundMap(managerSoundList.getInputStream()).entrySet())
                    {
                        soundLists.add(new Tuple<>(new ResourceLocation(s, entry.getKey()), entry.getValue()));
                    }
                }
            }
            catch (final Exception ignored) {}
        }
    }

    @Shadow
    protected Map<String, SoundList> getSoundMap(InputStream stream)
    {
        return null;
    }

    @Shadow
    private void loadSoundResource(ResourceLocation location, SoundList sounds) {}
}