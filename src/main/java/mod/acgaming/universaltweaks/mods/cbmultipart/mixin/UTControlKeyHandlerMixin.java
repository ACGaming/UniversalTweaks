package mod.acgaming.universaltweaks.mods.cbmultipart.mixin;

import net.minecraft.entity.player.EntityPlayer;

import codechicken.multipart.ControlKeyHandler$;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// Courtesy of jchung01
@Mixin(value = ControlKeyHandler$.class, remap = false)
public class UTControlKeyHandlerMixin
{
    @ModifyArg(method = "tick", at = @At(value = "INVOKE", target = "Lscala/collection/mutable/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Lscala/Option;"), index = 0)
    private Object utPutWithUUID(Object playerObj)
    {
        return ((EntityPlayer) playerObj).getGameProfile().getId();
    }
}
