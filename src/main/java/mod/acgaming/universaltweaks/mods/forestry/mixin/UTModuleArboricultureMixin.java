package mod.acgaming.universaltweaks.mods.forestry.mixin;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import forestry.api.arboriculture.EnumGermlingType;
import forestry.arboriculture.ModuleArboriculture;
import forestry.arboriculture.blocks.BlockRegistryArboriculture;
import forestry.arboriculture.items.ItemRegistryArboriculture;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import mod.acgaming.universaltweaks.mods.forestry.UTVillagerArboristTrades;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = ModuleArboriculture.class, remap = false)
public class UTModuleArboricultureMixin
{
    @Inject(method = "doInit", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/common/registry/VillagerRegistry$VillagerCareer;addTrade(I[Lnet/minecraft/entity/passive/EntityVillager$ITradeList;)Lnet/minecraftforge/fml/common/registry/VillagerRegistry$VillagerCareer;", ordinal = 3, shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILSOFT)
    public void utForestryArboristTrades(CallbackInfo ci, ItemRegistryArboriculture items, BlockRegistryArboriculture blocks, VillagerRegistry.VillagerCareer arboristCareer)
    {
        for (String deal : UTConfig.MOD_INTEGRATION.FORESTRY.utFOArboristDeals)
        {
            String[] dealProperties = deal.split(";");
            int level = Integer.parseInt(dealProperties[0]);
            int emeralds_min = Integer.parseInt(dealProperties[1]);
            int emeralds_max = Integer.parseInt(dealProperties[2]);
            int germlings_min = Integer.parseInt(dealProperties[3]);
            int germlings_max = Integer.parseInt(dealProperties[4]);
            String type = dealProperties[5];
            int complexity_min = Integer.parseInt(dealProperties[6]);
            int complexity_max = Integer.parseInt(dealProperties[7]);
            arboristCareer.addTrade(level, new UTVillagerArboristTrades.GivePollenForEmeralds(new EntityVillager.PriceInfo(emeralds_min, emeralds_max), new EntityVillager.PriceInfo(germlings_min, germlings_max), type.equals("pollen") ? EnumGermlingType.POLLEN : EnumGermlingType.SAPLING, complexity_min, complexity_max));
            UniversalTweaks.LOGGER.info("[Forestry] Arborist Villager Trades - Adding a level {} trade for {} to {} {}(s) with a complexity between {} and {}, costing between {} to {} emeralds", level, germlings_min, germlings_max, type, complexity_min, complexity_max, emeralds_min, emeralds_max);
        }
    }
}