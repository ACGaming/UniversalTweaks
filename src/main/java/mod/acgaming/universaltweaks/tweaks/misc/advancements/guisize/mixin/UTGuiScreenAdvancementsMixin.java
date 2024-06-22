package mod.acgaming.universaltweaks.tweaks.misc.advancements.guisize.mixin;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.advancements.GuiAdvancementTab;
import net.minecraft.client.gui.advancements.GuiScreenAdvancements;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.tweaks.misc.advancements.guisize.UTAdvancementInfo;

// Courtesy of WaitingIdly
@Mixin(GuiScreenAdvancements.class)
public abstract class UTGuiScreenAdvancementsMixin extends GuiScreen
{
    @Unique
    private static final int ARROW_ADJUSTMENT = 22;

    @Shadow(remap = false)
    private static int tabPage;
    @Shadow
    private GuiAdvancementTab selectedTab;

    /**
     * @reason adjust the maximum number of tabs from a final static field to a number based on the height and width
     */
    @WrapOperation(method = "initGui", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/advancements/AdvancementTabType;MAX_TABS:I"))
    private int utAdjustMaxTabs(Operation<Integer> original)
    {
        if (!UTConfigTweaks.MISC.ADVANCEMENTS.utAdvancementsToggle || !UTConfigTweaks.MISC.ADVANCEMENTS.utSizeToggle) return original.call();
        return UTAdvancementInfo.utMaximumTabCountPerPage(width, height);
    }

    /**
     * @reason set the current page to that of the focused tab
     */
    @Inject(method = "initGui", at = @At("TAIL"))
    private void utFocusActiveTab(CallbackInfo ci)
    {
        if (!UTConfigTweaks.MISC.ADVANCEMENTS.utAdvancementsToggle || !UTConfigTweaks.MISC.ADVANCEMENTS.utSizeToggle) return;
        tabPage = selectedTab.getPage();
    }

    /**
     * @reason prevent displaying the page number or total, as there is nowhere to put it and it goes offscreen
     */
    @WrapOperation(method = "drawScreen", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/advancements/GuiScreenAdvancements;maxPages:I", remap = false))
    private int utDisableMaxPageText(Operation<Integer> original)
    {
        if (!UTConfigTweaks.MISC.ADVANCEMENTS.utAdvancementsToggle || !UTConfigTweaks.MISC.ADVANCEMENTS.utHidePageHeader)
        {
            return original.call();
        }
        return 0;
    }

    /**
     * @reason move the left arrow from offscreen into the corner
     */
    @ModifyArg(method = "initGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiButton;<init>(IIIIILjava/lang/String;)V", ordinal = 0), index = 1)
    private int utAdjustArrowLeft(int original)
    {
        if (!UTConfigTweaks.MISC.ADVANCEMENTS.utAdvancementsToggle || !UTConfigTweaks.MISC.ADVANCEMENTS.utMoveArrowButtons)
        {
            return original;
        }
        return original - ARROW_ADJUSTMENT;
    }

    /**
     * @reason move the right arrow from offscreen into the corner
     */
    @ModifyArg(method = "initGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiButton;<init>(IIIIILjava/lang/String;)V", ordinal = 1), index = 1)
    private int utAdjustArrowRight(int original)
    {
        if (!UTConfigTweaks.MISC.ADVANCEMENTS.utAdvancementsToggle || !UTConfigTweaks.MISC.ADVANCEMENTS.utMoveArrowButtons)
        {
            return original;
        }
        return original + ARROW_ADJUSTMENT;
    }

    /**
     * @reason move both arrows from offscreen into the corner
     */
    @ModifyConstant(method = "initGui", constant = @Constant(intValue = 50))
    private int utVerticalOffset(int original)
    {
        if (!UTConfigTweaks.MISC.ADVANCEMENTS.utAdvancementsToggle || !UTConfigTweaks.MISC.ADVANCEMENTS.utMoveArrowButtons)
        {
            return original;
        }
        return ARROW_ADJUSTMENT;
    }

    /**
     * @reason adjust the location used for the width
     */
    @ModifyConstant(method = {"initGui", "mouseClicked", "drawScreen"}, constant = @Constant(intValue = 252))
    private int utAdjustWidthPosition(int original)
    {
        if (!UTConfigTweaks.MISC.ADVANCEMENTS.utAdvancementsToggle || !UTConfigTweaks.MISC.ADVANCEMENTS.utSizeToggle) return original;
        return UTAdvancementInfo.utPageWidth(width) - 4;
    }

    /**
     * @reason adjust the location used to indicate half the width for drawing the header text
     */
    @ModifyConstant(method = "drawScreen", constant = @Constant(intValue = 126))
    private int utAdjustHalfWidthPosition(int original)
    {
        if (!UTConfigTweaks.MISC.ADVANCEMENTS.utAdvancementsToggle || !UTConfigTweaks.MISC.ADVANCEMENTS.utSizeToggle) return original;
        return (UTAdvancementInfo.utPageWidth(width) - 4) / 2;
    }

