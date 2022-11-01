package mod.acgaming.universaltweaks.tweaks.mixin;

import net.minecraft.block.BlockReed;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BlockReed.class)
public class UTSugarCaneSizeMixin
{
    @ModifyConstant(method = "updateTick", constant = @Constant(intValue = 3))
    public int utSugarCaneSize(int constant)
    {
        return UTConfig.tweaks.utSugarCaneSize;
    }
}