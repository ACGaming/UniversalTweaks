package mod.acgaming.universaltweaks.tweaks;

import net.minecraft.init.Items;

// Courtesy of Turkey9002
public class UTNoLeftoverBreath
{
    public static void postInit()
    {
        Items.DRAGON_BREATH.setContainerItem(null);
    }
}