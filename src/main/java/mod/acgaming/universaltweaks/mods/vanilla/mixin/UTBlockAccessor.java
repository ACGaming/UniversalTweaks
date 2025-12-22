package mod.acgaming.universaltweaks.mods.vanilla.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Block.class)
public interface UTBlockAccessor
{
    @Invoker("getSilkTouchDrop")
    ItemStack callGetSilkTouchDrop(IBlockState state);
}
