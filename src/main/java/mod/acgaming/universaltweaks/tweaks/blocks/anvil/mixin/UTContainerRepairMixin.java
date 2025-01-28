package mod.acgaming.universaltweaks.tweaks.blocks.anvil.mixin;

import net.minecraft.inventory.ContainerRepair;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ContainerRepair.class)
public abstract class UTContainerRepairMixin
{
    @ModifyConstant(method = "updateRepairOutput", constant = @Constant(intValue = 40))
    private int utContainerRepair(int constant)
    {
        return UTConfigTweaks.BLOCKS.ANVIL.utAnvilXPLevelCap;
    }
}