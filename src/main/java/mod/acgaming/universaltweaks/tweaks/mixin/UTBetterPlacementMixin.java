package mod.acgaming.universaltweaks.tweaks.mixin;

import net.minecraft.client.Minecraft;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Minecraft.class)
public class UTBetterPlacementMixin
{
    @ModifyConstant(method = "rightClickMouse", constant = @Constant(intValue = 4))
    public int utClickDelay(int constant)
    {
        return UTConfig.tweaks.utBPClickDelay;
    }
}