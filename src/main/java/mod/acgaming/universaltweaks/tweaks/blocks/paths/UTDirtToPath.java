package mod.acgaming.universaltweaks.tweaks.blocks.paths;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

import net.minecraft.block.BlockDirt;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class UTDirtToPath
{
    @SubscribeEvent
    public static void utOnDirtInteract(PlayerInteractEvent.RightClickBlock event)
    {
        ItemStack stack = event.getItemStack();
        if(!stack.isEmpty() && stack.getItem() instanceof ItemSpade || stack.getItem().getToolClasses(stack).contains("shovel"))
        {
            EntityPlayer player = event.getEntityPlayer();
            World world = event.getWorld();
            BlockPos pos = event.getPos();
            IBlockState state = world.getBlockState(event.getPos());
            //Reverting grass path to dirt
            if(state.getBlock() == Blocks.GRASS_PATH && player.isSneaking())
            {
                world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                if(!world.isRemote)
                {
                    world.setBlockState(pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT).withProperty(BlockDirt.SNOWY, false), 11);
                    stack.damageItem(1, player);
                }
                event.setCancellationResult(EnumActionResult.SUCCESS);
                event.setCanceled(true);
            }
            //Converting Dirt into Grass Paths
            else if(state.getBlock() == Blocks.DIRT && state.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT)
            {
                if(event.getFace() != EnumFacing.DOWN && (world.getBlockState(pos).getMaterial() == Material.AIR || UTConfigTweaks.BLOCKS.utLenientPathsToggle))
                {
                    world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    if(!world.isRemote)
                    {
                        world.setBlockState(pos, Blocks.GRASS_PATH.getDefaultState(), 11);
                        stack.damageItem(1, player);
                    }
                    event.setCancellationResult(EnumActionResult.SUCCESS);
                    event.setCanceled(true);
                }
            }
        }
    }
}
