package mod.acgaming.universaltweaks.tweaks.misc.fov;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UTFovHandler
{
    /**
     * Priority is {@link EventPriority#HIGH} for this event to fire early so mods with custom FOV
     * handling can modify this value without being overwritten.
     */
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void utOnFovUpdate(FOVUpdateEvent event) {
        event.setNewfov(getBowFov(event.getEntity()));
    }

    /**
     * A copy of the FOV logic from {@link AbstractClientPlayer#getFovModifier()}, bypassing movement
     * speed modifiers and only applying bow aiming adjustments.
     */
    private static float getBowFov(EntityPlayer player) {
        if (player.isHandActive() && player.getActiveItemStack().getItem() instanceof ItemBow)
        {
            int useMaxCount = player.getItemInUseMaxCount();
            float useSeconds = (float)useMaxCount / 20.0F;

            if (useSeconds > 1.0F)
            {
                useSeconds = 1.0F;
            }
            else
            {
                useSeconds = useSeconds * useSeconds;
            }

            return 1.0F - useSeconds * 0.15F;
        }
        return 1.0f;
    }
}
