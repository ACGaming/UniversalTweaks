package mod.acgaming.universaltweaks.mods.roots.spiritdrops.mixin;

import net.minecraftforge.oredict.OreDictionary;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import epicsquid.mysticalworld.materials.Materials;
import epicsquid.roots.recipe.SpiritDrops;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

// Courtesy of WaitingIdly
@Mixin(value = SpiritDrops.class, remap = false)
public abstract class UTSpiritDropsMixin
{
    /**
     * @author WaitingIdly
     * @reason Check that the oredict exists before adding it
     */
    @WrapWithCondition(method = "<clinit>", at = @At(value = "INVOKE", target = "Lepicsquid/roots/recipe/SpiritDrops;addPouch(Lepicsquid/roots/recipe/SpiritDrops$SpiritItem;)V", ordinal = 0),
        slice = @Slice(from = @At(value = "INVOKE", target = "Lepicsquid/roots/recipe/SpiritDrops$OreSpiritItem;<init>(Ljava/lang/String;I)V"),
            to = @At(value = "INVOKE", target = "Lepicsquid/roots/recipe/SpiritDrops;addReliquary(Lepicsquid/roots/recipe/SpiritDrops$SpiritItem;)V")))
    private static boolean utEnsureCopperNuggetsExist(SpiritDrops.SpiritItem item)
    {
        return OreDictionary.doesOreNameExist("nugget" + Materials.copper.getOredictNameSuffix());
    }

    /**
     * @author WaitingIdly
     * @reason Check that the oredict exists before adding it
     */
    @WrapWithCondition(method = "<clinit>", at = @At(value = "INVOKE", target = "Lepicsquid/roots/recipe/SpiritDrops;addPouch(Lepicsquid/roots/recipe/SpiritDrops$SpiritItem;)V", ordinal = 1),
        slice = @Slice(from = @At(value = "INVOKE", target = "Lepicsquid/roots/recipe/SpiritDrops$OreSpiritItem;<init>(Ljava/lang/String;I)V"),
            to = @At(value = "INVOKE", target = "Lepicsquid/roots/recipe/SpiritDrops;addReliquary(Lepicsquid/roots/recipe/SpiritDrops$SpiritItem;)V")))
    private static boolean utEnsureSilverNuggetsExist(SpiritDrops.SpiritItem item)
    {
        return OreDictionary.doesOreNameExist("nugget" + Materials.silver.getOredictNameSuffix());
    }

    /**
     * @author WaitingIdly
     * @reason Check that the oredict exists before adding it
     */
    @WrapWithCondition(method = "<clinit>", at = @At(value = "INVOKE", target = "Lepicsquid/roots/recipe/SpiritDrops;addPouch(Lepicsquid/roots/recipe/SpiritDrops$SpiritItem;)V", ordinal = 2),
        slice = @Slice(from = @At(value = "INVOKE", target = "Lepicsquid/roots/recipe/SpiritDrops$OreSpiritItem;<init>(Ljava/lang/String;I)V"),
            to = @At(value = "INVOKE", target = "Lepicsquid/roots/recipe/SpiritDrops;addReliquary(Lepicsquid/roots/recipe/SpiritDrops$SpiritItem;)V")))
    private static boolean utEnsureCopperIngotsExist(SpiritDrops.SpiritItem item)
    {
        return OreDictionary.doesOreNameExist("ingot" + Materials.copper.getOredictNameSuffix());
    }

    /**
     * @author WaitingIdly
     * @reason Check that the oredict exists before adding it
     */
    @WrapWithCondition(method = "<clinit>", at = @At(value = "INVOKE", target = "Lepicsquid/roots/recipe/SpiritDrops;addPouch(Lepicsquid/roots/recipe/SpiritDrops$SpiritItem;)V", ordinal = 3),
        slice = @Slice(from = @At(value = "INVOKE", target = "Lepicsquid/roots/recipe/SpiritDrops$OreSpiritItem;<init>(Ljava/lang/String;I)V"),
            to = @At(value = "INVOKE", target = "Lepicsquid/roots/recipe/SpiritDrops;addReliquary(Lepicsquid/roots/recipe/SpiritDrops$SpiritItem;)V")))
    private static boolean utEnsureSilverIngotsExist(SpiritDrops.SpiritItem item)
    {
        return OreDictionary.doesOreNameExist("ingot" + Materials.silver.getOredictNameSuffix());
    }
}
