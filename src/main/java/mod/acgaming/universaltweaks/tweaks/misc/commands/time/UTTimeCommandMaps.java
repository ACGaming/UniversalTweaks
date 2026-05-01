package mod.acgaming.universaltweaks.tweaks.misc.commands.time;

import java.util.Map;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2LongOpenHashMap;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

public class UTTimeCommandMaps
{
    public static final Map<Integer, Long> DIM_DAY_LENGTH_MAP = new Int2LongOpenHashMap();
    public static final Map<Integer, Integer> DIM_DAY_ADD_MAP = new Int2IntOpenHashMap();
    public static final Map<Integer, Integer> DIM_NIGHT_ADD_MAP = new Int2IntOpenHashMap();

    public static void initDimensions()
    {
        DIM_DAY_LENGTH_MAP.clear();
        DIM_DAY_ADD_MAP.clear();
        DIM_NIGHT_ADD_MAP.clear();
        try
        {
            for (String config : UTConfigTweaks.MISC.TIME_COMMAND.utTimeCommandDimList)
            {
                String[] configParts = config.split(";");
                DIM_DAY_LENGTH_MAP.put(Integer.parseInt(configParts[0]), Long.parseLong(configParts[1]));
                DIM_DAY_ADD_MAP.put(Integer.parseInt(configParts[0]), Integer.parseInt(configParts[2]));
                DIM_NIGHT_ADD_MAP.put(Integer.parseInt(configParts[0]), Integer.parseInt(configParts[3]));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        UniversalTweaks.LOGGER.info("Time command dimension maps initialized");
    }
}
