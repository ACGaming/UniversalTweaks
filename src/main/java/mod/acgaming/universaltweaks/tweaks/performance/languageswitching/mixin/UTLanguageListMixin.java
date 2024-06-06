package mod.acgaming.universaltweaks.tweaks.performance.languageswitching.mixin;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.resource.IResourceType;
import net.minecraftforge.fml.client.FMLClientHandler;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of WaitingIdly, TheRandomLabs (RandomPatches)
@Mixin(targets = "net.minecraft.client.gui.GuiLanguage$List")
public abstract class UTLanguageListMixin
{
    @Redirect(method = "elementClicked", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/client/FMLClientHandler;refreshResources([Lnet/minecraftforge/client/resource/IResourceType;)V", remap = false))
    public void utImproveLanguageSwitchingPerformance(FMLClientHandler handler, IResourceType[] resources)
    {
        if (!UTConfigTweaks.PERFORMANCE.utImproveLanguageSwitchingSpeed) return;
        Minecraft mc = Minecraft.getMinecraft();
        mc.getLanguageManager().onResourceManagerReload(mc.getResourceManager());
    }
}