package mod.acgaming.universaltweaks.mods.woot.mixin;

import net.minecraftforge.common.ForgeChunkManager;

import ipsis.Woot;
import mod.acgaming.universaltweaks.mods.woot.UTWootTicketManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of jchung01
@Mixin(value = Woot.class, remap = false)
public class UTWootMixin
{
    @Inject(method = "postInit", at = @At(value = "TAIL"))
    private void utRegisterTicketCallback(CallbackInfo ci)
    {
        ForgeChunkManager.setForcedChunkLoadingCallback(Woot.instance, UTWootTicketManager::callback);
    }
}
