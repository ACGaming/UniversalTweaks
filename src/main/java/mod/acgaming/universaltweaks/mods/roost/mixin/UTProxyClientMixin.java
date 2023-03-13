package mod.acgaming.universaltweaks.mods.roost.mixin;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableSet;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;

import com.timwoodcreates.roost.RoostTextures;
import com.timwoodcreates.roost.proxy.ProxyClient;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of jchung01
@Mixin(value = ProxyClient.class, remap = false)
public class UTProxyClientMixin
{
    @Inject(method = "loadComplete", at = @At("HEAD"))
    public void utRoostLoadComplete(FMLLoadCompleteEvent e, CallbackInfo ci)
    {
        if (UTConfig.MOD_INTEGRATION.ROOST.utRoostChickenMods.length == 0) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTProxyClientMixin ::: Rebuild roost stock textures");
        // stockTextures is immutable set, use stream to rebuild
        RoostTextures.stockTextures = Stream.concat(RoostTextures.stockTextures.stream(),
                Arrays.stream(UTConfig.MOD_INTEGRATION.ROOST.utRoostChickenMods))
            .collect(Collectors.collectingAndThen(Collectors.toSet(), ImmutableSet::copyOf));
    }
}