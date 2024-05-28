package mod.acgaming.universaltweaks.tweaks.misc.mainmenu.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of WaitingIdly, TheRandomLabs (RandomPatches)
@Mixin(value = GuiIngameMenu.class)
public abstract class UTGuiIngameMenuMixin
{
    @WrapOperation(method = "actionPerformed", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;isIntegratedServerRunning()Z"))
    private boolean utAlwaysReturnToMainMenu(Minecraft instance, Operation<Boolean> original)
    {
        if (!UTConfigTweaks.MISC.utReturnToMainMenu) return original.call(instance);
        return true;
    }
}