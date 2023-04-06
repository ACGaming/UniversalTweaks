package mod.acgaming.universaltweaks.tweaks.entities.creepers.charged;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import mod.acgaming.universaltweaks.util.UTRandomUtil;

// Courtesy of UeberallGebannt
@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTChargedCreepers
{
    // All creepers with this tag will be ignored and not checked again
    public static final String SPAWNED_CHECK_TAG = "utChargedCreepers.spawned";

    // Also ignore all creepers modified by NCC (compatible with old worlds)
    public static final String NCC_CHECK_TAG = "naturallychargedcreepers.checked";

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void utChargedCreepersSpawnChance(EntityJoinWorldEvent event)
    {
        if (!(event.getEntity() instanceof EntityCreeper) || event.getWorld().isRemote) return;

        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTChargedCreepers ::: Creeper Spawn Event");

        double chargedChance = UTConfig.TWEAKS_ENTITIES.utCreeperChargedChance;
        if (chargedChance <= 0.0D) return;

        if (event.getEntity().getTags().contains(SPAWNED_CHECK_TAG) || event.getEntity().getTags().contains(NCC_CHECK_TAG)) return;
        event.getEntity().addTag(SPAWNED_CHECK_TAG);

        if (!UTRandomUtil.chance(chargedChance)) return;

        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTChargedCreepers ::: Creeper Spawn Event Charged");

        EntityCreeper creeper = (EntityCreeper) event.getEntity();
        NBTTagCompound tags = new NBTTagCompound();
        creeper.writeEntityToNBT(tags);
        tags.setByte("powered", (byte) 1);
        creeper.readEntityFromNBT(tags);
    }
}