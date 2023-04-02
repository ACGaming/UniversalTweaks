package mod.acgaming.universaltweaks.tweaks.items.dragonbreath;

import net.minecraft.init.Items;

// Courtesy of Turkey9002
public class UTLeftoverDragonBreath
{
    public static void postInit()
    {
        Items.DRAGON_BREATH.setContainerItem(null);
    }
}