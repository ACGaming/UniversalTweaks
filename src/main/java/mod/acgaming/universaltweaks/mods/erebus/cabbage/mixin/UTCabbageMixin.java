package mod.acgaming.universaltweaks.mods.erebus.cabbage.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import erebus.ModItems;
import erebus.blocks.BlockCabbage;
import erebus.items.ItemErebusFood;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

// Courtesy of WaitingIdly
@Mixin(value = BlockCabbage.class, remap = false)
public abstract class UTCabbageMixin extends BlockCrops
{
    @Inject(method = "getDrops", at = @At("RETURN"), cancellable = true)
    private void utOverrideDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune, CallbackInfoReturnable<List<ItemStack>> cir)
    {
        if (!UTConfigMods.EREBUS.utCabbageDrop) return;
        NonNullList<ItemStack> drops = NonNullList.create();
        getDrops(drops, world, pos, state, fortune);
        cir.setReturnValue(drops);
    }

    @Override
    public void getDrops(@Nonnull NonNullList<ItemStack> drops, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull IBlockState state, int fortune)
    {
        if (!UTConfigMods.EREBUS.utCabbageDrop) return;
        Random rand = world instanceof World ? ((World) world).rand : RANDOM;
        int age = getAge(state);

        int count = quantityDropped(state, 0, rand);
        for (int i = 0; i < count; i++)
        {
            Item item = this.getItemDropped(state, rand, 0);
            if (item != Items.AIR)
            {
                drops.add(new ItemStack(item, 1, ItemErebusFood.EnumFoodType.CABBAGE.ordinal()));
            }
        }

        if (age >= getMaxAge())
        {
            for (int i = 0; i < 3 + fortune; ++i)
            {
                if (rand.nextInt(2 * getMaxAge()) <= age)
                {
                    drops.add(new ItemStack(this.getSeed(), 1, 0));
                }
            }
        }
    }

    @ModifyReturnValue(method = "getCrop", at = @At("RETURN"))
    private Item utGetCrop(Item original)
    {
        if (!UTConfigMods.EREBUS.utCabbageDrop) return original;
        return ModItems.EREBUS_FOOD;
    }
}