package mod.acgaming.universaltweaks.tweaks;

import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

import static net.minecraft.entity.SharedMonsterAttributes.*;

// Courtesy of Darkhax
public class UTAttributes
{
    public static final IAttribute[] ATTRIBUTES = new IAttribute[] {MAX_HEALTH, FOLLOW_RANGE, KNOCKBACK_RESISTANCE, MOVEMENT_SPEED, FLYING_SPEED, ATTACK_DAMAGE, ATTACK_SPEED, ARMOR, ARMOR_TOUGHNESS, LUCK};

    public static void utSetAttributes()
    {
        setValues(ATTRIBUTES[0], UTConfig.TWEAKS_ENTITIES.ATTRIBUTES.utAttributeMaxHealthMin, UTConfig.TWEAKS_ENTITIES.ATTRIBUTES.utAttributeMaxHealthMax);
        setValues(ATTRIBUTES[1], UTConfig.TWEAKS_ENTITIES.ATTRIBUTES.utAttributeFollowRangeMin, UTConfig.TWEAKS_ENTITIES.ATTRIBUTES.utAttributeFollowRangeMax);
        setValues(ATTRIBUTES[2], UTConfig.TWEAKS_ENTITIES.ATTRIBUTES.utAttributeKnockbackResistanceMin, UTConfig.TWEAKS_ENTITIES.ATTRIBUTES.utAttributeKnockbackResistanceMax);
        setValues(ATTRIBUTES[3], UTConfig.TWEAKS_ENTITIES.ATTRIBUTES.utAttributeMovementSpeedMin, UTConfig.TWEAKS_ENTITIES.ATTRIBUTES.utAttributeMovementSpeedMax);
        setValues(ATTRIBUTES[4], UTConfig.TWEAKS_ENTITIES.ATTRIBUTES.utAttributeFlyingSpeedMin, UTConfig.TWEAKS_ENTITIES.ATTRIBUTES.utAttributeFlyingSpeedMax);
        setValues(ATTRIBUTES[5], UTConfig.TWEAKS_ENTITIES.ATTRIBUTES.utAttributeAttackDamageMin, UTConfig.TWEAKS_ENTITIES.ATTRIBUTES.utAttributeAttackDamageMax);
        setValues(ATTRIBUTES[6], UTConfig.TWEAKS_ENTITIES.ATTRIBUTES.utAttributeAttackSpeedMin, UTConfig.TWEAKS_ENTITIES.ATTRIBUTES.utAttributeAttackSpeedMax);
        setValues(ATTRIBUTES[7], UTConfig.TWEAKS_ENTITIES.ATTRIBUTES.utAttributeArmorMin, UTConfig.TWEAKS_ENTITIES.ATTRIBUTES.utAttributeArmorMax);
        setValues(ATTRIBUTES[8], UTConfig.TWEAKS_ENTITIES.ATTRIBUTES.utAttributeArmorToughnessMin, UTConfig.TWEAKS_ENTITIES.ATTRIBUTES.utAttributeArmorToughnessMax);
        setValues(ATTRIBUTES[9], UTConfig.TWEAKS_ENTITIES.ATTRIBUTES.utAttributeLuckMin, UTConfig.TWEAKS_ENTITIES.ATTRIBUTES.utAttributeLuckMax);
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