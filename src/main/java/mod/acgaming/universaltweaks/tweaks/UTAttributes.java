package mod.acgaming.universaltweaks.tweaks;

import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.tweaks.mixin.RangedAttributeAccessor;

import static net.minecraft.entity.SharedMonsterAttributes.*;

// Courtesy of Darkhax
public class UTAttributes
{
    public static final IAttribute[] ATTRIBUTES = new IAttribute[] {MAX_HEALTH, FOLLOW_RANGE, KNOCKBACK_RESISTANCE, MOVEMENT_SPEED, FLYING_SPEED, ATTACK_DAMAGE, ATTACK_SPEED, ARMOR, ARMOR_TOUGHNESS, LUCK};

    public static void utSetAttributes()
    {
        for (final IAttribute attribute : ATTRIBUTES)
        {
            if (attribute instanceof RangedAttribute)
            {
                ((RangedAttributeAccessor) attribute).setMinimumValue(Double.MIN_VALUE);
                ((RangedAttributeAccessor) attribute).setMaximumValue(Double.MAX_VALUE);
                UniversalTweaks.LOGGER.info("Successfully altered attribute " + attribute.getName());
            }
        }
    }
}