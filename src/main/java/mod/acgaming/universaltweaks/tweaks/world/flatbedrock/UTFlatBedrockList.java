package mod.acgaming.universaltweaks.tweaks.world.flatbedrock;

import java.util.HashSet;
import java.util.Set;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

public class UTFlatBedrockList
{
    public static final Set<Integer> WHITELISTED_HEIGHTS = new HashSet<>();

    public static void initHeightWhitelist()
    {
        WHITELISTED_HEIGHTS.clear();
        for (int height : UTConfigTweaks.WORLD.FLAT_BEDROCK.utFlatBedrockHeightWhitelist) WHITELISTED_HEIGHTS.add(height);
        UniversalTweaks.LOGGER.info("Flat Bedrock height whitelist initialized");
    }
}
