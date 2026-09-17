package mod.acgaming.universaltweaks.tweaks.misc.advancements.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Minecraft.class)
public abstract class UTAdvKeyBindMinecraftMixin
{
    @WrapWithCondition(method = "processKeyBinds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V", ordinal = 1))
    public boolean utAdvancementsKeyBinding3(Minecraft instance, GuiScreen guiScreenIn)
    {
        return !UTConfigTweaks.MISC.utDisableAdvancementsToggle;
    }
}
