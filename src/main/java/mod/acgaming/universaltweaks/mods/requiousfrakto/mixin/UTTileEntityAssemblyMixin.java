package mod.acgaming.universaltweaks.mods.requiousfrakto.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.tileentity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import requious.tile.TileEntityAssembly;

// Courtesy of jchung01
@Mixin(value = TileEntityAssembly.class)
public abstract class UTTileEntityAssemblyMixin extends TileEntity
{
    /**
     * @reason Updating MachineVisuals should only be needed client-side.
     * This is the earliest we can check the side before calls to IProxy
     * methods for spawning particles.
     */
    @ModifyExpressionValue(method = "update", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;hasNext()Z", remap = false))
    private boolean utCheckSide(boolean original)
    {
        if (!this.world.isRemote) return false;
        return original;
    }
}
