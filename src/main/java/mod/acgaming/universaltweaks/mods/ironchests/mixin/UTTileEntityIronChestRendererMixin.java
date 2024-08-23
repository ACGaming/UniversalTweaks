package mod.acgaming.universaltweaks.mods.ironchests.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

import cpw.mods.ironchest.client.renderer.chest.TileEntityIronChestRenderer;
import cpw.mods.ironchest.common.tileentity.chest.TileEntityIronChest;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of jchung01
@Mixin(value = TileEntityIronChestRenderer.class, remap = false)
public class UTTileEntityIronChestRendererMixin extends TileEntitySpecialRenderer<TileEntityIronChest>
{
    @Shadow
    private static float[][] shifts;

    /**
     * Use simple RenderItem instead of RenderEntityItem.
     * @reason Don't use customItem which retains the first WorldClient.
     * @author jchung01
     */
    @Inject(method = "render(Lcpw/mods/ironchest/common/tileentity/chest/TileEntityIronChest;DDDFIF)V", at = @At(value = "INVOKE", target = "Ljava/util/Random;setSeed(J)V", shift = At.Shift.AFTER), cancellable = true)
    private void utReplaceItemRenderer(TileEntityIronChest te, double x, double y, double z, float partialTicks, int destroyStage, float partial, CallbackInfo ci)
    {
        // if (UT) return;
        ci.cancel();

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
