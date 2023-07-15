package mod.acgaming.universaltweaks.mods.thaumcraft.enderio.mixin;

import net.minecraft.entity.player.EntityPlayer;

import crazypants.enderio.base.handler.darksteel.gui.DSUContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import thaumcraft.api.items.ItemsTC;

// Courtesy of Focamacho
@Mixin(value = DSUContainer.class, remap = false)
public class UTDSUContainerMixin
{
    @Inject(method = "func_75145_c", at = @At("HEAD"), cancellable = true)
    private void canInteractWith(EntityPlayer player, CallbackInfoReturnable<Boolean> info)
    {
        if (((DSUContainer) (Object) this).getSlot(1).getStack().getItem().equals(ItemsTC.primordialPearl)) info.setReturnValue(false);
    }
}