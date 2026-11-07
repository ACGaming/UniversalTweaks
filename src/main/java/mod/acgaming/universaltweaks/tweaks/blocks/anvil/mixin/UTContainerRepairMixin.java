package mod.acgaming.universaltweaks.tweaks.blocks.anvil.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.minecraft.inventory.ContainerRepair;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ContainerRepair.class)
public abstract class UTContainerRepairMixin
{
    @ModifyExpressionValue(method = "updateRepairOutput", at = @At(value = "CONSTANT", args = "intValue=40"))
    private int utContainerRepair(int constant)
    {
        return UTConfigTweaks.BLOCKS.ANVIL.utAnvilXPLevelCap;
    }
}