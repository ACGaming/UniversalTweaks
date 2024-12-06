package mod.acgaming.universaltweaks.tweaks.misc.advancements.guisize.mixin;

import net.minecraft.client.gui.advancements.AdvancementTabType;
import net.minecraft.client.gui.advancements.GuiAdvancementTab;
import net.minecraft.client.gui.advancements.GuiScreenAdvancements;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.tweaks.misc.advancements.guisize.UTAdvancementInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

// Courtesy of WaitingIdly
@Mixin(GuiAdvancementTab.class)
public abstract class UTGuiAdvancementTabMixin
{
    @WrapOperation(method = "create", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/advancements/AdvancementTabType;MAX_TABS:I"))
    private static int utAdjustMaxTabs(Operation<Integer> original, @Local(argsOnly = true) GuiScreenAdvancements screen)
    {
        if (!UTConfigTweaks.MISC.ADVANCEMENTS.utAdvancementsToggle || !UTConfigTweaks.MISC.ADVANCEMENTS.utSizeToggle) return original.call();
        return UTAdvancementInfo.utMaximumTabCountPerPage(screen.width, screen.height);
    }

    @WrapOperation(method = "create", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/advancements/AdvancementTabType;getMax()I"))
    private static int utAdjustIndividualMaxTabs(AdvancementTabType instance, Operation<Integer> original, @Local(argsOnly = true) GuiScreenAdvancements screen)
    {
        if (!UTConfigTweaks.MISC.ADVANCEMENTS.utAdvancementsToggle || !UTConfigTweaks.MISC.ADVANCEMENTS.utSizeToggle) return original.call(instance);
        return UTAdvancementInfo.utTabCountForSide(screen.width, screen.height, instance == AdvancementTabType.LEFT || instance == AdvancementTabType.RIGHT);
    }

    @Shadow
    @Final
    private GuiScreenAdvancements screen;

    // update the size
    @ModifyConstant(method = {"drawContents", "scroll", "drawToolTips"}, constant = @Constant(intValue = UTAdvancementInfo.DEFAULT_WIDTH - 18))
    private int utDrawWidth(int original)
    {
        if (!UTConfigTweaks.MISC.ADVANCEMENTS.utAdvancementsToggle || !UTConfigTweaks.MISC.ADVANCEMENTS.utSizeToggle) return original;
        return UTAdvancementInfo.utPageWidth(screen.width, 2) - 4;
    }

    @ModifyConstant(method = {"drawContents", "scroll", "drawToolTips"}, constant = @Constant(intValue = UTAdvancementInfo.DEFAULT_HEIGHT - 27))
    private int utDrawHeight(int original)
    {
        if (!UTConfigTweaks.MISC.ADVANCEMENTS.utAdvancementsToggle || !UTConfigTweaks.MISC.ADVANCEMENTS.utSizeToggle) return original;
        return UTAdvancementInfo.utPageHeight(screen.height, 3);
    }

    // origin of the shown tree within the scrollable space
    @ModifyConstant(method = "drawContents", constant = @Constant(intValue = (UTAdvancementInfo.DEFAULT_WIDTH - 18) / 2))
    private int utDrawContentsWidth(int original)
    {
        if (!UTConfigTweaks.MISC.ADVANCEMENTS.utAdvancementsToggle || !UTConfigTweaks.MISC.ADVANCEMENTS.utSizeToggle) return original;
        return (UTAdvancementInfo.utPageWidth(screen.width, 2) - 4) / 2;
    }

    @ModifyConstant(method = "drawContents", constant = @Constant(intValue = (UTAdvancementInfo.DEFAULT_HEIGHT - 27) / 2))
    private int utDrawContentsHeight(int original)
    {
        if (!UTConfigTweaks.MISC.ADVANCEMENTS.utAdvancementsToggle || !UTConfigTweaks.MISC.ADVANCEMENTS.utSizeToggle) return original;
        return UTAdvancementInfo.utPageHeight(screen.height, 3) / 2;
    }

    // repeat the texture to fill up the drawn space
    @ModifyConstant(method = "drawContents", constant = @Constant(intValue = 15))
    private int utRepeatingTextureWidth(int original)
    {
        if (!UTConfigTweaks.MISC.ADVANCEMENTS.utAdvancementsToggle || !UTConfigTweaks.MISC.ADVANCEMENTS.utSizeToggle) return original;
        return (UTAdvancementInfo.utPageWidth(screen.width) - 4) / 16 + 1;
    }

    @ModifyConstant(method = "drawContents", constant = @Constant(intValue = 8))
    private int utRepeatingTextureHeight(int original)
    {
        if (!UTConfigTweaks.MISC.ADVANCEMENTS.utAdvancementsToggle || !UTConfigTweaks.MISC.ADVANCEMENTS.utSizeToggle) return original;
        return UTAdvancementInfo.utPageHeight(screen.height) / 16 + 1;
    }

    // prevents changing the fade value from 0
    @ModifyExpressionValue(method = "drawToolTips", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(FFF)F"))
    private float utRemoveFade(float original)
    {
        if (!UTConfigTweaks.MISC.ADVANCEMENTS.utAdvancementsToggle || !UTConfigTweaks.MISC.ADVANCEMENTS.utDisableFadeOnHover) return original;
        return 0;
    }
}
