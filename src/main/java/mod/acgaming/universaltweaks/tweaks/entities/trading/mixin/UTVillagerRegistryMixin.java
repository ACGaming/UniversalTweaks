package mod.acgaming.universaltweaks.tweaks.entities.trading.mixin;

import java.util.List;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = VillagerRegistry.VillagerCareer.class, remap = false)
public class UTVillagerRegistryMixin
{
    @Inject(method = "getTrades", at = @At("RETURN"), cancellable = true)
    public void utGetTrades(int level, CallbackInfoReturnable<List<EntityVillager.ITradeList>> cir)
    {
        if (UTConfigTweaks.ENTITIES.utVillagerTradeLevelingToggle && level > 0) cir.setReturnValue(null);
    }
}