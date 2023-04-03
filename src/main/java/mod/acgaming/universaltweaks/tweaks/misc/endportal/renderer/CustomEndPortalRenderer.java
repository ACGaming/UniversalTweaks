package mod.acgaming.universaltweaks.tweaks.misc.endportal.renderer;

import java.nio.FloatBuffer;
import java.util.Random;

import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import static org.lwjgl.opengl.GL11.*;

// Courtesy of rune_smith98
public class CustomEndPortalRenderer
{
    private static final ResourceLocation END_SKY_TEXTURE = new ResourceLocation("textures/environment/end_sky.png");
    private static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");
    private static final Random RANDOM = new Random(31100L);
    private final FloatBuffer texBuffer = GLAllocation.createDirectFloatBuffer(16);

    public void render(TileEntityEndPortal portalEntity, float offsetTop, float offsetBot, double posX, double posY, double posZ, double playerX, double playerY, double playerZ, TextureManager r)
    {
        if (r == null) return;
        GlStateManager.disableLighting();
        RANDOM.setSeed(31100L);
        for (int i = 0; i < 16; i++)
        {
            GlStateManager.pushMatrix();
            float portalSurfaceY = (float) (posY + offsetTop);
            float layerDepth = 16 - i;
            float layerScale = 0.0625F;
            float layerColorStrength = 1.0F / (layerDepth + 1.0F);
            if (i == 0)
            {
                r.bindTexture(END_SKY_TEXTURE);
                layerColorStrength = 0.1F;
                layerDepth = 65F;
                layerScale = 0.125F;
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            }
            if (i == 1)
            {
                r.bindTexture(END_PORTAL_TEXTURE);
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
                layerScale = 0.5F;
            }
            float f8 = -portalSurfaceY;
            float f9 = (float) (f8 + ActiveRenderInfo.getCameraPosition().y);
            float f10 = (float) (f8 + layerDepth + ActiveRenderInfo.getCameraPosition().y);
            float f11 = (f9 / f10) + portalSurfaceY;
            GlStateManager.translate(playerX, f11, playerZ);
            GlStateManager.texGen(GlStateManager.TexGen.S, GL_OBJECT_LINEAR);
            GlStateManager.texGen(GlStateManager.TexGen.T, GL_OBJECT_LINEAR);
            GlStateManager.texGen(GlStateManager.TexGen.R, GL_OBJECT_LINEAR);
            GlStateManager.texGen(GlStateManager.TexGen.Q, GL_EYE_LINEAR);
            GlStateManager.texGen(GlStateManager.TexGen.S, GL_OBJECT_PLANE, this.bufferTexData(1.0F, 0.0F, 0.0F, 0.0F));
            GlStateManager.texGen(GlStateManager.TexGen.T, GL_OBJECT_PLANE, this.bufferTexData(0.0F, 0.0F, 1.0F, 0.0F));
            GlStateManager.texGen(GlStateManager.TexGen.R, GL_OBJECT_PLANE, this.bufferTexData(0.0F, 0.0F, 0.0F, 1.0F));
            GlStateManager.texGen(GlStateManager.TexGen.Q, GL_EYE_PLANE, this.bufferTexData(0.0F, 1.0F, 0.0F, 0.0F));
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.S);
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.T);
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.R);
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.Q);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(GL_TEXTURE);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.0F, System.currentTimeMillis() % 700000L / 700000F, 0.0F);
            GlStateManager.scale(layerScale, layerScale, layerScale);
            GlStateManager.translate(0.5F, 0.5F, 0.0F);
            GlStateManager.rotate((i * i * 4321 + i * 9) * 2.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(-0.5F, -0.5F, 0.0F);
            GlStateManager.translate(-playerX, -playerZ, -playerY);
            f9 = f8 + (float) ActiveRenderInfo.getCameraPosition().y;
            GlStateManager.translate(((float) ActiveRenderInfo.getCameraPosition().x * layerDepth) / f9, ((float) ActiveRenderInfo.getCameraPosition().z * layerDepth) / f9, -playerY + 20);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();
            buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
            float red = (RANDOM.nextFloat() * 0.5F + 0.1F) * layerColorStrength;
            float green = (RANDOM.nextFloat() * 0.5F + 0.4F) * layerColorStrength;
            float blue = (RANDOM.nextFloat() * 0.5F + 0.5F) * layerColorStrength;
            if (i == 0) red = green = blue = layerColorStrength;
            if (portalEntity.shouldRenderFace(EnumFacing.SOUTH))
            {
                buffer.pos(posX, posY + offsetBot, posZ + 1.0).color(red, green, blue, 1.0F).endVertex();
                buffer.pos(posX + 1.0, posY + offsetBot, posZ + 1.0).color(red, green, blue, 1.0F).endVertex();
                buffer.pos(posX + 1.0, posY + offsetTop, posZ + 1.0).color(red, green, blue, 1.0F).endVertex();
                buffer.pos(posX, posY + offsetTop, posZ + 1.0).color(red, green, blue, 1.0F).endVertex();
            }
            if (portalEntity.shouldRenderFace(EnumFacing.NORTH))
            {
                buffer.pos(posX, posY + offsetTop, posZ).color(red, green, blue, 1.0F).endVertex();
                buffer.pos(posX + 1.0, posY + offsetTop, posZ).color(red, green, blue, 1.0F).endVertex();
                buffer.pos(posX + 1.0, posY + offsetBot, posZ).color(red, green, blue, 1.0F).endVertex();
                buffer.pos(posX, posY + offsetBot, posZ).color(red, green, blue, 1.0F).endVertex();
            }
            if (portalEntity.shouldRenderFace(EnumFacing.EAST))
            {
                buffer.pos(posX + 1.0, posY + offsetTop, posZ).color(red, green, blue, 1.0F).endVertex();
                buffer.pos(posX + 1.0, posY + offsetTop, posZ + 1.0).color(red, green, blue, 1.0F).endVertex();
                buffer.pos(posX + 1.0, posY + offsetBot, posZ + 1.0).color(red, green, blue, 1.0F).endVertex();
                buffer.pos(posX + 1.0, posY + offsetBot, posZ).color(red, green, blue, 1.0F).endVertex();
            }
            if (portalEntity.shouldRenderFace(EnumFacing.WEST))
            {
                buffer.pos(posX, posY + offsetBot, posZ).color(red, green, blue, 1.0F).endVertex();
                buffer.pos(posX, posY + offsetBot, posZ + 1.0).color(red, green, blue, 1.0F).endVertex();
                buffer.pos(posX, posY + offsetTop, posZ + 1.0).color(red, green, blue, 1.0F).endVertex();
                buffer.pos(posX, posY + offsetTop, posZ).color(red, green, blue, 1.0F).endVertex();
            }
            if (portalEntity.shouldRenderFace(EnumFacing.DOWN))
            {
                buffer.pos(posX, posY + offsetBot, posZ).color(red, green, blue, 1.0F).endVertex();
                buffer.pos(posX + 1.0, posY + offsetBot, posZ).color(red, green, blue, 1.0F).endVertex();
                buffer.pos(posX + 1.0, posY + offsetBot, posZ + 1.0).color(red, green, blue, 1.0F).endVertex();
                buffer.pos(posX, posY + offsetBot, posZ + 1.0).color(red, green, blue, 1.0F).endVertex();
            }
            if (portalEntity.shouldRenderFace(EnumFacing.UP))
            {
                buffer.pos(posX, posY + offsetTop, posZ + 1.0).color(red, green, blue, 1.0F).endVertex();
                buffer.pos(posX + 1.0, posY + offsetTop, posZ + 1.0).color(red, green, blue, 1.0F).endVertex();
                buffer.pos(posX + 1.0, posY + offsetTop, posZ).color(red, green, blue, 1.0F).endVertex();
                buffer.pos(posX, posY + offsetTop, posZ).color(red, green, blue, 1.0F).endVertex();
            }
            tessellator.draw();
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(GL_MODELVIEW);
        }
        GlStateManager.disableBlend();
        GlStateManager.disableTexGenCoord(GlStateManager.TexGen.S);
        GlStateManager.disableTexGenCoord(GlStateManager.TexGen.T);
        GlStateManager.disableTexGenCoord(GlStateManager.TexGen.R);
        GlStateManager.disableTexGenCoord(GlStateManager.TexGen.Q);
        GlStateManager.enableLighting();
    }

    private FloatBuffer bufferTexData(float f, float f1, float f2, float f3)
    {
        texBuffer.clear();
        texBuffer.put(f).put(f1).put(f2).put(f3);
        texBuffer.flip();
        return texBuffer;
    }
}