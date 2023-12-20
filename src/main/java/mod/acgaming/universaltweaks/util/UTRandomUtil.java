package mod.acgaming.universaltweaks.util;

import java.util.Random;

// Courtesy of UeberallGebannt
public class UTRandomUtil
{
    public static final Random RANDOM = new Random();

    /**
     * Returns true with a certain chance
     *
     * @param chance The chance to return true
     * @param random The random instance to be used
     * @return true with a certain chance or false
     */
    public static boolean chance(double chance, Random random)
    {
        double val = random.nextDouble();
        return val <= chance;
    }

    /**
     * Returns true with a certain chance
     *
     * @param chance The chance to return true
     * @return true with a certain chance or false
     */
    public static boolean chance(double chance)
    {
        return chance(chance, RANDOM);
    }
}