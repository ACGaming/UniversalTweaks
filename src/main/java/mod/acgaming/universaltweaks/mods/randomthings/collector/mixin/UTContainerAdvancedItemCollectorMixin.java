package mod.acgaming.universaltweaks.mods.randomthings.collector.mixin;

import lumien.randomthings.container.ContainerAdvancedItemCollector;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

// Courtesy of WaitingIdly
@Mixin(value = ContainerAdvancedItemCollector.class, remap = false)
public class UTContainerAdvancedItemCollectorMixin
{
    @ModifyConstant(method = "transferStackInSlot", constant = @Constant(intValue = 3), remap = true)
    private int utFixSlotDuping(int constant)
    {
        if (!UTConfigMods.RANDOM_THINGS.utItemCollectorDupe) return constant;
        return 1;
    }
}