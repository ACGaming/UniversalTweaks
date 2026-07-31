package mod.acgaming.universaltweaks.mods.ironchests.mixin;

import cpw.mods.ironchest.common.tileentity.chest.TileEntityIronChest;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = TileEntityIronChest.class, remap = false)
public abstract class UTTileEntityIronChestMixin
{
    @Inject(method = "receiveClientEvent", at = @At("RETURN"), cancellable = true, remap = true)
    private void utMarkHandledEvents(int id, int type, CallbackInfoReturnable<Boolean> cir)
    {
        if (id == 1 || id == 2 || id == 3)
        {
            cir.setReturnValue(true);
        }
    }
}