package mod.acgaming.universaltweaks.mods.biomesoplenty.biometweaker;

import biomesoplenty.common.biome.overworld.BOPOverworldBiome;
import biomesoplenty.common.world.TerrainSettings;
import me.superckl.api.biometweaker.property.Property;
import mod.acgaming.universaltweaks.UniversalTweaks;

public class UTBTBOPTerrainProperty extends Property<Float>
{
    private final String field;

    public UTBTBOPTerrainProperty(String field)
    {
        super(Float.class);
        this.field = field;
    }

    @Override
    public void set(Object obj, Float val)
    {
        try
        {
            TerrainSettings.class.getField(this.field).setDouble(((BOPOverworldBiome) obj).terrainSettings, val);
        }
        catch (Exception e)
        {
            UniversalTweaks.LOGGER.error("BiomeTweaker - Failed to set TerrainSettings property {} for biome {}", this.field, obj);
        }
    }

    @Override
    public Float get(Object obj)
    {
        try
        {
            return (float) TerrainSettings.class.getField(this.field).getDouble(((BOPOverworldBiome) obj).terrainSettings);
        }
        catch (Exception e)
        {
            UniversalTweaks.LOGGER.error("BiomeTweaker - Failed to get TerrainSettings property {} for biome {}", this.field, obj);
            return 0F;
        }
    }

    @Override
    public boolean isReadable()
    {
        return true;
    }

    @Override
    public boolean isSettable()
    {
        return true;
    }

    @Override
    public Class<?> getTargetClass()
    {
        return BOPOverworldBiome.class;
    }

    @Override
    public void copy(Object from, Object to)
    {
        if (from instanceof BOPOverworldBiome && to instanceof BOPOverworldBiome) super.copy(from, to);
    }
}
