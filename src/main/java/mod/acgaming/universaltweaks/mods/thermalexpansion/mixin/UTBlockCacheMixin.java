package mod.acgaming.universaltweaks.mods.thermalexpansion.mixin;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import cofh.core.util.helpers.ItemHelper;
import cofh.thermalexpansion.block.BlockTEBase;
import cofh.thermalexpansion.block.storage.BlockCache;
import cofh.thermalexpansion.block.storage.TileCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// Courtesy of Focamacho
@Mixin(value = BlockCache.class, remap = false)
public abstract class UTBlockCacheMixin extends BlockTEBase
{
    @Shadow
    private static boolean insertAllItemsFromPlayer(TileCache tile, EntityPlayer player)
    {
        return false;
    }

    protected UTBlockCacheMixin(Material material)
    {
        super(material);
    }

    @Override
    public boolean onBlockActivatedDelegate(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileCache tile = (TileCache) world.getTileEntity(pos);

        if (tile == null || !tile.canPlayerAccess(player))
        {
            return false;
        }
        if (ItemHelper.isPlayerHoldingNothing(player))
        {
            if (player.isSneaking())
            {
                tile.setLocked(!tile.isLocked());
                if (tile.isLocked())
                {
                    world.playSound(null, pos, SoundEvents.UI_BUTTON_CLICK, SoundCategory.BLOCKS, 0.2F, 0.8F);
                }
                else
                {
                    world.playSound(null, pos, SoundEvents.UI_BUTTON_CLICK, SoundCategory.BLOCKS, 0.3F, 0.5F);
                }
                return true;
            }
        }
        boolean playSound = false;

        if (hand != EnumHand.MAIN_HAND) return false;

        ItemStack heldItem = player.getHeldItem(hand);
        ItemStack ret = tile.insertItem(heldItem, false);
        long time = player.getEntityData().getLong("thermalexpansion:CacheClick"), currentTime = world.getTotalWorldTime();
        player.getEntityData().setLong("thermalexpansion:CacheClick", currentTime);

        if (!player.capabilities.isCreativeMode)
        {
            if (ret != heldItem)
            {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, ret);
                playSound = true;
            }
            if (!tile.getStoredInstance().isEmpty() && currentTime - time < 15)
            {
                playSound &= !insertAllItemsFromPlayer(tile, player);
            }
        }
        if (playSound)
        {
            world.playSound(null, pos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 0.1F, 0.7F);
        }
        return true;
    }
}