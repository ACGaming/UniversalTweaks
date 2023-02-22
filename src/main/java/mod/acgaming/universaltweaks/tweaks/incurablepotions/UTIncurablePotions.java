package mod.acgaming.universaltweaks.tweaks.incurablepotions;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

public class UTIncurablePotions
{
    public static List<String> incurablePotions = new ArrayList<>();

    public static void initPotionList()
    {
        incurablePotions.clear();
        try
        {
            for (String entry : UTConfig.TWEAKS_MISC.utIncurablePotions)
            {
                ResourceLocation resLoc = new ResourceLocation(entry);
                if (ForgeRegistries.POTIONS.containsKey(resLoc)) incurablePotions.add(ForgeRegistries.POTIONS.getValue(resLoc).getRegistryName().toString());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        UniversalTweaks.LOGGER.info("Incurable Potions list initialized");
    }
}