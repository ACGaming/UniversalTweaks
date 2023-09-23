package mod.acgaming.universaltweaks.mods.thaumcraft.entities.mixin;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;

import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import thaumcraft.client.renderers.entity.mob.RenderEldritchCrab;
import thaumcraft.common.entities.monster.EntityEldritchCrab;

// Courtesy of Turkey9002
@Mixin(RenderEldritchCrab.class)
public abstract class UTEldritchCrabRenderMixin extends RenderLiving<EntityEldritchCrab>
{
    public UTEldritchCrabRenderMixin(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn)
    {
        super(rendermanagerIn, modelbaseIn, shadowsizeIn);
    }

    @Override
    public float getDeathMaxRotation(EntityEldritchCrab entity)
    {
        if (UTConfigMods.THAUMCRAFT_ENTITIES.utTCSpiderlikeEldritchCrabToggle) return 180.0F;
        else return 90.0F;
    }
}