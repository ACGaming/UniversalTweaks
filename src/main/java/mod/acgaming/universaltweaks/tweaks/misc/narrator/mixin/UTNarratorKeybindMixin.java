package mod.acgaming.universaltweaks.tweaks.misc.narrator.mixin;

import net.minecraft.client.Minecraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of WaitingIdly
@Mixin(value = Minecraft.class)
public class UTNarratorKeybindMixin
{
    @ModifyConstant(method = "dispatchKeypresses", constant = @Constant(intValue = 48))
    public int utNarratorSay(int original)
    {
        if (!UTConfigTweaks.MISC.utUseCustomNarratorKeybind) return original;
        return 0;
    }
}