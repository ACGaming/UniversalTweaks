package mod.acgaming.universaltweaks.mods.divinerpg.armorset;

import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import divinerpg.api.DivineAPI;
import divinerpg.api.armor.cap.IArmorPowers;
import divinerpg.events.ArmorWearingEvents;
import mod.acgaming.universaltweaks.mods.divinerpg.armorset.mixin.UTArmorPowersAccessor;

/**
 * Event handler to properly manage armor powers, because DivineRPG completely disregards thread safety
 * with their implementation.
 */
// Courtesy of jchung01
public class ArmorPowerHandler
{
    @SubscribeEvent
    public void copyArmorPowers(PlayerEvent.Clone event)
    {
        IArmorPowers oldCap = DivineAPI.getArmorPowers(event.getOriginal());
        IArmorPowers cap = DivineAPI.getArmorPowers(event.getEntityPlayer());
        if (oldCap == null || cap == null) return;

        Set<ResourceLocation> currentPowers = oldCap.wearing();
        ((UTArmorPowersAccessor) oldCap).ut$unsubscribe();

        for (ResourceLocation powerKey : currentPowers)
        {
            cap.putOn(powerKey);
        }
    }

    @SubscribeEvent
    public void syncArmorPowers(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event)
    {
        ArmorWearingEvents.recheckAllWearing(event.player, true);
    }

    @SubscribeEvent
    public void cleanupArmorPowersServer(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent event)
    {
        IArmorPowers cap = DivineAPI.getArmorPowers(event.player);
        if (cap == null) return;

        ((UTArmorPowersAccessor) cap).ut$unsubscribe();
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void cleanupArmorPowersClient(FMLNetworkEvent.ClientDisconnectionFromServerEvent event)
    {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        FMLCommonHandler.instance().getWorldThread(event.getHandler()).addScheduledTask(() -> {
            IArmorPowers cap = DivineAPI.getArmorPowers(player);
            if (cap == null) return;

            ((UTArmorPowersAccessor) cap).ut$unsubscribe();
        });
    }
}
