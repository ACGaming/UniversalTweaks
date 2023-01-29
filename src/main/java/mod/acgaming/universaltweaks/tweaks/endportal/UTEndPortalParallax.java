package mod.acgaming.universaltweaks.tweaks.endportal;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntityEndPortal;

import mod.acgaming.universaltweaks.tweaks.endportal.renderer.TileEntityEndPortalParallaxRenderer;

// Courtesy of rune_smith98
public class UTEndPortalParallax
{
    public static void initRenderer()
    {
        TileEntityRendererDispatcher.instance.renderers.put(TileEntityEndPortal.class, new TileEntityEndPortalParallaxRenderer());
    }
}