package mod.acgaming.universaltweaks.tweaks.misc.lightning.fire.mixin;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityLightningBolt.class)
public class UTLightningFireMixin
{
    @WrapWithCondition(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Z"))
    public boolean utLightningFireInit(World instance, BlockPos pos, IBlockState state)
    {
        return !UTConfigTweaks.MISC.LIGHTNING.utLightningFireToggle;
    }

    @WrapWithCondition(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Z"))
    public boolean utLightningFireUpdate(World instance, BlockPos pos, IBlockState state)
    {
        return !UTConfigTweaks.MISC.LIGHTNING.utLightningFireToggle;
    }
}