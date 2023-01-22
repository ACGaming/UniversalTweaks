package mod.acgaming.universaltweaks.mods.forestry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

import forestry.api.arboriculture.EnumGermlingType;
import forestry.api.arboriculture.TreeManager;
import forestry.api.genetics.*;

public class UTVillagerArboristTrades
{
    public static class GivePollenForEmeralds implements EntityVillager.ITradeList
    {
        @Nullable
        public final EntityVillager.PriceInfo emeraldPriceInfo;
        @Nullable
        public final EntityVillager.PriceInfo sellInfo;
        private final EnumGermlingType type;
        private final int minComplexity;
        private final int maxComplexity;

        public GivePollenForEmeralds(@Nullable EntityVillager.PriceInfo emeraldPriceInfo, @Nullable EntityVillager.PriceInfo sellInfo, EnumGermlingType type, int minComplexity, int maxComplexity)
        {
            this.emeraldPriceInfo = emeraldPriceInfo;
            this.sellInfo = sellInfo;
            this.type = type;
            this.minComplexity = minComplexity;
            this.maxComplexity = maxComplexity;
        }

        public void addMerchantRecipe(IMerchant iMerchant, MerchantRecipeList recipeList, Random random)
        {
            int i = 1;
            int j = 1;
            if (this.sellInfo != null) i = this.sellInfo.getPrice(random);
            if (this.emeraldPriceInfo != null) j = this.emeraldPriceInfo.getPrice(random);
            IChromosomeType treeSpeciesType = TreeManager.treeRoot.getSpeciesChromosomeType();
            Collection<IAllele> registeredSpecies = AlleleManager.alleleRegistry.getRegisteredAlleles(treeSpeciesType);
            List<IAlleleSpecies> potentialSpecies = new ArrayList<>();
            for (IAllele allele : registeredSpecies)
            {
                if (allele instanceof IAlleleSpecies)
                {
                    IAlleleSpecies species = (IAlleleSpecies) allele;
                    if (species.getComplexity() >= this.minComplexity && species.getComplexity() <= this.maxComplexity) potentialSpecies.add(species);
                }
            }
            if (!potentialSpecies.isEmpty())
            {
                IAlleleSpecies chosenSpecies = potentialSpecies.get(random.nextInt(potentialSpecies.size()));
                IAllele[] template = TreeManager.treeRoot.getTemplate(chosenSpecies);
                IIndividual individual = TreeManager.treeRoot.templateAsIndividual(template);
                ItemStack sellStack = TreeManager.treeRoot.getMemberStack(individual, this.type);
                sellStack.setCount(i);
                ItemStack emeralds = new ItemStack(Items.EMERALD, j);
                recipeList.add(new MerchantRecipe(emeralds, sellStack));
            }
        }
    }
}