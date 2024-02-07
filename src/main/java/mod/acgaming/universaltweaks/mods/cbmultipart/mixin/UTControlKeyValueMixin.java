package mod.acgaming.universaltweaks.mods.cbmultipart.mixin;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;

import codechicken.multipart.ControlKeyModifer;
import codechicken.multipart.ControlKeyModifer$;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of jchung01
/**
 * Note: To my knowledge, {@link ControlKeyModifer#isControlDown(EntityPlayer)} is never called in any mod, so this mixin isn't very useful.
 */
@Mixin(value = ControlKeyModifer.ControlKeyValue.class, remap = false)
public class UTControlKeyValueMixin
{
    @Final
    @Mutable
    @Shadow
    private EntityPlayer p;
    @Unique
    private UUID universalTweaks$id;

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void utSetUUID(EntityPlayer p, CallbackInfo ci)
    {
        universalTweaks$id = p.getUniqueID();
        this.p = null;
    }

    /**
     * @see ControlKeyModifer$#isControlDown(EntityPlayer) for where this is called
     */
    @ModifyArg(method = "isControlDown", at = @At(value = "INVOKE", target = "Lscala/collection/mutable/Map;apply(Ljava/lang/Object;)Ljava/lang/Object;"))
    private Object utGetByUUID(Object thisPlayer)
    {
        return universalTweaks$id;
    }
}
