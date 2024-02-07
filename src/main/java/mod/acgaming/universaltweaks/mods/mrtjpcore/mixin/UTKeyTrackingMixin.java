package mod.acgaming.universaltweaks.mods.mrtjpcore.mixin;

import net.minecraft.entity.player.EntityPlayer;

import mrtjp.core.data.KeyTracking$;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// Courtesy of jchung01
@Mixin(value = KeyTracking$.class, remap = false)
public class UTKeyTrackingMixin
{
    @ModifyArg(method = "updatePlayerKey", at = @At(value = "INVOKE", target = "Lscala/Predef$;ArrowAssoc(Ljava/lang/Object;)Ljava/lang/Object;"))
    private Object utUpdateUUIDKey(Object playerObj)
    {
        // Set key as UUID, not EntityPlayer
        return ((EntityPlayer) playerObj).getUniqueID();
    }

    // This doesn't seem to be necessary
//    @ModifyArg(method = "registerTracker", at = @At(value = "INVOKE", target = "Lscala/collection/mutable/HashMap;getOrElseUpdate(Ljava/lang/Object;Lscala/Function0;)Ljava/lang/Object;"), index = 1)
//    private Function0<Map<Object, Object>> utRegisterUUIDMap(Function0<Map<Object, Object>> par2)
//    {
//        final class NamelessClass_2 extends AbstractFunction0<Map<Object, Object>> implements Serializable
//        {
//            public Map<Object, Object> apply() {
//                return ((Map<Object, Object>)scala.collection.mutable.HashMap$.MODULE$.apply(HashMap$.MODULE$.empty().toSeq())).withDefaultValue(BoxesRunTime.boxToBoolean(false));
//            }
//
//            public NamelessClass_2() {
//            }
//        }
//        return new NamelessClass_2();
//    }

    @ModifyArg(method = "isKeyDown", at = @At(value = "INVOKE", target = "Lscala/collection/MapLike;apply(Ljava/lang/Object;)Ljava/lang/Object;"), index = 0)
    private Object utIsKeyDownForUUID(Object playerObj)
    {
        // Get value using UUID key
        return ((EntityPlayer) playerObj).getUniqueID();
    }
}
