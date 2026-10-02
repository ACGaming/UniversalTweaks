package mod.acgaming.universaltweaks.mods.enderutilities.mixin;

import com.feed_the_beast.ftblib.integration.FTBLibJEIIntegration;
import mod.acgaming.universaltweaks.mods.enderutilities.client.UTGuiHandyBagJEIHandler;
import mezz.jei.api.IModRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = FTBLibJEIIntegration.class, remap = false)
public class UTFTBLibJEIIntegrationMixin
{
    @Inject(method = "register", at = @At("TAIL"), require = 1, remap = false)
    private void ut$registerHandyBagExclusionArea(IModRegistry registry, CallbackInfo ci)
    {
        registry.addAdvancedGuiHandlers(new UTGuiHandyBagJEIHandler());
    }
}
