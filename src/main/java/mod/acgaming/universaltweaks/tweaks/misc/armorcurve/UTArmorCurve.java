package mod.acgaming.universaltweaks.tweaks.misc.armorcurve;

import com.udojava.evalex.Expression;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of Jackiecrazy
public class UTArmorCurve
{
    public static Expression armor, toughness, enchants, degrade;

    public static void initExpressions()
    {
        armor = new Expression(UTConfigTweaks.MISC.ARMOR_CURVE.utArmorCurveArmor);
        toughness = new Expression(UTConfigTweaks.MISC.ARMOR_CURVE.utArmorCurveArmorToughness);
        enchants = new Expression(UTConfigTweaks.MISC.ARMOR_CURVE.utArmorCurveEnchantments);
        degrade = new Expression(UTConfigTweaks.MISC.ARMOR_CURVE.utArmorCurveDegradation);

        try
        {
            armor.with("damage", "1").and("armor", "10").and("toughness", "0").eval();
        }
        catch (Expression.ExpressionException e)
        {
            armor = new Expression("1");
            UniversalTweaks.LOGGER.fatal("UTArmorCurve ::: Invalid formula " + armor);
        }

        try
        {
            toughness.with("damage", "1").and("armor", "10").and("toughness", "0").eval();
        }
        catch (Expression.ExpressionException e)
        {
            toughness = new Expression("1");
            UniversalTweaks.LOGGER.fatal("UTArmorCurve ::: Invalid formula " + toughness);
        }

        try
        {
            enchants.with("damage", "1").and("enchant", "2").eval();
        }
        catch (Expression.ExpressionException e)
        {
            enchants = new Expression("1");
            UniversalTweaks.LOGGER.fatal("UTArmorCurve ::: Invalid formula " + enchants);
        }

        try
        {
            degrade.with("remaining", "100").and("max", "100").eval();
        }
        catch (Expression.ExpressionException e)
        {
            degrade = new Expression("1");
            UniversalTweaks.LOGGER.fatal("UTArmorCurve ::: Invalid formula " + degrade);
        }

        UniversalTweaks.LOGGER.info("Armor Curve expressions initialized");
    }
}