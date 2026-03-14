package mod.acgaming.universaltweaks.tweaks.entities.damage.arrow.layers;

import java.util.Random;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UTLayerArrowCustom implements LayerRenderer<EntityLivingBase>
{
    private final RenderLivingBase<?> renderer;

    public UTLayerArrowCustom(RenderLivingBase<?> renderer)
    {
        this.renderer = renderer;
    }

    @Override
    public void doRenderLayer(EntityLivingBase entityLivingBase, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        int i = entityLivingBase.getArrowCountInEntity();
        if (i > 0)
        {
            Entity entity = new EntityTippedArrow(entityLivingBase.world, entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ);
            Random random = new Random(entityLivingBase.getEntityId());
            for (int j = 0; j < i; ++j)
            {
                GlStateManager.pushMatrix();
                ModelRenderer modelrenderer = this.renderer.getMainModel().getRandomModelBox(random);
                ModelBox modelbox = modelrenderer.cubeList.get(random.nextInt(modelrenderer.cubeList.size()));
                modelrenderer.postRender(0.0625F);
                float f = random.nextFloat();
                float f1 = random.nextFloat();
                float f2 = random.nextFloat();
                float f3 = (modelbox.posX1 + (modelbox.posX2 - modelbox.posX1) * f) / 16.0F;
                float f4 = (modelbox.posY1 + (modelbox.posY2 - modelbox.posY1) * f1) / 16.0F;
                float f5 = (modelbox.posZ1 + (modelbox.posZ2 - modelbox.posZ1) * f2) / 16.0F;
                GlStateManager.translate(f3, f4, f5);
                f = f * 2.0F - 1.0F;
                f1 = f1 * 2.0F - 1.0F;
                f2 = f2 * 2.0F - 1.0F;
                f = f * -1.0F;
                f1 = f1 * -1.0F;
                f2 = f2 * -1.0F;
                float f6 = MathHelper.sqrt(f * f + f2 * f2);
                entity.rotationYaw = (float) (Math.atan2(f, f2) * (180D / Math.PI));
                entity.rotationPitch = (float) (Math.atan2(f1, f6) * (180D / Math.PI));
                entity.prevRotationYaw = entity.rotationYaw;
                entity.prevRotationPitch = entity.rotationPitch;
                this.renderer.getRenderManager().renderEntity(entity, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, false);
                GlStateManager.popMatrix();
            }
        }
    }

    @Override
    public boolean shouldCombineTextures()
    {
        return false;
    }
}
