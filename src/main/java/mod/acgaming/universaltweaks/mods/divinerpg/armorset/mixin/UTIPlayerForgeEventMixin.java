package mod.acgaming.universaltweaks.mods.divinerpg.armorset.mixin;

import java.util.Objects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.eventhandler.Event;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import divinerpg.api.armor.IPlayerSubscription;
import divinerpg.api.armor.binded.IPlayerForgeEvent;
import divinerpg.api.armor.registry.IForgeEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of jchung01
@Mixin(value = IPlayerForgeEvent.class, remap = false)
public interface UTIPlayerForgeEventMixin<T extends Event> extends IForgeEvent<T>, IPlayerSubscription
{
    /**
     * Use {@link java.util.Objects#equals(Object, Object)} to handle based on entity id.
     * The armor set tweak deduplicates event handler registration, so player equality must be based on their id to handle both sides.
     */
    @SuppressWarnings("PublicStaticMixinMember")
    @Definition(id = "player", local = @Local(type = EntityLivingBase.class, name = "player"))
    @Expression("? == player")
    @WrapOperation(method = "canHandle", at = @At("MIXINEXTRAS:EXPRESSION"))
    static boolean ut$handlePlayerById(Object left, Object right, Operation<Boolean> original) {
        return Objects.equals(left, right);
    }
}
