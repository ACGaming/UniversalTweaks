package mod.acgaming.universaltweaks.tweaks.breakablebedrock;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import mod.acgaming.universaltweaks.config.UTConfig;

public class UTBlockBedrock extends Block
{
    public UTBlockBedrock()
    {
        super(Material.ROCK);
        this.setHardness(UTConfig.TWEAKS_BLOCKS.BREAKABLE_BEDROCK.utBreakableBedrockHardness);
        this.setResistance(UTConfig.TWEAKS_BLOCKS.BREAKABLE_BEDROCK.utBreakableBedrockResistance);
        this.setHarvestLevel("pickaxe", UTConfig.TWEAKS_BLOCKS.BREAKABLE_BEDROCK.utBreakableBedrockHarvestLevel);
        this.setTranslationKey("bedrock");
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return MapColor.GRAY;
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(Blocks.BEDROCK);
    }
}