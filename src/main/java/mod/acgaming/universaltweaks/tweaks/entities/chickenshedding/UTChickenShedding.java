package mod.acgaming.universaltweaks.tweaks.entities.chickenshedding;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of holmraven
@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTChickenShedding {
    @SubscribeEvent
    public static void onLivingUpdate(LivingUpdateEvent event)
    {
        if (event.getEntity().world.isRemote || !(event.getEntity() instanceof EntityChicken) || !(UTConfigTweaks.ENTITIES.CHICKEN_SHEDDING.utChickenSheddingToggle))
        {
            return;
        }

        EntityChicken chicken = (EntityChicken) event.getEntity();
        if (UTConfigTweaks.ENTITIES.CHICKEN_SHEDDING.utChickenSheddingBabyToggle || !(chicken.isChild()) && chicken.world.rand.nextInt(UTConfigTweaks.ENTITIES.CHICKEN_SHEDDING.utChickenSheddingFrequency) == 0)
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTChickenShedding ::: Living update event");
            chicken.dropItem(Items.FEATHER, 1);
        }
    }
}