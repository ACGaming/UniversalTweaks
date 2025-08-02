package mod.acgaming.universaltweaks.bugfixes.world.portal;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;

public class UTPortalLocationLink
{
    /**
     * Store the entity's last used portal when they enter the Nether.
     */
    @SubscribeEvent
    public static void utDimensionChangeEventPortalLocation(EntityTravelToDimensionEvent event)
    {
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTPortalLocationLink ::: Entity travel to dimension event");
        if (event.getEntity().inPortal && event.getEntity().getEntityWorld().provider.getDimension() == 0)
        {
            writePortalNBT(event.getEntity());
        }
    }

    /**
     * Checks if the stored portal location exists and is close enough.
     */
    public static double[] getStoredPortalCoords(Entity entity)
    {
        // Coming from the Nether (-1)
        if (entity.getEntityWorld().provider.getDimension() == -1)
        {
            // Read portal coordinates from NBT
            double[] storedPortalCoords = UTPortalLocationLink.readPortalNBT(entity);
            if (storedPortalCoords != null)
            {
                // Check if the stored portal is within the vanilla teleporter distance (128 blocks)
                double distanceSq = entity.getPosition().distanceSq(storedPortalCoords[0], storedPortalCoords[1], storedPortalCoords[2]);
                if (distanceSq <= 128 * 128)
                {
                    return storedPortalCoords;
                }
            }
        }
        return new double[0];
    }

    /**
     * Stores the portal location in the entity's NBT data.
     */
    private static void writePortalNBT(Entity entity)
    {
        NBTTagCompound nbt = entity.getEntityData();
        NBTTagCompound portalData;

        if (!nbt.hasKey("NetherPortal", 10))
        {
            nbt.setTag("NetherPortal", new NBTTagCompound());
        }

        portalData = nbt.getCompoundTag("NetherPortal");

        portalData.setDouble("LastPortalX", entity.posX);
        portalData.setDouble("LastPortalY", entity.posY);
        portalData.setDouble("LastPortalZ", entity.posZ);

        nbt.setTag("NetherPortal", portalData);
    }

    /**
     * Retrieves the stored portal location from the entity's NBT data.
     */
    private static double[] readPortalNBT(Entity entity)
    {
        NBTTagCompound nbt = entity.getEntityData();
        if (nbt.hasKey("NetherPortal", 10))
        {
            NBTTagCompound portalData = nbt.getCompoundTag("NetherPortal");
            if (portalData.hasKey("LastPortalX") && portalData.hasKey("LastPortalY") && portalData.hasKey("LastPortalZ"))
            {
                double x = portalData.getDouble("LastPortalX");
                double y = portalData.getDouble("LastPortalY");
                double z = portalData.getDouble("LastPortalZ");
                return new double[] {x, y, z};
            }
        }
        return null;
    }
}
