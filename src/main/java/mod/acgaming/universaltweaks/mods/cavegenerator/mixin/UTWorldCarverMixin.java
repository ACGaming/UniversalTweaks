package mod.acgaming.universaltweaks.mods.cavegenerator.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.personthecat.cavegenerator.world.generator.WorldCarver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = WorldCarver.class, remap = false)
public abstract class UTWorldCarverMixin
{
    @Definition(id = "cZ", local = @Local(type = int.class, ordinal = 4, argsOnly = true))
    @Definition(id = "x", local = @Local(type = int.class, ordinal = 0, argsOnly = true))
    @Expression("cZ * 16 + x")
    @ModifyExpressionValue(method = "evaluatePond", at = @At("MIXINEXTRAS:EXPRESSION"))
    private int utEvaluatePond(int orginal, @Local(argsOnly = true, index = 4) int x, @Local(argsOnly = true, index = 6) int z)
    {
        return orginal - x + z;
    }
}
