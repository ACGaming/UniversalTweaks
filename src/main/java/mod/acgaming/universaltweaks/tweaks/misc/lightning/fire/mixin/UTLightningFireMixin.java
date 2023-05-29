package mod.acgaming.universaltweaks.tweaks.misc.lightning.fire.mixin;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import zone.rong.mixinextras.injector.WrapWithCondition;

@Mixin(EntityLightningBolt.class)
public class UTLightningFireMixin
{
    @WrapWithCondition(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Z"))
    public boolean utLightningFire(World instance, BlockPos pos, IBlockState state)
    {
        return !UTConfig.TWEAKS_MISC.LIGHTNING.utLightningFireToggle;
    }
}