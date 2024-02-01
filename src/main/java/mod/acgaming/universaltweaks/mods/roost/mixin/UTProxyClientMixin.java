package mod.acgaming.universaltweaks.mods.roost.mixin;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableSet;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;

import com.teamacronymcoders.contenttweaker.modules.chickens.ChickenFactory;
import com.teamacronymcoders.contenttweaker.modules.chickens.ChickenRepresentation;
import com.timwoodcreates.roost.RoostTextures;
import com.timwoodcreates.roost.proxy.ProxyClient;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigMods;
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
        if (!UTConfigMods.ROOST.utRoostEarlyRegisterCTChickens) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTProxyClientMixin ::: Rebuild roost stock textures");
        // stockTextures is immutable set, use stream to rebuild
        RoostTextures.stockTextures = Stream.concat(RoostTextures.stockTextures.stream(),
                ChickenFactory.CHICKEN_REPRESENTATIONS.stream().map(ChickenRepresentation::getName))
            .collect(Collectors.collectingAndThen(Collectors.toSet(), ImmutableSet::copyOf));
    }
}