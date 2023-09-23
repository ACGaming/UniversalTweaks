package mod.acgaming.universaltweaks.mods.abyssalcraft;

import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.shinoow.abyssalcraft.api.transfer.caps.ItemTransferCapabilityProvider;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import mod.acgaming.universaltweaks.mods.abyssalcraft.worlddata.UTWorldDataCapability;
import mod.acgaming.universaltweaks.mods.abyssalcraft.worlddata.UTWorldDataCapabilityProvider;

// Courtesy of jchung01
public class UTAbyssalCraftEvents
{
    private static final String CHECK_ID = UniversalTweaks.MODID + "AbyssalConfigurations";

    @SubscribeEvent
    public void attachCapabilityWorld(AttachCapabilitiesEvent<World> event)
    {
        if (!UTConfigMods.ABYSSALCRAFT.utOptimizedItemTransferToggle) return;

        event.addCapability(new ResourceLocation("universaltweaks", "WorldDataCapability"), new UTWorldDataCapabilityProvider());
    }

    // Save data from memory to nbt
    @SubscribeEvent
    public void onChunkSave(ChunkDataEvent.Save event)
    {
        if (!UTConfigMods.ABYSSALCRAFT.utOptimizedItemTransferToggle) return;

        Chunk chunk = event.getChunk();
        Map<BlockPos, TileEntity> set = UTWorldDataCapability.getCap(event.getWorld()).getChunkMap(chunk);
        // Save configured tile entity positions to nbt
        if (set != null && !set.isEmpty())
        {
            NBTTagList tagList = new NBTTagList();
            for (BlockPos pos : set.keySet())
            {
                TileEntity te = event.getWorld().getTileEntity(pos);
                // Skip blocks that are no longer the expected tile entity (block was broken/replaced)
                if (te != null && !te.hasCapability(ItemTransferCapabilityProvider.ITEM_TRANSFER_CAP, null)) continue;
                NBTTagCompound tag = new NBTTagCompound();
                tag.setInteger("x", pos.getX());
                tag.setInteger("y", pos.getY());
                tag.setInteger("z", pos.getZ());
                tagList.appendTag(tag);
            }
            // Cleanup unloaded/invalid chunks from memory
            if (!event.getChunk().isLoaded() || tagList.isEmpty())
            {
                UTWorldDataCapability.getCap(event.getWorld()).removeChunk(event.getChunk());
                if (tagList.isEmpty()) return;
            }

            event.getData().setTag(CHECK_ID, tagList);
        }
    }

    // Load data from nbt to memory
    @SubscribeEvent
    public void onChunkLoad(ChunkDataEvent.Load event)
    {
        if (!UTConfigMods.ABYSSALCRAFT.utOptimizedItemTransferToggle) return;
        if (event.getWorld().isRemote) return;

        if (event.getData().hasKey(CHECK_ID))
        {
            World world = event.getWorld();
            NBTTagList tagList = event.getData().getTagList(CHECK_ID, Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < tagList.tagCount(); i++)
            {
                NBTTagCompound tag = tagList.getCompoundTagAt(i);
                BlockPos pos = new BlockPos(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"));

                // Add entry to memory (Tile entity is unknown if world is still loading)
                TileEntity te = world.isBlockLoaded(pos) ? world.getTileEntity(pos) : null;
                UTWorldDataCapability.getCap(world).addConfigured(event.getChunk(), pos, te);
            }
        }
    }
}
