package mod.acgaming.universaltweaks.mods.ironchests.mixin;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelShulker;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import cpw.mods.ironchest.client.renderer.shulker.TileEntityIronShulkerBoxRenderer;
import cpw.mods.ironchest.common.blocks.shulker.BlockIronShulkerBox;
import cpw.mods.ironchest.common.blocks.shulker.IronShulkerBoxType;
import cpw.mods.ironchest.common.tileentity.shulker.TileEntityIronShulkerBox;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

// Courtesy of jchung01
@Mixin(value = TileEntityIronShulkerBoxRenderer.class, remap = false)
public class UTTileEntityIronShulkerBoxRendererMixin extends TileEntitySpecialRenderer<TileEntityIronShulkerBox>
{
    @Shadow
    private static float[][] shifts;
    @Final
    @Shadow
    private ModelShulker model;
    @Shadow
    private Random random;

    /**
     * Use simple RenderItem instead of RenderEntityItem.
     *
     * @reason Don't use customItem which retains the first WorldClient.
     * @author jchung01
     */
    @Overwrite
    public void render(TileEntityIronShulkerBox te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        if (te == null || te.isInvalid())
        {
            return;
        }

        EnumFacing enumfacing = EnumFacing.UP;
        IronShulkerBoxType type = te.getType();

        if (te.hasWorld())
        {
            IBlockState iblockstate = this.getWorld().getBlockState(te.getPos());

            if (iblockstate.getBlock() instanceof BlockIronShulkerBox)
            {
                enumfacing = te.getFacing();
                type = iblockstate.getValue(BlockIronShulkerBox.VARIANT_PROP);
            }
        }

        GlStateManager.enableDepth();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);
        GlStateManager.disableCull();

        if (destroyStage >= 0)
        {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 4.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        }
        else
        {
            ResourceLocation rs = new ResourceLocation("ironchest", "textures/model/shulker/" + te.getColor().getName() + "/shulker_" + te.getColor().getName() + type.modelTexture);

            this.bindTexture(rs);
        }

        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();

        if (destroyStage < 0)
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
        }

        GlStateManager.translate((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        GlStateManager.translate(0.0F, 1.0F, 0.0F);
        GlStateManager.scale(0.9995F, 0.9995F, 0.9995F);
        GlStateManager.translate(0.0F, -1.0F, 0.0F);

        switch (enumfacing)
        {
            case DOWN:
                GlStateManager.translate(0.0F, 2.0F, 0.0F);
                GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            case UP:
            default:
                break;
            case NORTH:
                GlStateManager.translate(0.0F, 1.0F, 1.0F);
                GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
                break;
            case SOUTH:
                GlStateManager.translate(0.0F, 1.0F, -1.0F);
                GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                break;
            case WEST:
                GlStateManager.translate(-1.0F, 1.0F, 0.0F);
                GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
                break;
            case EAST:
                GlStateManager.translate(1.0F, 1.0F, 0.0F);
                GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
        }

        this.model.base.render(0.0625F);
        GlStateManager.translate(0.0F, -te.getProgress(partialTicks) * 0.5F, 0.0F);
        GlStateManager.rotate(270.0F * te.getProgress(partialTicks), 0.0F, 1.0F, 0.0F);
        this.model.lid.render(0.0625F);
        GlStateManager.enableCull();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        if (destroyStage >= 0)
        {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }

        if (type == IronShulkerBoxType.CRYSTAL)
        {
            GlStateManager.enableCull();
        }

        if (type.isTransparent() && te.getDistanceSq(this.rendererDispatcher.entityX, this.rendererDispatcher.entityY, this.rendererDispatcher.entityZ) < 128d)
        {
            this.random.setSeed(254L);

            float shiftX;
            float shiftY;
            float shiftZ;
            int shift = 0;
            float blockScale = 0.70F;
            float timeD = (float) (360D * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL) - partialTicks;

            if (te.getTopItems().get(1).isEmpty())
            {
                shift = 8;
                blockScale = 0.85F;
            }

            GlStateManager.pushMatrix();
            GlStateManager.translate((float) x, (float) y, (float) z);

            // CHANGE START: Don't init customItem
            /*
            if (customItem == null)
            {
                customItem = new EntityItem(this.getWorld());
            }

            customItem.hoverStart = 0F;
             */
            // CHANGE END

            for (ItemStack item : te.getTopItems())
            {
                if (shift > shifts.length || shift > 8)
                {
                    break;
                }

                if (item.isEmpty())
                {
                    shift++;
                    continue;
                }

                shiftX = shifts[shift][0];
                shiftY = shifts[shift][1];
                shiftZ = shifts[shift][2];
                shift++;

                GlStateManager.pushMatrix();
                GlStateManager.translate(shiftX, shiftY, shiftZ);
                GlStateManager.rotate(timeD, 0F, 1F, 0F);
                GlStateManager.scale(blockScale, blockScale, blockScale);
                // CHANGE START: Don't use customItem and use built-in RenderItem
                /*
                customItem.setItem(item);

                if (this.itemRenderer == null)
                {
                    this.itemRenderer = new RenderEntityItem(Minecraft.getMinecraft().getRenderManager(), Minecraft.getMinecraft().getRenderItem())
                    {
                        @Override
                        public int getModelCount(ItemStack stack)
                        {
                            return SignedBytes.saturatedCast(Math.min(stack.getCount() / 32, 15) + 1);
                        }

                        @Override
                        public boolean shouldBob()
                        {
                            return false;
                        }

                        @Override
                        public boolean shouldSpreadItems()
                        {
                            return true;
                        }
                    };
                }

                this.itemRenderer.doRender(customItem, 0D, 0D, 0D, 0F, partialTicks);
                 */
                Minecraft.getMinecraft().getRenderItem().renderItem(item, ItemCameraTransforms.TransformType.GROUND);
                // CHANGE END

                GlStateManager.popMatrix();
            }

            GlStateManager.popMatrix();
        }
    }
}
