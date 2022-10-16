package mod.acgaming.hkntweaks.bugfixes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.lwjgl.opengl.GL11;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.CullFace;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.Mod;

import mod.acgaming.hkntweaks.HkNTweaks;

// Courtesy of Meldexun
@Mod.EventBusSubscriber(modid = HkNTweaks.MODID)
public class HkNBlockOverlay
{
    @SuppressWarnings("deprecation")
    public static void renderNearbyBlocks(float partialTicks)
    {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player.isPlayerSleeping())
        {
            return;
        }
        if (mc.player.isSpectator())
        {
            return;
        }

        double error = 0.175D;
        double x = mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * partialTicks;
        double y = mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * partialTicks + mc.player.getEyeHeight();
        double z = mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * partialTicks;
        AxisAlignedBB aabb = new AxisAlignedBB(x - error, y - error, z - error, x + error, y + error, z + error);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        boolean[] startedBuilding = new boolean[1];

        forEachNearbyPos(x, y, z, error, pos -> {
            IBlockState state = mc.world.getBlockState(pos);
            if (state.getRenderType() == EnumBlockRenderType.INVISIBLE)
            {
                return;
            }
            if (Arrays.stream(EnumFacing.VALUES).noneMatch(side -> state.doesSideBlockRendering(mc.world, pos, side)))
            {
                return;
            }

            List<AxisAlignedBB> aabbs = new ArrayList<>();
            state.getBlock().addCollisionBoxToList(state, mc.world, pos, aabb, aabbs, mc.player, true);
            Vec3d vec = new Vec3d(x, y, z);
            if (aabbs.stream().noneMatch(aabb1 -> aabb1.grow(error).contains(vec)))
            {
                return;
            }

            if (!startedBuilding[0])
            {
                startedBuilding[0] = true;
                bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
                bufferBuilder.setTranslation(-x, -(y - mc.player.getEyeHeight()), -z);
            }
            IBlockState state1 = state.getActualState(mc.world, pos);
            mc.getBlockRendererDispatcher().getBlockModelRenderer().renderModel(mc.world, mc.getBlockRendererDispatcher().getModelForState(state1), state1, pos, bufferBuilder, false);
        });

        if (startedBuilding[0])
        {
            bufferBuilder.setTranslation(0, 0, 0);

            GlStateManager.cullFace(CullFace.FRONT);

            tessellator.draw();
            bufferBuilder.reset();

            GlStateManager.cullFace(CullFace.BACK);

            if (OpenGlHelper.useVbo())
            {
                GlStateManager.glEnableClientState(GL11.GL_VERTEX_ARRAY);
                OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                GlStateManager.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
                OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
                GlStateManager.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
                OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                GlStateManager.glEnableClientState(GL11.GL_COLOR_ARRAY);
            }
        }
    }

    private static void forEachNearbyPos(double x, double y, double z, double error, Consumer<BlockPos> action)
    {
        int bx = MathHelper.floor(x);
        int by = MathHelper.floor(y);
        int bz = MathHelper.floor(z);
        MutableBlockPos pos = new MutableBlockPos();
        int used = 0;
        for (int i = 0; i < 8; i++)
        {
            double x1 = x + (((i >> 1) & 2) - 1) * error;
            double y1 = y + ((i & 2) - 1) * error;
            double z1 = z + (((i << 1) & 2) - 1) * error;
            pos.setPos(x1, y1, z1);
            int mask = 1 << (((pos.getX() + 1 - bx) * 3 + (pos.getY() + 1 - by)) * 3 + (pos.getZ() + 1 - bz));
            if ((used & mask) != 0)
            {
                continue;
            }
            used |= mask;
            action.accept(pos);
        }
    }
}