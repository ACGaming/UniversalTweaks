package mod.acgaming.universaltweaks.mods.tombmanygraves.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.m4thg33k.tombmanygraves.events.CommonEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(value = CommonEvents.class, remap = false)
public abstract class UTCommonEventsMixin
{
    /**
     * @author WaitingIdly
     * @reason Replace the check for the world height that uses {@code 0 <= y < }{@link World#getActualHeight()}
     * with {@link World#isOutsideBuildHeight}, as the former can be incorrect
     * if {@link World#getActualHeight()} doesn't reflect the true height of the world,
     * or if Cubic Chunks or another similar mod that expands world height is enabled.
     * <p>
     * Note that {@code x < y} also matches {@code x >= y},
     * and so if this applied to both checks it would internally be
     * {@code !utCheckWorldSize() && utCheckWorldSize()},
     * which would make it always false.
     */
    @Definition(id = "pos", local = @Local(type = BlockPos.class, argsOnly = true))
    @Definition(id = "getY", method = "Lnet/minecraft/util/math/BlockPos;getY()I")
    @Expression("pos.getY() < ?")
    @ModifyExpressionValue(method = "isValidLocation", at = @At(value = "MIXINEXTRAS:EXPRESSION", ordinal = 0))
    private boolean utCheckWorldSize(boolean original, @Local(argsOnly = true) World world, @Local(argsOnly = true) BlockPos pos)
    {
        return world.isOutsideBuildHeight(pos);
    }

    /**
     * @author WaitingIdly
     * @reason Skip the actual height check, since the logic is already handled in {@link #utCheckWorldSize}.
     */
    @Definition(id = "getActualHeight", method = "Lnet/minecraft/world/World;getActualHeight()I")
    @Definition(id = "world", local = @Local(type = World.class, argsOnly = true))
    @Expression("? < world.getActualHeight()")
    @WrapOperation(method = "isValidLocation", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean utSkipActualHeightCheck(int left, int right, Operation<Boolean> original)
    {
        return true;
    }
}
