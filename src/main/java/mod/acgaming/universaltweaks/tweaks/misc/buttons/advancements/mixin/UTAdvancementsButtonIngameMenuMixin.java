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
    public void utRemoveAdvancementsButtonIngameMenu(CallbackInfo ci)
    {
        buttonList.removeIf(button -> button.id == 5);
        for (GuiButton button : buttonList)
        {
            if (button.id == 6)
            {
                button.x = this.width / 2 - 100;
                button.setWidth(200);
                break;
            }
        }
    }
}
