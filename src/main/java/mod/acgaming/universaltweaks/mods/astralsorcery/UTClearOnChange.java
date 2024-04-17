package mod.acgaming.universaltweaks.mods.astralsorcery;

import hellfirepvp.astralsorcery.client.effect.EffectHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

// Courtesy of WaitingIdly
public class UTClearOnChange
{
    @SubscribeEvent
    public void utClearAstralEffects(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        EffectHandler.cleanUp();
    }

    @SubscribeEvent
    public void utClearAstralEffectsWhenLeavingEnd(PlayerEvent.PlayerRespawnEvent event)
    {
        if (event.isEndConquered()) EffectHandler.cleanUp();
    }
}
