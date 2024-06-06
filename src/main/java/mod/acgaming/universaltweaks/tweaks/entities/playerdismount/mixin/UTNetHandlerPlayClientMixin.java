package mod.acgaming.universaltweaks.tweaks.entities.playerdismount.mixin;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.KeyBinding;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.tweaks.entities.playerdismount.UTDismountKeybind;

// Courtesy of WaitingIdly, TheRandomLabs (RandomPatches)
@Mixin(value = NetHandlerPlayClient.class)
public abstract class UTNetHandlerPlayClientMixin
{
    @WrapOperation(method = "handleSetPassengers", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;getDisplayName()Ljava/lang/String;", remap = false))
    public String utHandleSetPassengers(KeyBinding instance, Operation<String> original)
    {
        if (!UTConfigTweaks.MISC.utUseSeparateDismountKey) return original.call(instance);
        return UTDismountKeybind.key.getKey().getDisplayName();
    }
}