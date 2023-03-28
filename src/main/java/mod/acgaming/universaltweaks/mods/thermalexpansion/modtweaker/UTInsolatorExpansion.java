package mod.acgaming.universaltweaks.mods.thermalexpansion.modtweaker;

import java.util.Set;

import net.minecraft.item.ItemStack;

import com.blamejared.compat.thermalexpansion.Insolator;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

// Courtesy of jchung01
@ZenExpansion("mods.thermalexpansion.Insolator")
@ModOnly("thermalexpansion")
@ZenRegister
public class UTInsolatorExpansion
{
    // list of fertilizers that will be added to TE's InsolatorManager
    // This isn't really a Set because ItemStack doesn't override equals(), but performance impact shouldn't be significant.
    public static Set<ItemStack> additionalFertilizers = UTConfig.MOD_INTEGRATION.THERMAL_EXPANSION.utTEInsolatorCustomMonoculture ? new ObjectOpenHashSet<>() : null;

    @ZenMethodStatic
    public static void addRecipeMonoculture(IItemStack primaryOutput, IItemStack primaryInput, IItemStack secondaryInput, int energy, @Optional IItemStack secondaryOutput, @Optional int secondaryChance, @Optional(valueLong = -1L) int water)
    {
        if (!UTConfig.MOD_INTEGRATION.THERMAL_EXPANSION.utTEInsolatorCustomMonoculture)
        {
            if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTInsolatorExpansion ::: ERROR - utTEInsolatorCustomMonoculture must be enabled to use this function!");
            return;
        }
        additionalFertilizers.add(CraftTweakerMC.getItemStack(secondaryInput));
        Insolator.addRecipe(primaryOutput, primaryInput, secondaryInput, energy, secondaryOutput, secondaryChance, water);
    }

    @ZenMethodStatic
    public static void addRecipeMonocultureSaplingInfuser(IItemStack primaryOutput, IItemStack primaryInput, IItemStack secondaryInput, int energy, @Optional IItemStack secondaryOutput, @Optional int secondaryChance, @Optional(valueLong = -1L) int water)
    {
        if (!UTConfig.MOD_INTEGRATION.THERMAL_EXPANSION.utTEInsolatorCustomMonoculture)
        {
            if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTInsolatorExpansion ::: ERROR - utTEInsolatorCustomMonoculture must be enabled to use this function!");
            return;
        }
        additionalFertilizers.add(CraftTweakerMC.getItemStack(secondaryInput));
        Insolator.addRecipeSaplingInfuser(primaryOutput, primaryInput, secondaryInput, energy, secondaryOutput, secondaryChance, water);
    }
}