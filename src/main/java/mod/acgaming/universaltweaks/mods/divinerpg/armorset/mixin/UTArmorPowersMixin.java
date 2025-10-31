package mod.acgaming.universaltweaks.mods.divinerpg.armorset.mixin;

import java.lang.ref.WeakReference;
import java.util.Objects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import divinerpg.capabilities.armor.ArmorPowers;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ArmorPowers.class, remap = false)
public class UTArmorPowersMixin
{
    @Shadow
    @Final
    private WeakReference<EntityLivingBase> player;

    /**
     * @reason The UUID check is inverted; unsubscribe() must only be called when the UUID is the same (the same player),
     * but EntityPlayer object is different (the entity of the player was recreated), not on ANY entity join
     * <br>
     * Also only cleanup on the matching side, fixing possible ConcurrentModificationException and corrupting the EventBus.
     * @see <a href="https://github.com/DivineRPG/DivineRPG/commit/a1429c46a2bb5edad4979b5854b57c64b7a5e7b6?diff=split#diff-ada324cf157705224cfc240578dfdf6ff491085063010e5440de9f00c5a881baL53">the originally correct condition</a>
     */
    @Definition(id = "equals", method = "Ljava/util/Objects;equals(Ljava/lang/Object;Ljava/lang/Object;)Z")
    @Expression("equals(?, ?) == false")
    @ModifyExpressionValue(method = "onCleanUp", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean utOnlyCleanupRecreatedPlayer(boolean notEqual, EntityJoinWorldEvent event)
    {
        EntityLivingBase oldPlayer = Objects.requireNonNull(player.get());
        // !(!equals) -> equals
        return !notEqual && event.getWorld().isRemote == oldPlayer.world.isRemote;
    }
}
