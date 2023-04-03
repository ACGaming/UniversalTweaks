package mod.acgaming.universaltweaks.tweaks.blocks.growthsize.mixin;

import net.minecraft.block.BlockCactus;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BlockCactus.class)
public class UTCactusSizeMixin
{
    @ModifyConstant(method = "updateTick", constant = @Constant(intValue = 3))
    public int utCactusSize(int constant)
    {
        return UTConfig.TWEAKS_BLOCKS.utCactusSize;
    }
}