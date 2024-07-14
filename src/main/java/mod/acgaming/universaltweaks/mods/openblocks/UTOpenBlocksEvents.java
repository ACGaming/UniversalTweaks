package mod.acgaming.universaltweaks.mods.openblocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import openblocks.enchantments.LastStandEnchantmentsHandler;

public class UTOpenBlocksEvents
{
    public static LastStandEnchantmentsHandler lastStandHandler;

    /**
     * Last Stand in 1.12 uses LivingHurtEvent, which is the pre-mitigation damage (before armor, potions, etc).
     * It should use LivingDamageEvent, which is the post-mitigation damage.
     * This was actually explicitly fixed in older versions (1.10) but once again uses the wrong event in 1.12.
     */
    @SubscribeEvent
    public void handleLastStand(LivingDamageEvent event)
    {
        // This is a double check, to avoid extra allocations.
        if (event.getEntityLiving() instanceof EntityPlayer)
        {
            WrappedLivingHurtEvent eventIn = new WrappedLivingHurtEvent(event.getEntityLiving(), event.getSource(), event.getAmount());
            lastStandHandler.onHurt(eventIn);
            event.setAmount(eventIn.getAmount());
            event.setCanceled(eventIn.isCanceled());
        }
    }
}
