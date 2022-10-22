package mod.acgaming.hkntweaks.core;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
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
        if (HkNTweaksConfig.bugfixes.hknLocaleToggle && Locale.getDefault().getLanguage().equals("tr"))
        {
            HkNTweaks.LOGGER.info("The locale is Turkish, which is unfortunately not supported by some mods. Changing to English...");
            Locale.setDefault(Locale.ENGLISH);
        }
    }

    @Override
    public String[] getASMTransformerClass()
    {
        return new String[0];
    }

    @Override
    public String getModContainerClass()
    {
        return null;
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
        return isClient ? Arrays.asList(
            "mixins.bugfixes.blockoverlay.json",
            "mixins.bugfixes.dimensionchange.json",
            "mixins.bugfixes.entityaabb.json",
            "mixins.bugfixes.ladderflying.json",
            "mixins.bugfixes.pistontile.json",
            "mixins.bugfixes.skeletonaim.json",
            "mixins.bugfixes.teloadorder.json",
            "mixins.tweaks.attributes.json",
            "mixins.tweaks.autojump.json",
            "mixins.tweaks.bedobstruction.json",
            "mixins.tweaks.falldamage.json",
            "mixins.tweaks.mobdespawn.json") :
            Arrays.asList(
                "mixins.bugfixes.dimensionchange.json",
                "mixins.bugfixes.entityaabb.json",
                "mixins.bugfixes.ladderflying.json",
                "mixins.bugfixes.pistontile.json",
                "mixins.bugfixes.skeletonaim.json",
                "mixins.bugfixes.teloadorder.json",
                "mixins.tweaks.attributes.json",
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
            switch (mixinConfig)
            {
                case "mixins.bugfixes.blockoverlay.json":
                    return HkNTweaksConfig.bugfixes.hknBlockOverlayToggle;
                case "mixins.tweaks.autojump.json":
                    return HkNTweaksConfig.tweaks.hknAutoJumpToggle;
            }
        }
        switch (mixinConfig)
        {
            case "mixins.bugfixes.dimensionchange.json":
                return HkNTweaksConfig.bugfixes.hknDimensionChangeToggle;
            case "mixins.bugfixes.entityaabb.json":
                return HkNTweaksConfig.bugfixes.hknEntityAABBToggle;
            case "mixins.bugfixes.ladderflying.json":
                return HkNTweaksConfig.bugfixes.hknLadderFlyingToggle;
            case "mixins.bugfixes.pistontile.json":
                return HkNTweaksConfig.bugfixes.hknPistonTileToggle;
            case "mixins.bugfixes.skeletonaim.json":
                return HkNTweaksConfig.bugfixes.hknSkeletonAimToggle;
            case "mixins.bugfixes.teloadorder.json":
                return HkNTweaksConfig.bugfixes.hknTELoadOrderToggle;
            case "mixins.tweaks.attributes.json":
                return HkNTweaksConfig.tweaks.hknAttributesToggle;
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