package mod.acgaming.universaltweaks.mods.elenaidodge2.mixin;

import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

import com.elenai.elenaidodge2.api.FeathersHelper;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = FeathersHelper.class, remap = false)
public class UTED2FeathersHelperMixin
{
    @WrapWithCondition(method = "increaseFeathers", at = @At(value = "INVOKE", target = "Lcom/elenai/elenaidodge2/util/Utils;showDodgeBar()V"))
    private static boolean utIncreaseFeathers()
    {
        return !UTConfigMods.ELENAI_DODGE_2.utED2FeathersHelperToggle || FMLLaunchHandler.side().isClient();
    }

    @WrapWithCondition(method = "decreaseFeathers", at = @At(value = "INVOKE", target = "Lcom/elenai/elenaidodge2/util/Utils;showDodgeBar()V"))
    private static boolean utDecreaseFeathers()
    {
        return !UTConfigMods.ELENAI_DODGE_2.utED2FeathersHelperToggle || FMLLaunchHandler.side().isClient();
    }
}