package mod.acgaming.universaltweaks.tweaks.misc.bannerlayers.mixin;

import net.minecraft.item.crafting.RecipesBanners;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(RecipesBanners.RecipeAddPattern.class)
public class UTBannerLayersMixin
{
    @ModifyConstant(method = "matches", constant = @Constant(intValue = 6))
    public int utBannerLayers(int constant)
    {
        return UTConfigTweaks.MISC.utBannerLayers;
    }
}