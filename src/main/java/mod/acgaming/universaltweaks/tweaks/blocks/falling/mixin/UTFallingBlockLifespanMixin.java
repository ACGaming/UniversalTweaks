package mod.acgaming.universaltweaks.tweaks.blocks.falling.mixin;

import net.minecraft.entity.item.EntityFallingBlock;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EntityFallingBlock.class)
public class UTFallingBlockLifespanMixin
{
    @ModifyConstant(method = "onUpdate", constant = @Constant(intValue = 600))
    public int utFallingBlockLifespan(int constant)
    {
        return UTConfigTweaks.BLOCKS.utFallingBlockLifespan;
    }
}