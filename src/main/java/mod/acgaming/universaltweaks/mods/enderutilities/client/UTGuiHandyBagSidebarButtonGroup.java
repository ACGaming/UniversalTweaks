package mod.acgaming.universaltweaks.mods.enderutilities.client;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.feed_the_beast.ftblib.client.FTBLibClientConfig;
import com.feed_the_beast.ftblib.client.FTBLibClientEventHandler;
import com.feed_the_beast.ftblib.client.SidebarButton;
import com.feed_the_beast.ftblib.client.SidebarButtonGroup;
import com.feed_the_beast.ftblib.client.SidebarButtonManager;
import com.feed_the_beast.ftblib.lib.icon.Color4I;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

// See {@code com.feed_the_beast.ftblib.client.FTBLibClientEventHandler.GuiButtonSidebarGroup}
public class UTGuiHandyBagSidebarButtonGroup extends GuiButton
{
    private final GuiContainer gui;
    private final List<SidebarButtonEntry> buttons;
    private SidebarButtonEntry mouseOver;

    public UTGuiHandyBagSidebarButtonGroup(GuiContainer gui)
    {
        super(495829, 0, 0, 0, 0, "");

        this.gui = gui;
        this.buttons = new ArrayList<>();
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
        this.buttons.clear();
        this.mouseOver = null;
        int buttonX;
        int buttonY = 0;
        boolean addedAny;
        boolean top = FTBLibClientConfig.action_buttons.top() || !mc.player.getActivePotionEffects().isEmpty();

        for (SidebarButtonGroup group : SidebarButtonManager.INSTANCE.groups)
        {
            buttonX = 0;
            addedAny = false;

            for (SidebarButton button : group.getButtons())
            {
                if (button.isActuallyVisible())
                {
                    this.buttons.add(new SidebarButtonEntry(buttonX, buttonY, button));
                    buttonX++;
                    addedAny = true;
                }
            }

            if (addedAny)
            {
                buttonY++;
            }
        }

        if (this.buttons.isEmpty())
        {
            this.visible = false;
            FTBLibClientEventHandler.lastDrawnArea = new Rectangle();
            return;
        }

        this.visible = true;
        int guiLeft = this.gui.getGuiLeft();
        int guiTop = this.gui.getGuiTop();

        if (top)
        {
            for (SidebarButtonEntry button : this.buttons)
            {
                button.x = 1 + button.buttonX * 17;
                button.y = 1 + button.buttonY * 17;
            }
        }
        else
        {
            for (SidebarButtonEntry button : this.buttons)
            {
                button.x = guiLeft - 18 - button.buttonY * 17;
                button.y = guiTop + 8 + button.buttonX * 17;
            }
        }

        this.x = Integer.MAX_VALUE;
        this.y = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (SidebarButtonEntry button : this.buttons)
        {
            this.x = Math.min(this.x, button.x);
            this.y = Math.min(this.y, button.y);
            maxX = Math.max(maxX, button.x + 16);
            maxY = Math.max(maxY, button.y + 16);

            if (mouseX >= button.x && mouseY >= button.y && mouseX < button.x + 16 && mouseY < button.y + 16)
            {
                this.mouseOver = button;
            }
        }

        this.x -= 2;
        this.y -= 2;
        maxX += 2;
        maxY += 2;
        this.width = maxX - this.x;
        this.height = maxY - this.y;
        this.zLevel = 0.0F;

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 0.0F, 500.0F);

        FontRenderer fontRenderer = mc.fontRenderer;

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        for (SidebarButtonEntry button : this.buttons)
        {
            button.button.getIcon().draw(button.x, button.y, 16, 16);

            if (button == this.mouseOver)
            {
                Color4I.WHITE.withAlpha(33).draw(button.x, button.y, 16, 16);
            }

            if (button.button.getCustomTextHandler() != null)
            {
                String text = button.button.getCustomTextHandler().get();

                if (text.isEmpty() == false)
                {
                    int textWidth = fontRenderer.getStringWidth(text);
                    Color4I.LIGHT_RED.draw(button.x + 16 - textWidth, button.y - 1, textWidth + 1, 9);
                    fontRenderer.drawString(text, button.x + 17 - textWidth, button.y, 0xFFFFFFFF);
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
        }

        if (this.mouseOver != null)
        {
            this.drawTooltip(fontRenderer, mouseX, mouseY);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
        this.zLevel = 0.0F;

        FTBLibClientEventHandler.lastDrawnArea = new Rectangle(this.x, this.y, this.width, this.height);
    }

    private void drawTooltip(FontRenderer fontRenderer, int mouseX, int mouseY)
    {
        int tooltipX = mouseX + 10;
        int tooltipY = Math.max(3, mouseY - 9);
        List<String> tooltip = new ArrayList<>();
        tooltip.add(I18n.format(this.mouseOver.button.getLangKey()));

        if (this.mouseOver.button.getTooltipHandler() != null)
        {
            this.mouseOver.button.getTooltipHandler().accept(tooltip);
        }

        int tooltipWidth = 0;

        for (String line : tooltip)
        {
            tooltipWidth = Math.max(tooltipWidth, fontRenderer.getStringWidth(line));
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 0.0F, 500.0F);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Color4I.DARK_GRAY.draw(tooltipX - 3, tooltipY - 2, tooltipWidth + 6, 2 + tooltip.size() * 10);

        for (int i = 0; i < tooltip.size(); i++)
        {
            fontRenderer.drawString(tooltip.get(i), tooltipX, tooltipY + i * 10, 0xFFFFFFFF);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    public void onClicked()
    {
        if (this.mouseOver == null)
        {
            return;
        }

        this.mouseOver.button.onClicked(GuiScreen.isShiftKeyDown());
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
        return Mouse.getEventDWheel() == 0 && super.mousePressed(mc, mouseX, mouseY);
    }

    private static class SidebarButtonEntry
    {
        private final int buttonX;
        private final int buttonY;
        private final SidebarButton button;
        private int x;
        private int y;

        private SidebarButtonEntry(int buttonX, int buttonY, SidebarButton button)
        {
            this.buttonX = buttonX;
            this.buttonY = buttonY;
            this.button = button;
        }
    }
}