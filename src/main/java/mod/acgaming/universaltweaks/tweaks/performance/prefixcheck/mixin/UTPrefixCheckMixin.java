package mod.acgaming.universaltweaks.tweaks.performance.prefixcheck.mixin;

import java.util.Locale;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLContainer;
import net.minecraftforge.fml.common.InjectedModContainer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.registries.GameData;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of Darkhax
@Mixin(GameData.class)
public class UTPrefixCheckMixin
{
    @Inject(method = "checkPrefix(Ljava/lang/String;Z)Lnet/minecraft/util/ResourceLocation;", at = @At("HEAD"), cancellable = true, remap = false)
    private static void checkPrefix(String name, boolean warnOverrides, CallbackInfoReturnable<ResourceLocation> info)
    {
        if (!UTConfig.TWEAKS_PERFORMANCE.utPrefixCheckToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTPrefixCheckMixin ::: Check prefix");
        // Get position of the last separator
        final int separator = name.lastIndexOf(':');
        // Resolve the namespace
        String namespace = separator == -1 ? "" : name.substring(0, separator).toLowerCase(Locale.ROOT);
        // Resolve the path
        final String path = separator == -1 ? name : name.substring(separator + 1);
        // If there is no namespace, try to get it from the active mod.
        if (namespace.isEmpty())
        {
            final ModContainer activeMod = Loader.instance().activeModContainer();
            if (activeMod != null) namespace = activeMod instanceof InjectedModContainer && ((InjectedModContainer) activeMod).wrappedContainer instanceof FMLContainer ? "minecraft" : activeMod.getModId().toLowerCase(Locale.ROOT);
        }
        info.setReturnValue(new ResourceLocation(namespace, path));
    }
}