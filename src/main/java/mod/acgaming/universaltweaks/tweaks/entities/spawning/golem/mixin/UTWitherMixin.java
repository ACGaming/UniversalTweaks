package mod.acgaming.universaltweaks.tweaks.entities.spawning.golem.mixin;

import net.minecraft.block.BlockSkull;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.tileentity.TileEntitySkull;
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

@Mixin(BlockSkull.class)
public abstract class UTWitherMixin
{
    @Inject(method = "checkWitherSpawn", at = @At("HEAD"), cancellable = true)
    public void utWitherSpawnCheck(World worldIn, BlockPos pos, TileEntitySkull te, CallbackInfo ci)
    {
        if (UTConfigTweaks.ENTITIES.NO_GOLEMS.utNGWitherToggle && this.getWitherPattern().match(worldIn, pos) != null)
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTNoGolems ::: No wither spawn");
            ci.cancel();
        }
    }

    @Shadow
    protected abstract BlockPattern getWitherPattern();
}