package mod.acgaming.universaltweaks.tweaks.entities.damage.arrow.layers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

@SideOnly(Side.CLIENT)
public class UTArrowLayers
{
    public static void addArrowLayers()
    {
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        List<String> list = Arrays.asList(UTConfigTweaks.ENTITIES.ARROW_LAYERS.utEntityList);
        boolean isWhitelist = UTConfigTweaks.ENTITIES.ARROW_LAYERS.utEntityListMode == UTConfigTweaks.EnumLists.WHITELIST;
        for (Map.Entry<Class<? extends Entity>, Render<? extends Entity>> entry : renderManager.entityRenderMap.entrySet())
        {
            Render<? extends Entity> renderer = entry.getValue();
            if (!(renderer instanceof RenderLivingBase) || renderer instanceof RenderPlayer) continue;
            Class<? extends Entity> entityClass = entry.getKey();
            ResourceLocation resourceLocation = EntityList.getKey(entityClass);
            if (resourceLocation != null && list.contains(resourceLocation.toString()) == isWhitelist)
            {
                ((RenderLivingBase<?>) renderer).addLayer(new UTLayerArrowCustom((RenderLivingBase<?>) renderer));
            }
        }
    }
}
