package mod.acgaming.universaltweaks.mods.netherrocks.mixin;

import net.minecraftforge.event.world.BlockEvent;

import alexndr.plugins.netherrocks.helpers.FyriteHandler;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = FyriteHandler.class, remap = false)
public class UTNetherrocksFyriteHandlerMixin
{
    @Inject(method = "onHarvestDrops", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/event/world/BlockEvent$HarvestDropsEvent;getWorld()Lnet/minecraft/world/World;", ordinal = 0), cancellable = true)
    public void utNetherrocksFyriteHandler(BlockEvent.HarvestDropsEvent event, CallbackInfo ci)
    {
        if (UTConfig.MOD_INTEGRATION.NETHERROCKS.utNRRightClickHarvestingToggle && event.getHarvester().getActiveHand() == null) ci.cancel();
    }
}