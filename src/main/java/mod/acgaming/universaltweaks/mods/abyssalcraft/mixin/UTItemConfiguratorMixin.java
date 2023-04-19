package mod.acgaming.universaltweaks.mods.abyssalcraft.mixin;

import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import com.shinoow.abyssalcraft.common.items.ItemConfigurator;
import mod.acgaming.universaltweaks.config.UTConfig;
import mod.acgaming.universaltweaks.mods.abyssalcraft.UTItemTransferManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = ItemConfigurator.class, remap = false)
public class UTItemConfiguratorMixin
{
    // Item is called "Spirit Tablet"
    /*
     * Mode reference:
     * mode == 0: Set Path
     *      - No changes needed, only edits the configurator's nbt
     * mode == 1: Apply Configuration
     *       - Need to add TE to UTItemTransferListHolder.configuredTileEntities
     * mode == 2: Clear Configurations
     *       - Need to remove TE from UTItemTransferListHolder.configuredTileEntities
     */

    // mode == 1
    @Inject(method = "onItemUse", at = @At(value = "INVOKE", target = "Lcom/shinoow/abyssalcraft/api/transfer/caps/IItemTransferCapability;addTransferConfiguration(Lcom/shinoow/abyssalcraft/api/transfer/ItemTransferConfiguration;)V"))
    private void utAddConfiguredTileEntity(EntityPlayer player, World w, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ, CallbackInfoReturnable<EnumActionResult> ci)
    {
        if (!UTConfig.MOD_INTEGRATION.ABYSSALCRAFT.utOptimizedItemTransferToggle) return;

        Chunk chunk = w.getChunk(pos);
        UTItemTransferManager.INSTANCE.addConfigured(chunk, pos, w.getTileEntity(pos));
        // Not sure if this is necessary?
        chunk.markDirty();
    }

    // mode == 2
    @Inject(method = "onItemUse", at = @At(value = "INVOKE", target = "Lcom/shinoow/abyssalcraft/api/transfer/caps/IItemTransferCapability;clearConfigurations()V"))
    private void utRemoveConfiguredTileEntity(EntityPlayer player, World w, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ, CallbackInfoReturnable<EnumActionResult> ci)
    {
        if (!UTConfig.MOD_INTEGRATION.ABYSSALCRAFT.utOptimizedItemTransferToggle) return;

        Chunk chunk = w.getChunk(pos);
        UTItemTransferManager.INSTANCE.removeConfigured(w.getChunk(pos), pos);
        // Not sure if this is necessary?
        chunk.markDirty();
    }
}
