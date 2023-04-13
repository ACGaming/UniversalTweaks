package mod.acgaming.universaltweaks.mods.roost.contenttweaker.mixin;

import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.contenttweaker.modules.chickens.ChickenModule;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import mod.acgaming.universaltweaks.mods.roost.contenttweaker.UTChickenRegistration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of jchung01
@Mixin(value = ChickenModule.class, remap = false)
public abstract class UTChickenModuleMixin extends ModuleBase
{
    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lcom/teamacronymcoders/contenttweaker/modules/chickens/ChickenFactory;registerChickens()V"), cancellable = true)
    public void utCTChickenInit(CallbackInfo ci)
    {
        if (!UTConfig.MOD_INTEGRATION.ROOST.utRoostEarlyRegisterCTChickens) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTChickenModuleMixin ::: Finish setting up chickens (init)");

        UTChickenRegistration.utInitChickens();
        // ignore ChickenFactory.registerChickens() and use instance methods instead
        ci.cancel();
    }
}