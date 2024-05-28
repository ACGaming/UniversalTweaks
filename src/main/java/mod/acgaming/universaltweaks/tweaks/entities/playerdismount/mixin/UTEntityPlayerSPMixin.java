package mod.acgaming.universaltweaks.tweaks.entities.playerdismount.mixin;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MovementInput;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.tweaks.entities.playerdismount.UTDismountKeybind;

// Courtesy of WaitingIdly, TheRandomLabs (RandomPatches)
@Mixin(value = EntityPlayerSP.class)
public abstract class UTEntityPlayerSPMixin
{
    @WrapOperation(method = "onUpdate", at = @At(value = "FIELD", target = "Lnet/minecraft/util/MovementInput;sneak:Z"))
    public boolean utOnUpdate(MovementInput instance, Operation<Boolean> original)
    {
        if (!UTConfigTweaks.MISC.utUseSeparateDismountKey) return original.call(instance);
        return UTDismountKeybind.key.getKey().isKeyDown();
    }
}