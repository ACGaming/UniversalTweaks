package mod.acgaming.universaltweaks.tweaks.realms.mixin;

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import zone.rong.mixinextras.injector.WrapWithCondition;

@Mixin(GuiOptions.class)
public class UTRealmsButtonOptionsMixin
{
    @WrapWithCondition(method = "initGui", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 4))
    public boolean utRemoveRealmsButtonOptions(List<GuiButton> list, Object e)
    {
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTRealmsButtonOptions ::: Initialize buttons");
        return !UTConfig.TWEAKS_MISC.utRealmsButtonToggle;
    }
}