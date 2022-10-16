package mod.acgaming.hkntweaks.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.Side;

import mod.acgaming.hkntweaks.HkNTweaks;
import mod.acgaming.hkntweaks.config.HkNTweaksConfig;
import zone.rong.mixinbooter.IEarlyMixinLoader;

@IFMLLoadingPlugin.Name("HkNTweaksCore")
@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
@IFMLLoadingPlugin.SortingIndex(Integer.MIN_VALUE)
public class HkNTweaksLoadingPlugin implements IFMLLoadingPlugin, IEarlyMixinLoader
{
    public static final boolean isClient = FMLLaunchHandler.side() == Side.CLIENT;

    static
    {
        HkNTweaks.LOGGER.info("HkN Tweaks Core initializing...");
    }

    @Override
    public String[] getASMTransformerClass()
    {
        return new String[0];
    }

    @Override
    public String getModContainerClass()
    {
        return HkNTweaksContainer.class.getName();
    }

    @Nullable
    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data)
    {

    }

    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }

    @Override
    public List<String> getMixinConfigs()
    {
        return isClient ? Collections.singletonList(
            "mixins.tweaks.autojump.json"
        ) : Arrays.asList(
            "mixins.bugfixes.json",
            "mixins.tweaks.bedobstruction.json",
            "mixins.tweaks.falldamage.json",
            "mixins.tweaks.mobdespawn.json"
        );
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig)
    {
        if (isClient)
        {
            if ("mixins.tweaks.autojump.json".equals(mixinConfig))
            {
                return HkNTweaksConfig.tweaks.hknAutoJumpToggle;
            }
        }
        switch (mixinConfig)
        {
            case "mixins.bugfixes.json":
                return true;
            case "mixins.tweaks.bedobstruction.json":
                return HkNTweaksConfig.tweaks.hknBedObstructionToggle;
            case "mixins.tweaks.falldamage.json":
                return HkNTweaksConfig.tweaks.hknFallDamageToggle;
            case "mixins.tweaks.mobdespawn.json":
                return HkNTweaksConfig.tweaks.hknMobDespawnToggle;
        }
        return true;
    }
}