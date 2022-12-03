package mod.acgaming.universaltweaks.core;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import zone.rong.mixinbooter.IEarlyMixinLoader;

@IFMLLoadingPlugin.Name("UniversalTweaksCore")
@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
@IFMLLoadingPlugin.SortingIndex(Integer.MIN_VALUE)
public class UTLoadingPlugin implements IFMLLoadingPlugin, IEarlyMixinLoader
{
    public static final boolean isClient = FMLLaunchHandler.side().isClient();

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
                        return UTConfig.tweaks.utDisableAudioDebugToggle;
                    }
                case "mixins.bugfixes.blockoverlay.json":
                    return UTConfig.bugfixes.utBlockOverlayToggle;
                case "mixins.bugfixes.frustumculling.json":
                    return UTConfig.bugfixes.utFrustumCullingToggle;
                case "mixins.bugfixes.miningglitch.client.json":
                    return UTConfig.bugfixes.utMiningGlitchToggle;
                case "mixins.tweaks.autojump.json":
                    return UTConfig.tweaks.utAutoJumpToggle;
                case "mixins.tweaks.damagetilt.json":
                    return UTConfig.tweaks.utDamageTiltToggle;
                case "mixins.tweaks.infinitemusic.json":
                    return UTConfig.tweaks.utInfiniteMusicToggle;
                case "mixins.tweaks.recipebook.json":
                    return UTConfig.tweaks.utRecipeBookToggle;
                case "mixins.tweaks.resourcemanager.json":
                    return UTConfig.tweaks.utDisableAnimatedModelsToggle;
            }
        }
        switch (mixinConfig)
        {
            case "mixins.bugfixes.blockfire.json":
                return UTConfig.bugfixes.utBlockFireToggle;
            case "mixins.bugfixes.comparatortiming.json":
                return UTConfig.bugfixes.utComparatorTimingToggle;
            case "mixins.bugfixes.destroypacket.json":
                return UTConfig.bugfixes.utDestroyPacketToggle;
            case "mixins.bugfixes.dimensionchange.json":
                return UTConfig.bugfixes.utDimensionChangeToggle;
            case "mixins.bugfixes.entityaabb.json":
                return UTConfig.bugfixes.utEntityAABBToggle;
            case "mixins.bugfixes.entitysuffocation.json":
                return UTConfig.bugfixes.utEntitySuffocationToggle;
            case "mixins.bugfixes.entitytracker.json":
                return UTConfig.bugfixes.utEntityTrackerToggle;
            case "mixins.bugfixes.ladderflying.json":
                return UTConfig.bugfixes.utLadderFlyingToggle;
            case "mixins.bugfixes.miningglitch.server.json":
                return UTConfig.bugfixes.utMiningGlitchToggle;
            case "mixins.bugfixes.pistontile.json":
                return UTConfig.bugfixes.utPistonTileToggle;
            case "mixins.bugfixes.skeletonaim.json":
                return UTConfig.bugfixes.utSkeletonAimToggle;
            case "mixins.bugfixes.teloadorder.json":
                return UTConfig.bugfixes.utTELoadOrderToggle;
            case "mixins.tweaks.ai.json":
                return UTConfig.tweaks.utAIReplacementToggle;
            case "mixins.tweaks.attributes.json":
                return UTConfig.tweaks.utAttributesToggle;
            case "mixins.tweaks.bedobstruction.json":
                return UTConfig.tweaks.utBedObstructionToggle;
            case "mixins.tweaks.chunkgenlimit.json":
                return UTConfig.tweaks.utChunkGenLimitToggle;
            case "mixins.tweaks.collisiondamage.json":
                return UTConfig.tweaks.utCollisionDamageToggle;
            case "mixins.tweaks.dyeblending.json":
                return UTConfig.tweaks.utDyeBlendingToggle;
            case "mixins.tweaks.itementity.json":
                return UTConfig.tweaks.utCombineItemEntityToggle;
            case "mixins.tweaks.falldamage.json":
                return UTConfig.tweaks.utFallDamageToggle;
            case "mixins.tweaks.mobdespawn.json":
                return UTConfig.tweaks.utMobDespawnToggle;
            case "mixins.tweaks.plantables.json":
                return UTConfig.tweaks.utCactusSize != 3 || UTConfig.tweaks.utSugarCaneSize != 3;
            case "mixins.tweaks.prefixcheck.json":
                return UTConfig.tweaks.utPrefixCheckToggle;
        }
        return true;
    }
}