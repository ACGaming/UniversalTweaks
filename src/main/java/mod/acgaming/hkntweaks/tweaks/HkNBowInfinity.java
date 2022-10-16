package mod.acgaming.hkntweaks.tweaks;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import mod.acgaming.hkntweaks.HkNTweaks;
import mod.acgaming.hkntweaks.config.HkNTweaksConfig;

// Courtesy of Parker8283
@Mod.EventBusSubscriber(modid = HkNTweaks.MODID)
public class HkNBowInfinity
{
    @GameRegistry.ObjectHolder("minecraft:infinity")
    public static final Enchantment INFINITY = null;

    @SubscribeEvent
    public static void hknBowInfinity(ArrowNockEvent event)
    {
        if (!HkNTweaksConfig.tweaks.hknBowInfinityToggle) return;
        if (EnchantmentHelper.getEnchantmentLevel(INFINITY, event.getBow()) > 0)
        {
            event.getEntityPlayer().setActiveHand(event.getHand());
            event.setAction(new ActionResult<>(EnumActionResult.SUCCESS, event.getBow()));
        }
    }
}