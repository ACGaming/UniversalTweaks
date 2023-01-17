package mod.acgaming.universaltweaks.tweaks.dyeblending.mixin;

import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import mod.acgaming.universaltweaks.tweaks.dyeblending.UTDyeBlending;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of Darkhax
@Mixin(EntitySheep.class)
public class UTDyeBlendingMixin
{
    /**
     * This patch is added to replace how the offspring color of two sheep is decided. The
     * normal behavior gets dye items for both of the parents and tries to find crafting
     * recipes that use them. This approach can cause unintentional bugs with mods that change
     * recipes, and is also not very efficient. This patch attempts to fix this by replacing
     * the crafting lookup with a 2d lookup array of the correct meta values.
     */
    @Inject(method = "getDyeColorMixFromParents(Lnet/minecraft/entity/passive/EntityAnimal;Lnet/minecraft/entity/passive/EntityAnimal;)Lnet/minecraft/item/EnumDyeColor;", at = @At("HEAD"), cancellable = true)
    public void utGetDyeColorMixFromParents(EntityAnimal father, EntityAnimal mother, CallbackInfoReturnable<EnumDyeColor> info)
    {
        if (!UTConfig.TWEAKS_PERFORMANCE.utDyeBlendingToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTDyeBlendingMixin ::: Get dye color mix from parents");
        // Ensure both entities are sheep.
        if (father instanceof EntitySheep && mother instanceof EntitySheep)
        {
            // Get dye colors of both parents.
            final EnumDyeColor fatherColor = ((EntitySheep) father).getFleeceColor();
            final EnumDyeColor motherColor = ((EntitySheep) mother).getFleeceColor();
            // Try to blend colors using pre-made blending table.
            final EnumDyeColor blended = UTDyeBlending.getBlendedColor(fatherColor, motherColor);
            // If they can blend, return the value from the blended table.
            if (blended != null) info.setReturnValue(blended);
                // If not, randomly select one of the parent colors 50/50.
                // This is default logic for this method when no matches are found and lets us skip
                // recipe lookups.
            else info.setReturnValue(father.world.rand.nextBoolean() ? fatherColor : motherColor);
        }
    }
}