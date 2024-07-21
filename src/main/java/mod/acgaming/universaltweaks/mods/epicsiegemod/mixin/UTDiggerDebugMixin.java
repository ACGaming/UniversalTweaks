package mod.acgaming.universaltweaks.mods.epicsiegemod.mixin;

import java.io.PrintStream;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import funwayguy.epicsiegemod.ai.additions.AdditionDigger;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AdditionDigger.class)
public class UTDiggerDebugMixin
{
    @WrapWithCondition(method = "isValid", at = @At(value = "INVOKE", target = "Ljava/io/PrintStream;println(Ljava/lang/String;)V"), remap = false)
    private boolean utDiggerDebug(PrintStream instance, String s)
    {
        return !UTConfigMods.EPIC_SIEGE_MOD.utESMDiggerDebugToggle;
    }
}