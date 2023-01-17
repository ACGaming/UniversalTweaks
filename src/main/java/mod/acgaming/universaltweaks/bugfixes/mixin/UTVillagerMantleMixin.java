package mod.acgaming.universaltweaks.bugfixes.mixin;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of MJaroslav
@Mixin(ModelVillager.class)
public class UTVillagerMantleMixin extends ModelBase
{
    @Shadow
    public ModelRenderer villagerHead;

    @Unique
    public ModelRenderer villagerMantle;

    @Inject(method = "<init>(FFII)V", at = @At("TAIL"))
    public void utVillagerMantleInit(float f, float f1, int i, int i1, CallbackInfo ci)
    {
        if (!UTConfig.BUGFIXES_ENTITIES.utVillagerMantleToggle) return;
        villagerMantle = new ModelRenderer(this).setTextureSize(i, i1);
        villagerMantle.setTextureOffset(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, f + 0.5F);
        villagerMantle.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
    }

    @Inject(method = "setRotationAngles", at = @At("TAIL"))
    public void utVillagerMantleRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e, CallbackInfo ci)
    {
        if (!UTConfig.BUGFIXES_ENTITIES.utVillagerMantleToggle || !(e instanceof EntityVillager)) return;
        villagerMantle.rotateAngleY = villagerHead.rotateAngleY;
        villagerMantle.rotateAngleX = villagerHead.rotateAngleX;
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void utVillagerMantleRender(Entity e, float f, float f1, float f2, float f3, float f4, float f5, CallbackInfo ci)
    {
        if (!UTConfig.BUGFIXES_ENTITIES.utVillagerMantleToggle || !(e instanceof EntityVillager)) return;
        villagerMantle.render(f5);
    }
}