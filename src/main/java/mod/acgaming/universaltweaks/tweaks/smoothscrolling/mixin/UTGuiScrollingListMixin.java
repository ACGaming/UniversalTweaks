package mod.acgaming.universaltweaks.tweaks.smoothscrolling.mixin;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.GuiScrollingList;

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
@Mixin(value = GuiScrollingList.class, remap = false)
public abstract class UTGuiScrollingListMixin
{
    @Shadow
    @Final
    protected int bottom;

    @Shadow
    @Final
    protected int top;

    @Unique
    protected float target;

    @Unique
    protected long start;

    @Unique
    protected long duration;

    @Shadow
    @Final
    protected int left;

    @Shadow
    @Final
    protected int listWidth;

    @Unique
    protected int lastContentHeight = -1;

    @Shadow
    private float scrollDistance;

    @Redirect(method = "handleMouseInput(II)V", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;getEventDWheel()I", ordinal = 0))
    public int utGetEventDWheel(int mouseX, int mouseY)
    {
        int wheel = Mouse.getEventDWheel();
        if (wheel != 0)
        {
            if (wheel > 0) wheel = -1;
            else if (wheel < 0) wheel = 1;
            offset(UTSmoothScrolling.getScrollStep() * wheel, true);
        }
        return 0;
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
        target = UTSmoothScrolling.clamp(value, func_148135_f());
        if (animated)
        {
            start = System.currentTimeMillis();
            this.duration = duration;
        }
        else scrollDistance = target;
    }

    @Redirect(method = "drawScreen(IIF)V", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/client/GuiScrollingList;applyScrollLimits()V"))
    public void utBindScrollDistance(GuiScrollingList guiSlot)
    {
        if (Mouse.isButtonDown(0)) target = scrollDistance = UTSmoothScrolling.clamp(scrollDistance, func_148135_f(), 0);
    }

    @Inject(method = "drawScreen(IIF)V", at = @At("HEAD"))
    public void utRender(int int_1, int int_2, float delta, CallbackInfo callbackInfo)
    {
        float[] target = new float[] {this.target};
        this.scrollDistance = UTSmoothScrolling.handleScrollingPosition(target, this.scrollDistance, this.func_148135_f(), 20f / Minecraft.getDebugFPS(), (double) this.start, (double) this.duration);
        this.target = target[0];
        if (lastContentHeight != getContentHeight())
        {
            if (lastContentHeight != -1) scrollDistance = this.target = UTSmoothScrolling.clamp(this.target, func_148135_f(), 0);
            lastContentHeight = getContentHeight();
        }
    }

    @Inject(method = "drawScreen(IIF)V", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/client/GuiScrollingList;getContentHeight()I", ordinal = 2, shift = At.Shift.AFTER), cancellable = true)
    public void utRenderScrollbar(int int_1, int int_2, float float_1, CallbackInfo callbackInfo)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        int scrollbarPositionMaxX = this.left + this.listWidth;
        int scrollbarPositionMinX = scrollbarPositionMaxX - 6;
        int maxScroll = this.func_148135_f();
        if (maxScroll > 0)
        {
            int height = (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight();
            height = MathHelper.clamp(height, 32, this.bottom - this.top - 8);
            height = (int) ((double) height - Math.min(this.scrollDistance < 0.0D ? (int) (-this.scrollDistance) : (this.scrollDistance > (double) this.func_148135_f() ? (int) this.scrollDistance - this.func_148135_f() : 0), (double) height * 0.75D));
            int minY = Math.min(Math.max(((int) scrollDistance) * (this.bottom - this.top - height) / maxScroll + this.top, this.top), this.bottom - height);
            GlStateManager.disableTexture2D();
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
        this.drawScreen(int_1, int_2);
        GlStateManager.enableTexture2D();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        callbackInfo.cancel();
    }

    @Shadow
    protected abstract int getContentHeight();

    @Shadow
    protected abstract void drawScreen(int mouseX, int mouseY);

    @Unique
    private int func_148135_f()
    {
        int max = this.getContentHeight() - (this.bottom - this.top) + 4;
        if (max < 0) max /= 2;
        return max;
    }
}