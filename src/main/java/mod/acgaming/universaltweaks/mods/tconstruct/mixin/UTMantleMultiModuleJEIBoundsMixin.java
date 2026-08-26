package mod.acgaming.universaltweaks.mods.tconstruct.mixin;

import java.awt.Rectangle;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.mantle.client.gui.GuiModule;
import slimeknights.mantle.client.gui.GuiMultiModule;

/**
 * Keeps JEI's main GUI bounds on Mantle's central panel while preserving input
 * handling for any side modules. This allows JEI wrapping nicely around Mantle's
 * GUIs, instead of squashing it to the sides and leaving a lot of empty space.
 */
@Mixin(value = GuiMultiModule.class, remap = false)
public abstract class UTMantleMultiModuleJEIBoundsMixin extends GuiContainer
{
    protected UTMantleMultiModuleJEIBoundsMixin(Container inventorySlotsIn)
    {
        super(inventorySlotsIn);
    }

    @Shadow
    protected List<GuiModule> modules;
    @Shadow
    public int cornerX;
    @Shadow
    public int cornerY;
    @Shadow
    public int realWidth;
    @Shadow
    public int realHeight;

    @Unique
    private int ut$savedGuiLeft;
    @Unique
    private int ut$savedGuiTop;
    @Unique
    private int ut$savedXSize;
    @Unique
    private int ut$savedYSize;
    @Unique
    private boolean ut$moduleBoundsExpanded;

    @Unique
    private void ut$resetToRealBounds()
    {
        guiLeft = cornerX;
        guiTop = cornerY;
        xSize = realWidth;
        ySize = realHeight;
    }

    @Unique
    private void ut$expandToModuleBounds()
    {
        if (ut$moduleBoundsExpanded) return;

        ut$savedGuiLeft = guiLeft;
        ut$savedGuiTop = guiTop;
        ut$savedXSize = xSize;
        ut$savedYSize = ySize;
        ut$moduleBoundsExpanded = true;

        int minX = cornerX;
        int minY = cornerY;
        int maxX = cornerX + realWidth;
        int maxY = cornerY + realHeight;
        for (GuiModule module : modules)
        {
            minX = Math.min(minX, module.guiLeft);
            minY = Math.min(minY, module.guiTop);
            maxX = Math.max(maxX, module.guiRight());
            maxY = Math.max(maxY, module.guiBottom());
        }

        guiLeft = minX;
        guiTop = minY;
        xSize = maxX - minX;
        ySize = maxY - minY;
    }

    @Unique
    private void ut$restoreBoundsAfterMouse()
    {
        if (!ut$moduleBoundsExpanded) return;

        guiLeft = ut$savedGuiLeft;
        guiTop = ut$savedGuiTop;
        xSize = ut$savedXSize;
        ySize = ut$savedYSize;
        ut$moduleBoundsExpanded = false;
    }

    @Inject(method = "updateSubmodule", at = @At("TAIL"))
    private void ut$keepRealBoundsAfterModuleUpdates(GuiModule module, CallbackInfo ci)
    {
        ut$resetToRealBounds();
    }

    @Inject(method = "initGui", at = @At("TAIL"))
    private void ut$keepRealBoundsAfterInit(CallbackInfo ci)
    {
        ut$resetToRealBounds();
    }

    @Inject(method = "setWorldAndResolution", at = @At("TAIL"))
    private void ut$keepRealBoundsAfterResolution(Minecraft mc, int width, int height, CallbackInfo ci)
    {
        ut$resetToRealBounds();
    }

    @Inject(method = "onResize", at = @At("TAIL"))
    private void ut$keepRealBoundsAfterResize(Minecraft mc, int width, int height, CallbackInfo ci)
    {
        ut$resetToRealBounds();
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"))
    private void ut$expandBoundsBeforeMouseClick(int mouseX, int mouseY, int mouseButton, CallbackInfo ci)
    {
        ut$expandToModuleBounds();
    }

    @Inject(method = "mouseClicked", at = @At("RETURN"))
    private void ut$restoreBoundsAfterMouseClick(int mouseX, int mouseY, int mouseButton, CallbackInfo ci)
    {
        ut$restoreBoundsAfterMouse();
    }

    @Inject(method = "mouseClickMove", at = @At("HEAD"))
    private void ut$expandBoundsBeforeMouseDrag(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick, CallbackInfo ci)
    {
        ut$expandToModuleBounds();
    }

    @Inject(method = "mouseClickMove", at = @At("RETURN"))
    private void ut$restoreBoundsAfterMouseDrag(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick, CallbackInfo ci)
    {
        ut$restoreBoundsAfterMouse();
    }

    @Inject(method = "mouseReleased", at = @At("HEAD"))
    private void ut$expandBoundsBeforeMouseRelease(int mouseX, int mouseY, int state, CallbackInfo ci)
    {
        ut$expandToModuleBounds();
    }

    @Inject(method = "mouseReleased", at = @At("RETURN"))
    private void ut$restoreBoundsAfterMouseRelease(int mouseX, int mouseY, int state, CallbackInfo ci)
    {
        ut$restoreBoundsAfterMouse();
    }
}
