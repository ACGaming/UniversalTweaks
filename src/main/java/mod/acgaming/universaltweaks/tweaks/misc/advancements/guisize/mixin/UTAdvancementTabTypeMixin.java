package mod.acgaming.universaltweaks.tweaks.misc.advancements.guisize.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.advancements.AdvancementTabType;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.tweaks.misc.advancements.guisize.UTAdvancementInfo;

// Courtesy of WaitingIdly
@Mixin(value = AdvancementTabType.class)
public abstract class UTAdvancementTabTypeMixin
{
    @WrapOperation(method = "draw", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/advancements/AdvancementTabType;max:I"))
    private int utIndex(AdvancementTabType instance, Operation<Integer> original, @Local(argsOnly = true, ordinal = 2) int index)
    {
        if (!UTConfigTweaks.MISC.ADVANCEMENTS.utAdvancementsToggle || !UTConfigTweaks.MISC.ADVANCEMENTS.utSizeToggle || Minecraft.getMinecraft().currentScreen == null)
        {
            return original.call(instance);
        }
        return UTAdvancementInfo.utTabCountForSide(Minecraft.getMinecraft().currentScreen.width, Minecraft.getMinecraft().currentScreen.height, instance == AdvancementTabType.LEFT || instance == AdvancementTabType.RIGHT);
    }

    @ModifyConstant(method = "getX", constant = @Constant(intValue = UTAdvancementInfo.DEFAULT_WIDTH - 4))
    private int utOverrideX(int original)
    {
        if (!UTConfigTweaks.MISC.ADVANCEMENTS.utAdvancementsToggle || !UTConfigTweaks.MISC.ADVANCEMENTS.utSizeToggle || Minecraft.getMinecraft().currentScreen == null)
        {
            return original;
        }
        return UTAdvancementInfo.utPageWidth(Minecraft.getMinecraft().currentScreen.width) - 8;
    }

    @ModifyConstant(method = "getY", constant = @Constant(intValue = UTAdvancementInfo.DEFAULT_HEIGHT - 4))
    private int utOverrideY(int original)
    {
        if (!UTConfigTweaks.MISC.ADVANCEMENTS.utAdvancementsToggle || !UTConfigTweaks.MISC.ADVANCEMENTS.utSizeToggle || Minecraft.getMinecraft().currentScreen == null)
        {
            return original;
        }
        return UTAdvancementInfo.utPageHeight(Minecraft.getMinecraft().currentScreen.height) - 4;
    }
}
