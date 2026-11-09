package mod.acgaming.universaltweaks.tweaks.blocks.anvil.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.minecraft.client.gui.GuiRepair;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GuiRepair.class)
public abstract class UTGuiRepairMixin
{
    @ModifyExpressionValue(method = "drawGuiContainerForegroundLayer", at = @At(value = "CONSTANT", args = "intValue=40"))
    private int utGuiRepair(int constant)
    {
        return UTConfigTweaks.BLOCKS.ANVIL.utAnvilXPLevelCap;
    }
}