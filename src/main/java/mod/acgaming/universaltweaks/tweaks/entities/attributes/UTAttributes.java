package mod.acgaming.universaltweaks.tweaks.entities.attributes;

import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

import static net.minecraft.entity.SharedMonsterAttributes.*;

// Courtesy of Darkhax
public class UTAttributes
{
    public static final IAttribute[] ATTRIBUTES = new IAttribute[] {MAX_HEALTH, FOLLOW_RANGE, KNOCKBACK_RESISTANCE, MOVEMENT_SPEED, FLYING_SPEED, ATTACK_DAMAGE, ATTACK_SPEED, ARMOR, ARMOR_TOUGHNESS, LUCK};

    public static void utSetAttributes()
    {
        setValues(ATTRIBUTES[0], UTConfigTweaks.ENTITIES.ATTRIBUTES.utAttributeMaxHealthMin, UTConfigTweaks.ENTITIES.ATTRIBUTES.utAttributeMaxHealthMax);
        setValues(ATTRIBUTES[1], UTConfigTweaks.ENTITIES.ATTRIBUTES.utAttributeFollowRangeMin, UTConfigTweaks.ENTITIES.ATTRIBUTES.utAttributeFollowRangeMax);
        setValues(ATTRIBUTES[2], UTConfigTweaks.ENTITIES.ATTRIBUTES.utAttributeKnockbackResistanceMin, UTConfigTweaks.ENTITIES.ATTRIBUTES.utAttributeKnockbackResistanceMax);
        setValues(ATTRIBUTES[3], UTConfigTweaks.ENTITIES.ATTRIBUTES.utAttributeMovementSpeedMin, UTConfigTweaks.ENTITIES.ATTRIBUTES.utAttributeMovementSpeedMax);
        setValues(ATTRIBUTES[4], UTConfigTweaks.ENTITIES.ATTRIBUTES.utAttributeFlyingSpeedMin, UTConfigTweaks.ENTITIES.ATTRIBUTES.utAttributeFlyingSpeedMax);
        setValues(ATTRIBUTES[5], UTConfigTweaks.ENTITIES.ATTRIBUTES.utAttributeAttackDamageMin, UTConfigTweaks.ENTITIES.ATTRIBUTES.utAttributeAttackDamageMax);
        setValues(ATTRIBUTES[6], UTConfigTweaks.ENTITIES.ATTRIBUTES.utAttributeAttackSpeedMin, UTConfigTweaks.ENTITIES.ATTRIBUTES.utAttributeAttackSpeedMax);
        setValues(ATTRIBUTES[7], UTConfigTweaks.ENTITIES.ATTRIBUTES.utAttributeArmorMin, UTConfigTweaks.ENTITIES.ATTRIBUTES.utAttributeArmorMax);
        setValues(ATTRIBUTES[8], UTConfigTweaks.ENTITIES.ATTRIBUTES.utAttributeArmorToughnessMin, UTConfigTweaks.ENTITIES.ATTRIBUTES.utAttributeArmorToughnessMax);
        setValues(ATTRIBUTES[9], UTConfigTweaks.ENTITIES.ATTRIBUTES.utAttributeLuckMin, UTConfigTweaks.ENTITIES.ATTRIBUTES.utAttributeLuckMax);
    }

    public static void setValues(IAttribute attribute, double minValue, double maxValue)
    {
        try
        {
            if (attribute instanceof RangedAttribute)
            {
                ((RangedAttribute) attribute).minimumValue = minValue;
                ((RangedAttribute) attribute).maximumValue = maxValue;
                UniversalTweaks.LOGGER.info("UTAttributes ::: Successfully altered attribute {} with {} as minimum and {} as maximum", attribute.getName(), minValue, maxValue);
            }
        }
        catch (Exception e)
        {
            UniversalTweaks.LOGGER.error("UTAttributes ::: Couldn't alter attribute {}", attribute.getName());
        }
    }
}