    /**
     * @reason adjust the location used for the height
     */
    @ModifyConstant(method = {"initGui", "mouseClicked", "drawScreen"}, constant = @Constant(intValue = 140))
    private int utAdjustHeightPosition(int original)
    {
        if (!UTConfigTweaks.MISC.ADVANCEMENTS.utAdvancementsToggle || !UTConfigTweaks.MISC.ADVANCEMENTS.utSizeToggle) return original;
        return UTAdvancementInfo.utPageHeight(height);
    }

    /**
     * @reason adjust the location used for the width when padded
     */
    @ModifyConstant(method = "renderInside", constant = @Constant(intValue = 234))
    private int utRenderWidthPosition(int original)
    {
        if (!UTConfigTweaks.MISC.ADVANCEMENTS.utAdvancementsToggle || !UTConfigTweaks.MISC.ADVANCEMENTS.utSizeToggle) return original;
        return UTAdvancementInfo.utPageWidth(width, 2) - 4;
    }

    /**
     * @reason adjust the location used for the height when padded
     */
    @ModifyConstant(method = "renderInside", constant = @Constant(intValue = 113))
    private int utRenderHeightPosition(int original)
    {
        if (!UTConfigTweaks.MISC.ADVANCEMENTS.utAdvancementsToggle || !UTConfigTweaks.MISC.ADVANCEMENTS.utSizeToggle) return original;
        return UTAdvancementInfo.utPageHeight(height, 3);
    }

    /**
     * @reason render the advancement display in an expandable way, cutting and repeatedly rendering some sections to
     * extend the size
     */
    @WrapOperation(method = "renderWindow", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/advancements/GuiScreenAdvancements;drawTexturedModalRect(IIIIII)V"))
    private void utRenderLargerWindow(GuiScreenAdvancements instance, int left, int top, int textureX, int textureY, int w, int h, Operation<Void> original)
    {
        if (!UTConfigTweaks.MISC.ADVANCEMENTS.utAdvancementsToggle || !UTConfigTweaks.MISC.ADVANCEMENTS.utSizeToggle)
        {
            original.call(instance, left, top, textureX, textureY, w, h);
            return;
        }

        // default texture location information
        final int texture_width = 252;
        final int texture_height = 140;
        final int texture_corner = 30;
        // location to draw to
        final int width = UTAdvancementInfo.utPageWidth(this.width) - 4;
        final int height = UTAdvancementInfo.utPageHeight(this.height);
        final int right = width + (this.width - width) / 2;
        final int bottom = height + (this.height - height) / 2;

        // draw components clockwise, from top left:

        // corner: top left
        drawTexturedModalRect(left, top, 0, 0, texture_corner, texture_corner);
        // side: top
        utRenderTextureRepeating(left + texture_corner, top, width - texture_corner * 2, texture_corner, texture_corner, 0, texture_width - texture_corner * 2, texture_corner);
        // corner: top right
        drawTexturedModalRect(right - texture_corner, top, texture_width - texture_corner, 0, texture_corner, texture_corner);
        // side: right
        utRenderTextureRepeating(right - texture_corner, top + texture_corner, texture_corner, bottom - top - texture_corner * 2, texture_width - texture_corner, texture_corner, texture_corner, texture_height - texture_corner * 2);
        // corner: bottom left
        drawTexturedModalRect(left, bottom - texture_corner, 0, texture_height - texture_corner, texture_corner, texture_corner);
        // side: bottom
        utRenderTextureRepeating(left + texture_corner, bottom - texture_corner, width - texture_corner * 2, texture_corner, texture_corner, texture_height - texture_corner, texture_width - texture_corner * 2, texture_corner);
        // corner: bottom right
        drawTexturedModalRect(right - texture_corner, bottom - texture_corner, texture_width - texture_corner, texture_height - texture_corner, texture_corner, texture_corner);
        // side: left
        utRenderTextureRepeating(left, top + texture_corner, texture_corner, bottom - top - texture_corner * 2, 0, texture_corner, texture_corner, texture_height - texture_corner * 2);
    }

    @Unique
    private void utRenderTextureRepeating(int x, int y, int width, int height, int textureX, int textureY, int textureWidth, int textureHeight)
    {
        for (int w = 0; w < width; w += textureWidth)
        {
            int drawX = x + w;
            int drawWidth = Math.min(textureWidth, width - w);
            for (int h = 0; h < height; h += textureHeight)
            {
                int drawY = y + h;
                int drawHeight = Math.min(textureHeight, height - h);
                this.drawTexturedModalRect(drawX, drawY, textureX, textureY, drawWidth, drawHeight);
            }
        }
    }

}
