package mod.acgaming.universaltweaks.tweaks.ai;

// https://stackoverflow.com/questions/1427422/cheap-algorithm-to-find-measure-of-angle-between-vectors/14675998#14675998
public class UTDiamondAtan2
{
    public static final double PI_2 = 1.5707963;

    public static double atan2(double y, double x)
    {
        double angle;
        if (y == 0 && x >= 0) return 0;
        else if (y >= 0)
        {
            if (x >= 0) angle = y / (x + y);
            else angle = 1 - x / (-x + y);
        }
        else
        {
            if (x < 0) angle = -2 + y / (x + y);
            else angle = -1 + x / (x - y);
        }
        return angle * PI_2;
    }
}