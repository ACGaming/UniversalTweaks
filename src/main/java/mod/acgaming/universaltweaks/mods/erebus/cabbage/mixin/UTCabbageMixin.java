package mod.acgaming.universaltweaks.mods.erebus.cabbage.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import erebus.ModItems;
import erebus.blocks.BlockCabbage;
import erebus.items.ItemErebusFood;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

// Courtesy of WaitingIdly
@Mixin(value = BlockCabbage.class, remap = false)
public abstract class UTCabbageMixin extends BlockCrops
{
    @Inject(method = "getDrops", at = @At("HEAD"), cancellable = true)
    private void utOverrideDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune, CallbackInfoReturnable<List<ItemStack>> cir)
    {
        if (!UTConfigMods.EREBUS.utCabbageDrop) return;
        NonNullList<ItemStack> drops = NonNullList.create();
        getDrops(drops, world, pos, state, fortune);
        cir.setReturnValue(drops);
    }

    @Override
    public int damageDropped(IBlockState blockState)
    {
        if (!UTConfigMods.EREBUS.utCabbageDrop) return 0;
        return ItemErebusFood.EnumFoodType.CABBAGE.ordinal();
    }

    @ModifyReturnValue(method = "getCrop", at = @At("RETURN"))
    private Item utGetCrop(Item original)
    {
        if (!UTConfigMods.EREBUS.utCabbageDrop) return original;
        return ModItems.EREBUS_FOOD;
    }
}