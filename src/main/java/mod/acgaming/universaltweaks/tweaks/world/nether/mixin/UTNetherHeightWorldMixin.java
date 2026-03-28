package mod.acgaming.universaltweaks.tweaks.world.nether.mixin;

import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(World.class)
public class UTNetherHeightWorldMixin
{
    @Shadow
    public WorldProvider provider;

    @ModifyConstant(method = "isOutsideBuildHeight", constant = @Constant(intValue = 256))
    public int utNetherHeightWorld(int value)
    {
        return provider.isNether() ? provider.getActualHeight() : value;
    }
}
