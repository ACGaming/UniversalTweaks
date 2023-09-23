package mod.acgaming.universaltweaks.mods.erebus.mixin;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import erebus.tileentity.TileEntityPreservedBlock;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of noobanidus
@Mixin(TileEntityPreservedBlock.class)
public class UTTileEntityPreservedBlockMixin
{
    @SuppressWarnings("ConstantConditions")
    @Inject(method = "markForUpdate", at = @At(value = "HEAD"), cancellable = true, remap = false)
    private void utMarkForUpdate(CallbackInfo info)
    {
        if (!UTConfigMods.EREBUS.utEBPreservedBlocksToggle) return;
        World world = ((TileEntityPreservedBlock) (Object) this).getWorld();
        if (world != null && !world.isRemote)
        {
            BlockPos pos = ((TileEntityPreservedBlock) (Object) this).getPos();
            ((TileEntityPreservedBlock) (Object) this).markDirty();
            final IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 8);
        }
        info.cancel();
    }
}