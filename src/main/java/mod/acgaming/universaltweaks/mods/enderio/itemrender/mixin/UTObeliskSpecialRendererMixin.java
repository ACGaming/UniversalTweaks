package mod.acgaming.universaltweaks.mods.enderio.itemrender.mixin;

import java.util.Random;
import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.enderio.core.client.render.ManagedTESR;
import com.enderio.core.client.render.RenderUtil;
import com.enderio.core.common.TileEntityBase;
import crazypants.enderio.base.EnderIO;
import crazypants.enderio.machines.machine.obelisk.render.ObeliskSpecialRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

// Courtesy of jchung01
@Mixin(value = ObeliskSpecialRenderer.class, remap = false)
public abstract class UTObeliskSpecialRendererMixin<T extends TileEntityBase> extends ManagedTESR<T>
{
    @Shadow
    @Final
    private Random rand;

    @Shadow
    protected abstract ItemStack getFloatingItem(T te);

    protected UTObeliskSpecialRendererMixin(Block block)
    {
        super(block);
    }

    /**
     * Use simple RenderItem instead of RenderEntityItem.
     * @reason Don't use rei/entityItem which retains the first WorldClient.
     * @author jchung01
     */
    @Overwrite
    protected void renderItemStack(T te, @Nonnull World world, double x, double y, double z, float tick)
    {
        // CHANGE START: Don't init enityItem
        /*
        EntityItem ei = this.enityItem;
        if (ei == null) {
            this.enityItem = ei = new EntityItem(world, 0, 0, 0, getFloatingItem(te));
        }
        ei.setItem(getFloatingItem(te));
        ei.hoverStart = (float) ((EnderIO.proxy.getTickCount() * 0.05f + (tick * 0.05f)) % (Math.PI * 2));
         */
        // CHANGE END
        RenderUtil.bindBlockTexture();

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5, y + 0.7, z + 0.5);
        GlStateManager.scale(1.1f, 1.1f, 1.1f);

        // CHANGE START: Simplify seed and remove allocation
        if (te != null)
        {
            BlockPos p = te.getPos();
            this.rand.setSeed(p.getX() + p.getY() + p.getZ());
        }
        else
        {
            this.rand.setSeed(0);
        }
        // CHANGE END

        this.rand.nextBoolean();
        if (Minecraft.getMinecraft().gameSettings.fancyGraphics) {
            GlStateManager.rotate(rand.nextFloat() * 360f, 0, 1, 0);
        }
        // CHANGE START: Don't use enityItem and use built-in RenderItem
        /*
        ei.hoverStart += rand.nextFloat();

        GlStateManager.translate(0, -0.15f, 0);
        if (rei == null) {
            rei = new InnerRenderEntityItem(Minecraft.getMinecraft().getRenderManager(), Minecraft.getMinecraft().getRenderItem());
        }
        rei.doRender(ei, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
         */
        float timeDelta = ((EnderIO.proxy.getTickCount() + tick) / 20.0f) * (180 / (float) Math.PI);
        GlStateManager.rotate(timeDelta, 0F, 1F, 0F);
        // An approximate shift, not exactly the same as original
        GlStateManager.translate(0, -0.025f, 0);
        Minecraft.getMinecraft().getRenderItem().renderItem(getFloatingItem(te), ItemCameraTransforms.TransformType.GROUND);
        // CHANGE END

        GlStateManager.popMatrix();
    }
}
