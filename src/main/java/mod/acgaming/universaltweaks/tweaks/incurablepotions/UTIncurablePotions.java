package mod.acgaming.universaltweaks.tweaks.incurablepotions;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

public class UTIncurablePotions
{
    public static List<String> potionList = new ArrayList<>();

    public static void initPotionList()
    {
        potionList.clear();
        try
        {
            for (String entry : UTConfig.TWEAKS_MISC.INCURABLE_POTIONS.utIncurablePotionsList)
            {
                ResourceLocation resLoc = new ResourceLocation(entry);
                if (ForgeRegistries.POTIONS.containsKey(resLoc)) potionList.add(ForgeRegistries.POTIONS.getValue(resLoc).getRegistryName().toString());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        UniversalTweaks.LOGGER.info("Incurable Potions list initialized");
    }
}