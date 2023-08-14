package mod.acgaming.universaltweaks.mods.industrialforegoing.mixin;

import net.minecraft.entity.Entity;

import com.buuz135.industrial.proxy.block.upgrade.ConveyorInsertionUpgrade;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of Focamacho
@Mixin(value = ConveyorInsertionUpgrade.class, remap = false)
public class UTConveyorInsertionUpgradeMixin
{
    @Inject(method = "handleEntity", at = @At("HEAD"), cancellable = true)
    private void handleEntity(Entity entity, CallbackInfo info)
    {
        if (entity.isDead) info.cancel();
    }
}