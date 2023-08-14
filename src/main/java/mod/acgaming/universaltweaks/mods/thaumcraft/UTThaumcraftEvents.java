package mod.acgaming.universaltweaks.mods.thaumcraft;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.common.container.ContainerArcaneWorkbench;
import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;
import thaumcraft.common.lib.utils.InventoryUtils;
import thaumcraft.common.tiles.misc.TileHole;

// Courtesy of Focamacho
public class UTThaumcraftEvents
{
    //Arcane Workbench Dupe Fix
    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event)
    {
        Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
        if (block != null && Block.isEqualTo(block, BlocksTC.arcaneWorkbench))
        {
            if (event.getEntityPlayer() != null)
            {
                if (event.getEntityPlayer().openContainer instanceof ContainerArcaneWorkbench)
                {
                    event.setCanceled(true);
                    return;
                }

                event.getEntityPlayer().getEntityData().setInteger("ThaumcraftWorkbenchX", event.getPos().getX());
                event.getEntityPlayer().getEntityData().setInteger("ThaumcraftWorkbenchY", event.getPos().getY());
                event.getEntityPlayer().getEntityData().setInteger("ThaumcraftWorkbenchZ", event.getPos().getZ());
                event.getEntityPlayer().getEntityData().setBoolean("ThaumcraftWorkbenchDupeFix", true);

                List<EntityPlayer> players = event.getWorld().getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(event.getPos().getX() - 32, event.getPos().getY() - 32, event.getPos().getZ() - 32, event.getPos().getX() + 32, event.getPos().getY() + 32, event.getPos().getZ() + 32));
                for (EntityPlayer player : players)
                {
                    if (player == event.getEntityPlayer()) continue;
                    if (player.openContainer instanceof ContainerArcaneWorkbench)
                    {
                        if (player.getEntityData().getBoolean("ThaumcraftWorkbenchDupeFix"))
                        {
                            NBTTagCompound playerData = player.getEntityData();
                            if (playerData.getInteger("ThaumcraftWorkbenchX") == event.getPos().getX() && playerData.getInteger("ThaumcraftWorkbenchY") == event.getPos().getY() && playerData.getInteger("ThaumcraftWorkbenchZ") == event.getPos().getZ())
                            {
                                event.setCanceled(true);
                                event.getEntityPlayer().sendMessage(new TextComponentString("Someone is already using this."));
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onTickPlayer(TickEvent.PlayerTickEvent event)
    {
        if (event.player.openContainer != null && !(event.player.openContainer instanceof ContainerArcaneWorkbench))
        {
            event.player.getEntityData().setBoolean("ThaumcraftWorkbenchDupeFix", false);
        }
    }

    //Collector Enchantment Fix
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void harvestDrops(BlockEvent.HarvestDropsEvent event)
    {
        if (event.getHarvester() != null && event.getHarvester().getActiveHand() != null)
        {
            ItemStack heldItem = event.getHarvester().getHeldItem(event.getHarvester().getActiveHand());
            if (heldItem.hasTagCompound() && heldItem.getTagCompound().hasKey("infench"))
            {
                NBTTagList nbtList = EnumInfusionEnchantment.getInfusionEnchantmentTagList(heldItem);
                for (int i = 0; i < nbtList.tagCount(); i++)
                {
                    if (nbtList.getCompoundTagAt(i).getShort("id") == 0)
                    {
                        nbtList.removeTag(i);
                        heldItem.setTagInfo("infench", nbtList);
                        heldItem.getTagCompound().setBoolean("fixCollector", true);
                        break;
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void dropsCheck(BlockEvent.HarvestDropsEvent event)
    {
        if (event.getHarvester() != null && event.getHarvester().getActiveHand() != null)
        {
            ItemStack heldItem = event.getHarvester().getHeldItem(event.getHarvester().getActiveHand());
            if (heldItem != null && !heldItem.isEmpty())
            {
                if (heldItem.hasTagCompound() && heldItem.getTagCompound().hasKey("fixCollector"))
                {
                    InventoryUtils.dropHarvestsAtPos(event.getWorld(), event.getPos(), event.getDrops(), true, 10, event.getHarvester());
                    event.getDrops().clear();
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void removeTagB(LivingDropsEvent event)
    {
        if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityPlayer && ((EntityPlayer) event.getSource().getTrueSource()).getActiveHand() != null)
        {
            ItemStack heldItem = ((EntityPlayer) event.getSource().getTrueSource()).getHeldItem(((EntityPlayer) event.getSource().getTrueSource()).getActiveHand());
            if (heldItem != null && !heldItem.isEmpty())
            {
                if (heldItem.hasTagCompound() && heldItem.getTagCompound().hasKey("infench"))
                {
                    NBTTagList nbtList = EnumInfusionEnchantment.getInfusionEnchantmentTagList(heldItem);
                    for (int i = 0; i < nbtList.tagCount(); i++)
                    {
                        if (nbtList.getCompoundTagAt(i).getShort("id") == 0)
                        {
                            nbtList.removeTag(i);
                            heldItem.setTagInfo("infench", nbtList);
                            heldItem.getTagCompound().setBoolean("fixCollector", true);
                            break;
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void livingDrops(LivingDropsEvent event)
    {
        if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityPlayer && ((EntityPlayer) event.getSource().getTrueSource()).getActiveHand() != null)
        {
            ItemStack heldItem = ((EntityPlayer) event.getSource().getTrueSource()).getHeldItem(((EntityPlayer) event.getSource().getTrueSource()).getActiveHand());
            if (heldItem != null && !heldItem.isEmpty())
            {
                if (heldItem.hasTagCompound() && heldItem.getTagCompound().hasKey("fixCollector"))
                {
                    List<ItemStack> itemsDrop = new ArrayList<ItemStack>();
                    for (EntityItem drop : event.getDrops())
                    {
                        itemsDrop.add(drop.getItem());
                    }
                    InventoryUtils.dropHarvestsAtPos(event.getEntity().world, new BlockPos(event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ), itemsDrop, true, 10, event.getSource().getTrueSource());
                    event.getDrops().clear();
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void tooltipFix(ItemTooltipEvent event)
    {
        if (event.getItemStack().hasTagCompound() && event.getItemStack().getTagCompound().hasKey("fixCollector"))
        {
            event.getToolTip().add(1, TextFormatting.GOLD + I18n.translateToLocal("enchantment.infusion.COLLECTOR"));
        }
    }

    //Arcane Stone Dupe Fix
    @SubscribeEvent
    public void arcaneStoneFix(BlockEvent.NeighborNotifyEvent event)
    {
        if (event.getState() != null && event.getState().getBlock().equals(BlocksTC.hole))
        {
            if (event.getWorld().getTileEntity(event.getPos()) != null && event.getWorld().getTileEntity(event.getPos()) instanceof TileHole)
            {
                for (EntityItem item : event.getWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(event.getPos())))
                {
                    if (item.getItem().getItem().equals(Item.getItemFromBlock(BlocksTC.stoneArcane)))
                    {
                        event.getWorld().removeEntity(item);
                    }
                }
            }
        }
    }
}