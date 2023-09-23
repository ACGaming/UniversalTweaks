package mod.acgaming.universaltweaks.tweaks.entities.spawning.golem.mixin;

import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockPumpkin.class)
public abstract class UTGolemsMixin
{
    @Inject(method = "trySpawnGolem", at = @At("HEAD"), cancellable = true)
    public void utGolemSpawnCheck(World worldIn, BlockPos pos, CallbackInfo ci)
    {
        if (UTConfigTweaks.ENTITIES.NO_GOLEMS.utNGSnowGolemToggle && this.getSnowmanPattern().match(worldIn, pos) != null)
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTNoGolems ::: No snow golem spawn");
            ci.cancel();
        }
        if (UTConfigTweaks.ENTITIES.NO_GOLEMS.utNGIronGolemToggle && this.getGolemPattern().match(worldIn, pos) != null)
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTNoGolems ::: No iron golem spawn");
            ci.cancel();
        }
    }

    @Shadow
    protected abstract BlockPattern getSnowmanPattern();

    @Shadow
    protected abstract BlockPattern getGolemPattern();
}