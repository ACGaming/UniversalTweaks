package mod.acgaming.universaltweaks.mods.astralsorcery.neromanticprime.mixin;

import hellfirepvp.astralsorcery.common.tile.TileBore;
import mod.acgaming.universaltweaks.config.UTConfigMods;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = TileBore.class, remap = false)
public abstract class UTConfigurableFluidVeinExtractionQuantityMixin {

    // Change both occurrences of 300 in rand.nextInt(300) + 300
    @ModifyConstant(
        method = "playBoreLiquidEffect",
        constant = @Constant(intValue = 300),
        remap = false
    )
    private int changeDrainConstant(int original) {
        return UTConfigMods.ASTRAL_SORCERY.utNeromanticPrimeExtractionQuantity;
    }
}