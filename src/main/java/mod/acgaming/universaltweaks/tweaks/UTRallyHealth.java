package mod.acgaming.universaltweaks.tweaks;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

// Courtesy of Mrbysco
@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTRallyHealth
{
    @SubscribeEvent
    public static void utDamageHandler(LivingHurtEvent event)
    {
        if (!UTConfig.TWEAKS_MISC.RALLY_HEALTH.utRallyHealthToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTRallyHealth ::: Damage handler");
        if (event.getEntityLiving() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            NBTTagCompound playerData = player.getEntityData();
            if (event.getSource().getTrueSource() != null && !player.world.isRemote)
            {
                playerData.setFloat("lastDamage", event.getAmount());
                playerData.setBoolean("atRisk", true);
                playerData.setInteger("riskTime", 0);
                playerData.setString("lastMob", event.getSource().getTrueSource().getName());
            }
        }
    }

    @SubscribeEvent
    public static void utLivingAttack(LivingAttackEvent event)
    {
        if (!UTConfig.TWEAKS_MISC.RALLY_HEALTH.utRallyHealthToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTRallyHealth ::: Living attack");
        if (event.getSource().getDamageType().equals("player") && event.getSource().getTrueSource() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
            NBTTagCompound playerData = player.getEntityData();
            if (event.getEntityLiving().getName().equals(playerData.getString("lastMob"))
                && !player.world.isRemote
                && playerData.getBoolean("atRisk")
                && player.world.rand.nextInt(100) < UTConfig.TWEAKS_MISC.RALLY_HEALTH.utRallyHealthHealChance)
            {
                player.heal(playerData.getFloat("lastDamage"));
                playerData.setBoolean("atRisk", false);
                if (UTConfig.TWEAKS_MISC.RALLY_HEALTH.utRallyHealthSound)
                {
                    player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.PLAYERS, 0.5F, 2.0F);
                }
            }
        }
    }

    @SubscribeEvent
    public static void utRiskEvent(TickEvent.PlayerTickEvent event)
    {
        if (!UTConfig.TWEAKS_MISC.RALLY_HEALTH.utRallyHealthToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTRallyHealth ::: Risk event");
        if (event.phase.equals(TickEvent.Phase.START) && event.side.isServer())
        {
            MinecraftServer server = event.player.world.getMinecraftServer();
            if (server == null) return;
            List<EntityPlayerMP> playerList = server.getPlayerList().getPlayers();
            for (EntityPlayer players : playerList)
            {
                NBTTagCompound playerData = players.getEntityData();
                int riskTime = playerData.getInteger("riskTime");
                if (playerData.getBoolean("atRisk"))
                {
                    if (riskTime >= UTConfig.TWEAKS_MISC.RALLY_HEALTH.utRallyHealthRiskTime)
                    {
                        riskTime = 0;
                        playerData.setInteger("riskTime", riskTime);
                        playerData.setBoolean("atRisk", false);
                    }
                    else
                    {
                        riskTime++;
                        playerData.setInteger("riskTime", riskTime);
                    }
                }
                else
                {
                    riskTime = 0;
                    playerData.setInteger("riskTime", riskTime);
                }
            }
        }
    }
}