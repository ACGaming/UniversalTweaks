package mod.acgaming.universaltweaks.util;

// https://stackoverflow.com/questions/1427422/cheap-algorithm-to-find-measure-of-angle-between-vectors/14675998#14675998
public class UTDiamondAtan2
{
    public static double atan2(double y, double x)
    {
        double angle;
        if (y == 0 && x >= 0) return 0;
        else angle = (y >= 0) ? (x >= 0 ? y / (x + y) : 1 - x / (-x + y)) : (x < 0 ? -2 + y / (x + y) : -1 + x / (x - y));
        return angle * 1.5707963;
    }
}