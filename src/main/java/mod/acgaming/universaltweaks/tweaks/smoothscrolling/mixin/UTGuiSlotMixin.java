package mod.acgaming.universaltweaks.tweaks.smoothscrolling.mixin;

import org.lwjgl.input.Mouse;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;

import mod.acgaming.universaltweaks.tweaks.smoothscrolling.UTSmoothScrolling;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of shedaniel
@Mixin(GuiSlot.class)
public abstract class UTGuiSlotMixin
{
    @Shadow
    public int bottom;

    @Shadow
    public int top;

    @Shadow
    protected float amountScrolled;

    @Unique
    protected float target;

    @Unique
    protected long start;

    @Unique
    protected long duration;

    @Unique
    protected int lastContentHeight = -1;

    @Shadow
    @Final
    protected Minecraft mc;

    @Shadow
    public abstract int getMaxScroll();

    @Shadow
    public abstract boolean getEnabled();

    @Shadow
    public abstract int getAmountScrolled();

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiSlot;bindAmountScrolled()V"))
    public void utBindAmountScrolled(GuiSlot guiSlot)
    {
        amountScrolled = UTSmoothScrolling.clamp(amountScrolled, getMaxScroll());
        target = UTSmoothScrolling.clamp(target, getMaxScroll());
    }

    @Inject(method = "handleMouseInput", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;getEventDWheel()I", remap = false), cancellable = true)
    public void utHandleMouseScroll(CallbackInfo callbackInfo)
    {
        if (Mouse.isButtonDown(0) && this.getEnabled()) target = amountScrolled = UTSmoothScrolling.clamp(amountScrolled, getMaxScroll(), 0);
        else
        {
            int wheel = Mouse.getEventDWheel();
            if (wheel != 0)
            {
                if (wheel > 0) wheel = -1;
                else if (wheel < 0) wheel = 1;
                offset(UTSmoothScrolling.getScrollStep() * wheel, true);
            }
            callbackInfo.cancel();
        }
    }

    @Unique
    public void offset(float value, boolean animated)
    {
        scrollTo(target + value, animated);
    }

    @Unique
    public void scrollTo(float value, boolean animated)
    {
        scrollTo(value, animated, UTSmoothScrolling.getScrollDuration());
    }

    @Unique
    public void scrollTo(float value, boolean animated, long duration)
    {
        target = UTSmoothScrolling.clamp(value, getMaxScroll());
        if (animated)
        {
            start = System.currentTimeMillis();
            this.duration = duration;
        }
        else amountScrolled = target;
    }

    @Inject(method = "drawScreen", at = @At("HEAD"))
    public void utRender(int int_1, int int_2, float delta, CallbackInfo callbackInfo)
    {
        float[] target = new float[] {this.target};
        this.amountScrolled = UTSmoothScrolling.handleScrollingPosition(target, this.amountScrolled, this.getMaxScroll(), 20f / Minecraft.getDebugFPS(), (double) this.start, (double) this.duration);
        this.target = target[0];
        if (lastContentHeight != getContentHeight())
        {
            if (lastContentHeight != -1) amountScrolled = this.target = UTSmoothScrolling.clamp(this.target, getContentHeight(), 0);
            lastContentHeight = getContentHeight();
        }
    }

    @Inject(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiSlot;getMaxScroll()I", ordinal = 0, shift = At.Shift.AFTER), cancellable = true)
    public void utRenderScrollbar(int int_1, int int_2, float float_1, CallbackInfo callbackInfo)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        int scrollbarPositionMinX = this.getScrollBarX();
        int scrollbarPositionMaxX = scrollbarPositionMinX + 6;
        int maxScroll = this.getMaxScroll();
        if (maxScroll > 0)
        {
            int height = (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight();
            height = MathHelper.clamp(height, 32, this.bottom - this.top - 8);
            height = (int) ((double) height - Math.min(this.amountScrolled < 0.0D ? (int) (-this.amountScrolled) : (this.amountScrolled > (double) this.getMaxScroll() ? (int) this.amountScrolled - this.getMaxScroll() : 0), (double) height * 0.75D));
            int minY = Math.min(Math.max(this.getAmountScrolled() * (this.bottom - this.top - height) / maxScroll + this.top, this.top), this.bottom - height);
            buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            buffer.pos(scrollbarPositionMinX, this.bottom, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
            buffer.pos(scrollbarPositionMaxX, this.bottom, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
            buffer.pos(scrollbarPositionMaxX, this.top, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
            buffer.pos(scrollbarPositionMinX, this.top, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
            tessellator.draw();
            buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            buffer.pos(scrollbarPositionMinX, minY + height, 0.0D).tex(0.0D, 1.0D).color(128, 128, 128, 255).endVertex();
            buffer.pos(scrollbarPositionMaxX, minY + height, 0.0D).tex(1.0D, 1.0D).color(128, 128, 128, 255).endVertex();
            buffer.pos(scrollbarPositionMaxX, minY, 0.0D).tex(1.0D, 0.0D).color(128, 128, 128, 255).endVertex();
            buffer.pos(scrollbarPositionMinX, minY, 0.0D).tex(0.0D, 0.0D).color(128, 128, 128, 255).endVertex();
            tessellator.draw();
            buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            buffer.pos(scrollbarPositionMinX, minY + height - 1, 0.0D).tex(0.0D, 1.0D).color(192, 192, 192, 255).endVertex();
            buffer.pos(scrollbarPositionMaxX - 1, minY + height - 1, 0.0D).tex(1.0D, 1.0D).color(192, 192, 192, 255).endVertex();
            buffer.pos(scrollbarPositionMaxX - 1, minY, 0.0D).tex(1.0D, 0.0D).color(192, 192, 192, 255).endVertex();
            buffer.pos(scrollbarPositionMinX, minY, 0.0D).tex(0.0D, 0.0D).color(192, 192, 192, 255).endVertex();
            tessellator.draw();
        }
        this.renderDecorations(int_1, int_2);
        GlStateManager.enableTexture2D();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        callbackInfo.cancel();
    }

    @Shadow
    protected abstract int getScrollBarX();

    @Shadow
    protected abstract int getContentHeight();

    @Shadow
    protected abstract void renderDecorations(int mouseXIn, int mouseYIn);
}