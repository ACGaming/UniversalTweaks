package mod.acgaming.universaltweaks.mods.thermalexpansion.modtweaker;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

import com.blamejared.compat.thermalexpansion.Insolator;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

// Courtesy of jchung01
@ZenExpansion("mods.thermalexpansion.Insolator")
@ModOnly("thermalexpansion")
@ZenRegister
public class UTInsolatorExpansion
{
    // Set of fertilizers that will be added to TE's InsolatorManager
    public static Set<IItemStack> additionalFertilizers = UTConfigMods.THERMAL_EXPANSION.utTEInsolatorCustomMonoculture ? new ObjectOpenHashSet<>() : null;

    @ZenMethodStatic
    public static void addRecipeMonoculture(IItemStack primaryOutput, IItemStack primaryInput, IItemStack secondaryInput, int energy, @Optional IItemStack secondaryOutput, @Optional int secondaryChance, @Optional(valueLong = -1L) int water)
    {
        if (!UTConfigMods.THERMAL_EXPANSION.utTEInsolatorCustomMonoculture)
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTInsolatorExpansion ::: ERROR - utTEInsolatorCustomMonoculture must be enabled to use this function!");
            return;
        }
        additionalFertilizers.add(secondaryInput);
        Insolator.addRecipe(primaryOutput, primaryInput, secondaryInput, energy, secondaryOutput, secondaryChance, water);
    }

    @ZenMethodStatic
    public static void addRecipeMonocultureSaplingInfuser(IItemStack primaryOutput, IItemStack primaryInput, IItemStack secondaryInput, int energy, @Optional IItemStack secondaryOutput, @Optional int secondaryChance, @Optional(valueLong = -1L) int water)
    {
        if (!UTConfigMods.THERMAL_EXPANSION.utTEInsolatorCustomMonoculture)
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTInsolatorExpansion ::: ERROR - utTEInsolatorCustomMonoculture must be enabled to use this function!");
            return;
        }
        additionalFertilizers.add(secondaryInput);
        Insolator.addRecipeSaplingInfuser(primaryOutput, primaryInput, secondaryInput, energy, secondaryOutput, secondaryChance, water);
    }

    public static List<ItemStack> getFertilizers()
    {
        List<ItemStack> fertilizers = Collections.emptyList();
        if (additionalFertilizers != null && Loader.isModLoaded("crafttweaker"))
        {
            fertilizers = additionalFertilizers.stream().map(CraftTweakerMC::getItemStack).collect(Collectors.toList());
            additionalFertilizers = null;
        }
        return fertilizers;
    }
}