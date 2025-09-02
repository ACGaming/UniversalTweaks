package mod.acgaming.universaltweaks.tweaks.performance.languageswitching.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.tweaks.performance.languageswitching.UTLangSwitcher;
import net.minecraftforge.client.resource.IResourceType;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly, TheRandomLabs (RandomPatches)
@Mixin(targets = "net.minecraft.client.gui.GuiLanguage$List")
public abstract class UTLanguageListMixin
{
    @WrapOperation(method = "elementClicked", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/client/FMLClientHandler;refreshResources([Lnet/minecraftforge/client/resource/IResourceType;)V", remap = false))
    public void utImproveLanguageSwitchingPerformance(FMLClientHandler handler, IResourceType[] resources, Operation<Void> method)
    {
        if (UTConfigTweaks.PERFORMANCE.utImproveLanguageSwitchingSpeed)
        {
            UTLangSwitcher.onLangSwitched();
        }
        else
        {
            method.call(handler, resources);
        }
    }
}