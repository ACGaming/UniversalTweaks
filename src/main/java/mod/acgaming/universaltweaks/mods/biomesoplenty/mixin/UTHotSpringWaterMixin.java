package mod.acgaming.universaltweaks.mods.biomesoplenty.mixin;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import biomesoplenty.common.fluids.blocks.BlockHotSpringWaterFluid;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockHotSpringWaterFluid.class)
public class UTHotSpringWaterMixin
{
    @Inject(method = "onEntityCollision", at = @At(value = "HEAD"), cancellable = true)
    public void utHotSpringWater(World world, BlockPos pos, IBlockState state, Entity entity, CallbackInfo ci)
    {
        if (!UTConfigMods.BIOMES_O_PLENTY.utBoPHotSpringWaterToggle) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTHotSpringWater ::: Check regeneration effect");
        if (entity instanceof EntityLivingBase && ((EntityLivingBase) entity).getActivePotionEffect(MobEffects.REGENERATION) != null) ci.cancel();
    }
}