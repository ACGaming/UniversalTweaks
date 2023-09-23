package mod.acgaming.universaltweaks.mods.roost.contenttweaker.mixin;

import net.minecraft.creativetab.CreativeTabs;

import com.teamacronymcoders.base.BaseModFoundation;
import com.teamacronymcoders.contenttweaker.ContentTweaker;
import crafttweaker.CraftTweakerAPI;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import mod.acgaming.universaltweaks.mods.roost.contenttweaker.UTChickenRegistration;
import org.spongepowered.asm.mixin.Mixin;

// Courtesy of jchung01
@Mixin(value = ContentTweaker.class, remap = false)
public abstract class UTContentTweakerMixin extends BaseModFoundation<ContentTweaker>
{
    public UTContentTweakerMixin(String modid, String name, String version, CreativeTabs creativeTab, boolean optionalSystems)
    {
        super(modid, name, version, creativeTab, optionalSystems);
    }

    @Override
    public void finalizeOptionalSystems()
    {
        super.finalizeOptionalSystems();
        if (!UTConfigMods.ROOST.utRoostEarlyRegisterCTChickens) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTContentTweakerMixin ::: Register chickens (pre-init)");

        ContentTweaker.scriptsSuccessful = CraftTweakerAPI.tweaker.loadScript(false, "finalize_contenttweaker");
        UTChickenRegistration.utRegisterChickens();
    }
}
