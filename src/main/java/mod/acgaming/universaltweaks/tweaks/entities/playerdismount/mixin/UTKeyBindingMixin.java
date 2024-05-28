package mod.acgaming.universaltweaks.tweaks.entities.playerdismount.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.tweaks.entities.playerdismount.UTDismountKeybind;

// Courtesy of WaitingIdly, TheRandomLabs (RandomPatches)
@Mixin(value = KeyBinding.class)
public abstract class UTKeyBindingMixin
{
    @Inject(method = "conflicts", at = @At("HEAD"), cancellable = true, remap = false)
    public void utConflicts(KeyBinding other, CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfigTweaks.MISC.utUseSeparateDismountKey) return;
        //noinspection ConstantValue
        if (UTDismountKeybind.key.getKey() == ((Object) this) && Minecraft.getMinecraft().gameSettings.keyBindSneak == other ||
                UTDismountKeybind.key.getKey() == other && Minecraft.getMinecraft().gameSettings.keyBindSneak == ((Object) this))
        {
            cir.setReturnValue(false);
        }
    }
}