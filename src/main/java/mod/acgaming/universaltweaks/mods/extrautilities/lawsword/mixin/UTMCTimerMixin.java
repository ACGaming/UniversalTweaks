package mod.acgaming.universaltweaks.mods.extrautilities.lawsword.mixin;

import com.rwtema.extrautils2.utils.MCTimer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = MCTimer.class, remap = false)
public abstract class UTMCTimerMixin
{
    @Unique
    @SubscribeEvent
    public void ut$serverTick(TickEvent.ServerTickEvent event)
    {
        if (event.phase == TickEvent.Phase.START)
        {
            MCTimer.serverTimer++;
        }
    }
}
