package mod.acgaming.universaltweaks.mods.enderutilities.mixin;

import com.feed_the_beast.ftblib.client.EnumSidebarButtonPlacement;
import com.feed_the_beast.ftblib.client.FTBLibClientConfig;
import com.feed_the_beast.ftblib.client.SidebarButtonManager;
import mod.acgaming.universaltweaks.mods.enderutilities.client.UTGuiHandyBagSidebarButtonGroup;
import fi.dy.masa.enderutilities.gui.client.GuiHandyBag;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiHandyBag.class, remap = false)
public abstract class UTGuiHandyBagSidebarButtonsMixin extends GuiContainer
{
    @Unique
    private GuiButton ut$sidebarButtons;

    protected UTGuiHandyBagSidebarButtonsMixin(Container inventorySlotsIn)
    {
        super(inventorySlotsIn);
    }

    @Inject(method = "createButtons", at = @At("TAIL"), require = 1, remap = false)
    private void ut$restoreSidebarButtons(CallbackInfo ci)
    {
        this.ut$addSidebarButtons();
    }

    @Inject(method = "actionPerformedWithButton", at = @At("TAIL"), require = 1, remap = false)
    private void ut$clickSidebarButton(GuiButton button, int mouseButton, CallbackInfo ci)
    {
        if (button == this.ut$sidebarButtons && mouseButton == 0)
        {
            ((UTGuiHandyBagSidebarButtonGroup) this.ut$sidebarButtons).onClicked();
        }
    }

    @Unique
    private void ut$addSidebarButtons()
    {
        if (FTBLibClientConfig.action_buttons == EnumSidebarButtonPlacement.DISABLED || SidebarButtonManager.INSTANCE.groups.isEmpty())
        {
            return;
        }

        if (this.ut$sidebarButtons == null)
        {
            this.ut$sidebarButtons = new UTGuiHandyBagSidebarButtonGroup(this);
        }

        if (this.buttonList.contains(this.ut$sidebarButtons) == false)
        {
            this.buttonList.add(this.ut$sidebarButtons);
        }
    }
}
