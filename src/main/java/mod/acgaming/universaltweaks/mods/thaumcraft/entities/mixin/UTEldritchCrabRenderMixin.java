package mod.acgaming.universaltweaks.mods.thaumcraft.entities.mixin;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
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

    public float getDeathMaxRotation(EntityEldritchCrab entity)
    {
		  if (UTConfig.MOD_INTEGRATION.THAUMCRAFT_ENTITIES.utTCSpiderlikeEldritchCrabToggle) return 180.0f;
      else return 90.0f;
	  }
}
