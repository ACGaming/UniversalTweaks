package mod.acgaming.universaltweaks.tweaks.misc.glint.potion.mixin;

import java.util.List;

import net.minecraft.item.ItemPotion;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of WaitingIdly, TheRandomLabs (RandomPatches)
@Mixin(value = ItemPotion.class)
public abstract class UTItemPotionMixin
{
    @WrapOperation(method = "hasEffect", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z"))
    private boolean utHasEffect(List<?> instance, Operation<Boolean> original)
    {
        if (!UTConfigTweaks.MISC.utDisablePotionGlint) return original.call(instance);
        return true;
    }
}