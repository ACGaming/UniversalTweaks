package mod.acgaming.universaltweaks.tweaks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTFiniteWater
{
    @SubscribeEvent
    public static void utFiniteWater(BlockEvent.CreateFluidSourceEvent event)
    {
        if (!UTConfig.TWEAKS_BLOCKS.utFiniteWaterToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTFiniteWater ::: Create fluid source event");
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        Biome biome = world.getBiomeForCoordsBody(pos);
        if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OCEAN) || BiomeDictionary.hasType(biome, BiomeDictionary.Type.RIVER)) return;
        IBlockState state = event.getState();
        if (state.getMaterial() == Material.WATER) event.setResult(Event.Result.DENY);
    }
}