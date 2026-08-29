package mod.acgaming.universaltweaks.tweaks.misc.moderndebugrender;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class UTModernDebugRender
{
    public static final float STROKE_WIDTH = 2.5F;
    public static final float THICK_WIDTH = 4.0F;
    public static final float THIN_WIDTH = 1.0F;

    @Nullable
    public static Vec3d cameraPos(float partialTicks)
    {
        Entity camera = Minecraft.getMinecraft().getRenderViewEntity();
        if (camera == null) return null;
        return new Vec3d(
            camera.lastTickPosX + (camera.posX - camera.lastTickPosX) * partialTicks,
            camera.lastTickPosY + (camera.posY - camera.lastTickPosY) * partialTicks,
            camera.lastTickPosZ + (camera.posZ - camera.lastTickPosZ) * partialTicks);
    }

    // Start of the 16 block cell the coordinate falls into, relative to the coordinate itself
    public static double sectionOrigin(double coord)
    {
        return (MathHelper.floor(coord) >> 4 << 4) - coord;
    }

    public static void drawArrowHead(Vec3d start, Vec3d end, float red, float green, float blue)
    {
        Vec3d shaft = end.subtract(start);
        double length = shaft.length();
        if (length < 1.0E-5D) return;

        Vec3d forward = shaft.scale(1.0D / length);
        double barb = MathHelper.clamp(length * 0.1D, 0.1D, 1.0D);
        // The seed only has to be non-parallel to the shaft to yield a usable perpendicular
        Vec3d seed = Math.abs(forward.y) > 0.999D ? new Vec3d(1.0D, 0.0D, 0.0D) : new Vec3d(0.0D, 1.0D, 0.0D);
        Vec3d sideA = forward.crossProduct(seed).normalize().scale(barb);
        // forward is unit length and perpendicular to sideA, so this product is already barb long
        Vec3d sideB = forward.crossProduct(sideA);
        Vec3d base = end.subtract(forward.scale(barb));

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        addLine(buffer, base.add(sideA), end, red, green, blue);
        addLine(buffer, base.subtract(sideA), end, red, green, blue);
        addLine(buffer, base.add(sideB), end, red, green, blue);
        addLine(buffer, base.subtract(sideB), end, red, green, blue);
        tessellator.draw();
    }

    public static void drawPoint(double x, double y, double z, float red, float green, float blue)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        GL11.glPointSize(2.0F);
        buffer.begin(GL11.GL_POINTS, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(x, y, z).color(red, green, blue, 1.0F).endVertex();
        tessellator.draw();
        GL11.glPointSize(1.0F);
    }

    private static void addLine(BufferBuilder buffer, Vec3d start, Vec3d end, float red, float green, float blue)
    {
        buffer.pos(start.x, start.y, start.z).color(red, green, blue, 1.0F).endVertex();
        buffer.pos(end.x, end.y, end.z).color(red, green, blue, 1.0F).endVertex();
    }
}