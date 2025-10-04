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
    /**
     * @reason There is only 1 slot in the Advanced Item Collector,
     * but with it acting as if there are 3, the top left two slots of the player inventory are interacted
     * with as if they are both in the player inventory and in the Advanced Item Collector,
     * which allows duplication bugs when shift+clicking if the hotbar is not full.
     * @author WaitingIdly
     */
    @ModifyConstant(method = "transferStackInSlot", constant = @Constant(intValue = 3), remap = true)
    private int utFixSlotDuping(int constant)
    {
        if (!UTConfigMods.RANDOM_THINGS.utItemCollectorDupe) return constant;
        return 1;
    }
}