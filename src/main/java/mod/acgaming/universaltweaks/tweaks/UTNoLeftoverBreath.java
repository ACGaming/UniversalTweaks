package mod.acgaming.universaltweaks.tweaks;

import net.minecraft.init.Items;

public class UTNoLeftoverBreath
{
    public static void postInit()
    {
        Items.DRAGON_BREATH.setContainerItem(null);
    }
}
