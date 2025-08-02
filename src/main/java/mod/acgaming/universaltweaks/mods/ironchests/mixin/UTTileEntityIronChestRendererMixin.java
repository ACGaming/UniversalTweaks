package mod.acgaming.universaltweaks.mods.ironchests.mixin;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import cpw.mods.ironchest.client.renderer.chest.TileEntityIronChestRenderer;
import cpw.mods.ironchest.common.blocks.chest.BlockIronChest;
import cpw.mods.ironchest.common.blocks.chest.IronChestType;
import cpw.mods.ironchest.common.core.IronChestBlocks;
import cpw.mods.ironchest.common.tileentity.chest.TileEntityIronChest;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

// Courtesy of jchung01
@Mixin(value = TileEntityIronChestRenderer.class, remap = false)
public class UTTileEntityIronChestRendererMixin extends TileEntitySpecialRenderer<TileEntityIronChest>
{
    @Shadow
    private static float[][] shifts;
    @Shadow
    private static float halfPI;
    @Shadow
    private ModelChest model;
    @Shadow
    private Random random;

    /**
     * Use simple RenderItem instead of RenderEntityItem.
     *
     * @reason Don't use customItem which retains the first WorldClient.
     * @author jchung01
     */
    @Overwrite
    public void render(TileEntityIronChest te, double x, double y, double z, float partialTicks, int destroyStage, float partial)
    {
        if (te == null || te.isInvalid())
        {
            return;
        }

        EnumFacing facing = EnumFacing.SOUTH;
        IronChestType type = te.getType();

        if (te.hasWorld() && te.getWorld().getBlockState(te.getPos()).getBlock() == IronChestBlocks.ironChestBlock)
        {
            facing = te.getFacing();
            IBlockState state = te.getWorld().getBlockState(te.getPos());
            type = state.getValue(BlockIronChest.VARIANT_PROP);
        }

        if (destroyStage >= 0)
        {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4F, 4F, 1F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        }
        else
        {
            this.bindTexture(type.modelTexture);
        }

        GlStateManager.pushMatrix();

        if (type == IronChestType.CRYSTAL)
        {
            GlStateManager.disableCull();
        }

        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.translate((float) x, (float) y + 1F, (float) z + 1F);
        GlStateManager.scale(1F, -1F, -1F);
        GlStateManager.translate(0.5F, 0.5F, 0.5F);

        switch (facing)
        {
            case NORTH:
            {
                GlStateManager.rotate(180F, 0F, 1F, 0F);
                break;
            }
            case SOUTH:
            {
                GlStateManager.rotate(0F, 0F, 1F, 0F);
                break;
            }
            case WEST:
            {
                GlStateManager.rotate(90F, 0F, 1F, 0F);
                break;
            }
            case EAST:
            {
                GlStateManager.rotate(270F, 0F, 1F, 0F);
                break;
            }
            default:
            {
                GlStateManager.rotate(0F, 0F, 1F, 0F);
                break;
            }
        }

        GlStateManager.translate(-0.5F, -0.5F, -0.5F);

        float lidangle = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * partialTicks;

        lidangle = 1F - lidangle;
        lidangle = 1F - lidangle * lidangle * lidangle;

        if (type.isTransparent())
        {
            GlStateManager.scale(1F, 0.99F, 1F);
        }

        this.model.chestLid.rotateAngleX = -lidangle * halfPI;
        // Render the chest itself
        this.model.renderAll();

        if (destroyStage >= 0)
        {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }

        if (type == IronChestType.CRYSTAL)
        {
            GlStateManager.enableCull();
        }

        GlStateManager.popMatrix();
        GlStateManager.color(1F, 1F, 1F, 1F);

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
