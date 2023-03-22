package mod.acgaming.universaltweaks.tweaks;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import mod.acgaming.universaltweaks.util.UTRandomUtil;

@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTChargedCreepers
{
    // All creepers with this tag will be ignored and not checked again
    private static final String SPAWNED_CHECK_TAG = "utChargedCreepers.spawned";

    // Also ignore all creepers modified by NCC (compatible with old worlds)
    private static final String NCC_CHECK_TAG = "naturallychargedcreepers.checked";

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void utChargedCreepersSpawnChance(EntityJoinWorldEvent event)
    {
        if (!(event.getEntity() instanceof EntityCreeper)) return;

        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTChargedCreepers ::: Creeper Spawn Event");

        double chargedChance = UTConfig.TWEAKS_ENTITIES.utChargedCreeperSpawnChance;
        if (chargedChance <= 0.0d) return;

        if (event.getEntity().getTags().contains(SPAWNED_CHECK_TAG) || event.getEntity().getTags().contains(NCC_CHECK_TAG)) return;
        event.getEntity().addTag(SPAWNED_CHECK_TAG);

        if (!UTRandomUtil.chance(chargedChance)) return;

        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTChargedCreepers ::: Creeper Spawn Event Charged");

        EntityCreeper creeper = (EntityCreeper) event.getEntity();
        BlockPos pos = creeper.getPosition();

        creeper.onStruckByLightning(new EntityLightningBolt(event.getWorld(), pos.getX(), pos.getY(), pos.getZ(), true));
        creeper.extinguish();
    }
}