package mod.acgaming.universaltweaks.mods.crafttweaker.mixin;

import crafttweaker.mc1120.proxies.ClientProxy;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ClientProxy.class, remap = false)
public class UTClientProxyMixin
{
    @Inject(method = "fixRecipeBook", at = @At("HEAD"), cancellable = true)
    public void utCTFixRecipeBook(CallbackInfo ci)
    {
        if (UTConfig.TWEAKS_MISC.utRecipeBookToggle) ci.cancel();
    }
}