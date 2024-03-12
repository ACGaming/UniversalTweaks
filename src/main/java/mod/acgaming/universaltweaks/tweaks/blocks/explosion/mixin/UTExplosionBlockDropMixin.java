package mod.acgaming.universaltweaks.tweaks.blocks.explosion.mixin;

import net.minecraft.world.Explosion;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Explosion.class)
public class UTExplosionBlockDropMixin
{
    @ModifyConstant(method = "doExplosionB", constant = @Constant(floatValue = 1.0F, ordinal = 1))
    public float utExplosionBlockDropChance(float constant)
    {
        return (float) UTConfigTweaks.BLOCKS.utExplosionDropChance;
    }
}