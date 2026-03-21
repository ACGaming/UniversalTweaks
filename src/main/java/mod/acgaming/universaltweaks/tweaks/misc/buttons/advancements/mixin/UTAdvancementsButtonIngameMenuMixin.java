package mod.acgaming.universaltweaks.tweaks.misc.buttons.advancements.mixin;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngameMenu.class)
public abstract class UTAdvancementsButtonIngameMenuMixin extends GuiScreen
{
    @Inject(method = "initGui", at = @At("TAIL"))
    public void utDisableAdvancementsButtonIngameMenu(CallbackInfo ci)
    {
        for (GuiButton button : this.buttonList)
        {
            if (button.id == 5) button.enabled = false;
        }
    }
}
