package mod.acgaming.universaltweaks.tweaks.items.bowinfinity;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;

// Courtesy of Parker8283
public class UTBowInfinity
{
    @GameRegistry.ObjectHolder("minecraft:infinity")
    public static final Enchantment INFINITY = null;

    @SubscribeEvent
    public static void utBowInfinity(ArrowNockEvent event)
    {
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTBowInfinity ::: Arrow nock event");
        if (EnchantmentHelper.getEnchantmentLevel(INFINITY, event.getBow()) > 0)
        {
            event.getEntityPlayer().setActiveHand(event.getHand());
            event.setAction(new ActionResult<>(EnumActionResult.SUCCESS, event.getBow()));
        }
    }
}