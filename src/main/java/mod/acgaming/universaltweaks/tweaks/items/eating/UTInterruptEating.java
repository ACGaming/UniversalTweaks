package mod.acgaming.universaltweaks.tweaks.items.eating;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class UTInterruptEating
{
    @SubscribeEvent
    public static void utInterruptEating(LivingHurtEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.isHandActive() && entity.getActiveItemStack() != ItemStack.EMPTY)
        {
            EnumAction action = entity.getActiveItemStack().getItemUseAction();
            if (action == EnumAction.EAT || action == EnumAction.DRINK) entity.resetActiveHand();
        }
    }
}
