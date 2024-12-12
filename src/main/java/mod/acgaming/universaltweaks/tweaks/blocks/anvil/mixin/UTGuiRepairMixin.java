package mod.acgaming.universaltweaks.tweaks.blocks.anvil.mixin;

import net.minecraft.client.gui.GuiRepair;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GuiRepair.class)
public abstract class UTGuiRepairMixin
{
    @ModifyConstant(method = "drawGuiContainerForegroundLayer", constant = @Constant(intValue = 40))
    private int utGuiRepair(int constant)
    {
        return UTConfigTweaks.BLOCKS.ANVIL.utAnvilXPLevelCap;
    }
}