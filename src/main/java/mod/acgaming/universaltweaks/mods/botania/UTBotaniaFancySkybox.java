package mod.acgaming.universaltweaks.mods.botania;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import vazkii.botania.client.render.world.SkyblockSkyRenderer;
import vazkii.botania.common.core.handler.ConfigHandler;

public class UTBotaniaFancySkybox
{
    public static List<Integer> dimList = new ArrayList<>();

    public static void initDimList()
    {
        dimList.clear();
        dimList = Arrays.asList(UTConfig.MOD_INTEGRATION.BOTANIA.utBotaniaSkyboxDims);
        UniversalTweaks.LOGGER.info("Botania Fancy Skybox dimension list initialized");
    }

    @SubscribeEvent
    public static void utRenderBotaniaFancySkybox(RenderWorldLastEvent event)
    {
        if (UTConfig.MOD_INTEGRATION.BOTANIA.utBotaniaSkyboxDims.length < 1) return;
        WorldProvider worldProvider = Minecraft.getMinecraft().world.provider;
        if (ConfigHandler.enableFancySkybox && dimList.contains(worldProvider.getDimension()) && !(worldProvider.getSkyRenderer() instanceof SkyblockSkyRenderer))
        {
            worldProvider.setSkyRenderer(new SkyblockSkyRenderer());
            if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTBotaniaFancySkybox ::: Enabled fancy skybox for dimension {}", worldProvider.getDimension());
        }
    }
}