package mod.acgaming.universaltweaks.bugfixes.entities.uuid;

import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;

// Courtesy of CAS-ual-TY
public class UTEntityUUID
{
    @SubscribeEvent
    public static void utFixDuplicateUUID(EntityJoinWorldEvent event)
    {
        if (event.getWorld() instanceof WorldServer)
        {
            Entity entity = event.getEntity();
            if (entity instanceof EntityPlayer) return;
            WorldServer world = (WorldServer) event.getWorld();
            UUID uuid = entity.getUniqueID();
            Entity existing = world.getEntityFromUuid(uuid);
            if (existing != null && existing != entity)
            {
                UUID uuidNew;
                do uuidNew = MathHelper.getRandomUUID(world.rand); while (world.getEntityFromUuid(uuidNew) != null);
                entity.setUniqueId(uuidNew);
                UniversalTweaks.LOGGER.info("Changing UUID of entity {} that already existed from {} to {}", EntityList.getKey(entity), uuid.toString(), uuidNew.toString());
            }
        }
    }
}