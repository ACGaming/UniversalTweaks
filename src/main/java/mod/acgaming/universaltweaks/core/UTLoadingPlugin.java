package mod.acgaming.universaltweaks.core;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.Side;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import zone.rong.mixinbooter.IEarlyMixinLoader;

@IFMLLoadingPlugin.Name("UniversalTweaksCore")
@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
@IFMLLoadingPlugin.SortingIndex(Integer.MIN_VALUE)
public class UTLoadingPlugin implements IFMLLoadingPlugin, IEarlyMixinLoader
{
    public static final boolean isClient = FMLLaunchHandler.side() == Side.CLIENT;

    static
    {
        if (UTConfig.bugfixes.utLocaleToggle && Locale.getDefault().getLanguage().equals("tr"))
        {
            UniversalTweaks.LOGGER.info("The locale is Turkish, which is unfortunately not supported by some mods. Changing to English...");
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
            "mixins.bugfixes.blockfire.json",
            "mixins.bugfixes.blockoverlay.json",
            "mixins.bugfixes.comparatortiming.json",
            "mixins.bugfixes.destroypacket.json",
            "mixins.bugfixes.dimensionchange.json",
            "mixins.bugfixes.entityaabb.json",
            "mixins.bugfixes.entitysuffocation.json",
            "mixins.bugfixes.entitytracker.json",
            "mixins.bugfixes.frustumculling.json",
            "mixins.bugfixes.ladderflying.json",
            "mixins.bugfixes.miningglitch.client.json",
            "mixins.bugfixes.miningglitch.server.json",
            "mixins.bugfixes.pistontile.json",
            "mixins.bugfixes.skeletonaim.json",
            "mixins.bugfixes.teloadorder.json",
            "mixins.tweaks.ai.json",
            "mixins.tweaks.attributes.json",
            "mixins.tweaks.audioreload.json",
            "mixins.tweaks.autojump.json",
            "mixins.tweaks.bedobstruction.json",
            "mixins.tweaks.chunkgenlimit.json",
            "mixins.tweaks.collisiondamage.json",
            "mixins.tweaks.damagetilt.json",
            "mixins.tweaks.dyeblending.json",
            "mixins.tweaks.infinitemusic.json",
            "mixins.tweaks.itementity.json",
            "mixins.tweaks.falldamage.json",
            "mixins.tweaks.mobdespawn.json",
            "mixins.tweaks.plantables.json",
            "mixins.tweaks.prefixcheck.json",
            "mixins.tweaks.recipebook.json",
            "mixins.tweaks.resourcemanager.json") :
            Arrays.asList(
                "mixins.bugfixes.blockfire.json",
                "mixins.bugfixes.comparatortiming.json",
                "mixins.bugfixes.destroypacket.json",
                "mixins.bugfixes.dimensionchange.json",
                "mixins.bugfixes.entityaabb.json",
                "mixins.bugfixes.entitysuffocation.json",
                "mixins.bugfixes.entitytracker.json",
                "mixins.bugfixes.ladderflying.json",
                "mixins.bugfixes.miningglitch.server.json",
                "mixins.bugfixes.pistontile.json",
                "mixins.bugfixes.skeletonaim.json",
                "mixins.bugfixes.teloadorder.json",
                "mixins.tweaks.ai.json",
                "mixins.tweaks.attributes.json",
                "mixins.tweaks.bedobstruction.json",
                "mixins.tweaks.chunkgenlimit.json",
                "mixins.tweaks.collisiondamage.json",
                "mixins.tweaks.dyeblending.json",
                "mixins.tweaks.itementity.json",
                "mixins.tweaks.falldamage.json",
                "mixins.tweaks.mobdespawn.json",
                "mixins.tweaks.plantables.json",
                "mixins.tweaks.prefixcheck.json"
            );
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig)
    {
        if (isClient)
        {
            switch (mixinConfig)
            {
                case "mixins.tweaks.audioreload.json":
                    try
                    {
                        Class.forName("net.darkhax.surge.core.SurgeLoadingPlugin");
                        return false;
                    }
                    catch (ClassNotFoundException e)
                    {
                        return true;
                    }
                case "mixins.bugfixes.blockoverlay.json":
                case "mixins.bugfixes.frustumculling.json":
                case "mixins.bugfixes.miningglitch.client.json":
                case "mixins.tweaks.autojump.json":
                case "mixins.tweaks.damagetilt.json":
                case "mixins.tweaks.infinitemusic.json":
                case "mixins.tweaks.recipebook.json":
                case "mixins.tweaks.resourcemanager.json":
                    return true;
            }
        }
        switch (mixinConfig)
        {
            case "mixins.bugfixes.blockfire.json":
            case "mixins.bugfixes.comparatortiming.json":
            case "mixins.bugfixes.destroypacket.json":
            case "mixins.bugfixes.dimensionchange.json":
            case "mixins.bugfixes.entityaabb.json":
            case "mixins.bugfixes.entitysuffocation.json":
            case "mixins.bugfixes.entitytracker.json":
            case "mixins.bugfixes.ladderflying.json":
            case "mixins.bugfixes.miningglitch.server.json":
            case "mixins.bugfixes.pistontile.json":
            case "mixins.bugfixes.skeletonaim.json":
            case "mixins.bugfixes.teloadorder.json":
            case "mixins.tweaks.ai.json":
            case "mixins.tweaks.attributes.json":
            case "mixins.tweaks.bedobstruction.json":
            case "mixins.tweaks.chunkgenlimit.json":
            case "mixins.tweaks.collisiondamage.json":
            case "mixins.tweaks.dyeblending.json":
            case "mixins.tweaks.itementity.json":
            case "mixins.tweaks.falldamage.json":
            case "mixins.tweaks.mobdespawn.json":
            case "mixins.tweaks.plantables.json":
            case "mixins.tweaks.prefixcheck.json":
                return true;
        }
        return true;
    }
}