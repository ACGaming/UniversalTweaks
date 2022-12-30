package mod.acgaming.universaltweaks.mods.epicsiegemod.mixin;

import java.io.PrintStream;

import funwayguy.epicsiegemod.ai.additions.AdditionDigger;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import zone.rong.mixinextras.injector.WrapWithCondition;

@Mixin(AdditionDigger.class)
public class UTDiggerDebugMixin
{
    @WrapWithCondition(method = "isValid", at = @At(value = "INVOKE", target = "Ljava/io/PrintStream;println(Ljava/lang/String;)V"), remap = false)
    private boolean utDiggerDebug(PrintStream instance, String s)
    {
        return !UTConfig.mods.utESMDiggerDebugToggle;
    }
}