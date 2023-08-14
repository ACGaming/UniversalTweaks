package mod.acgaming.universaltweaks.mods.elenaidodge2;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.elenai.elenaidodge2.api.FeathersHelper;
import mod.acgaming.universaltweaks.config.UTConfig;

public class UTED2Sprinting
{
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void utED2Sprinting(TickEvent.PlayerTickEvent event)
    {
        if (event.phase == TickEvent.Phase.START
            && event.player instanceof EntityPlayerMP
            && event.player.isSprinting()
            && event.player.ticksExisted % UTConfig.MOD_INTEGRATION.ELENAI_DODGE_2.utED2SprintingFeatherInterval == 0)
        {
            FeathersHelper.decreaseFeathers((EntityPlayerMP) event.player, UTConfig.MOD_INTEGRATION.ELENAI_DODGE_2.utED2SprintingFeatherConsumption);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void utED2SprintingClient(TickEvent.PlayerTickEvent event)
    {
        if (event.phase == TickEvent.Phase.START
            && event.player instanceof EntityPlayerSP
            && (((EntityPlayerSP) event.player).sprintingTicksLeft < 5 || FeathersHelper.getFeatherLevel((EntityPlayerSP) event.player) == 0)
            && FeathersHelper.getFeatherLevel((EntityPlayerSP) event.player) < UTConfig.MOD_INTEGRATION.ELENAI_DODGE_2.utED2SprintingFeatherRequirement)
        {
            event.player.setSprinting(false);
            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSprint.getKeyCode(), false);
        }
    }
}