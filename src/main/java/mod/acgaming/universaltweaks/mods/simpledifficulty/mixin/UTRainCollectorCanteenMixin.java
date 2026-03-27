package mod.acgaming.universaltweaks.mods.simpledifficulty.mixin;

import net.minecraft.item.Item;

import com.charles445.simpledifficulty.api.SDItems;
import com.charles445.simpledifficulty.api.item.IItemCanteen;
import com.charles445.simpledifficulty.block.BlockRainCollector;
import com.llamalad7.mixinextras.sugar.Local;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockRainCollector.class)
public abstract class UTRainCollectorCanteenMixin
{
    @Redirect(method = "onBlockActivated", at = @At(value = "FIELD", target = "Lcom/charles445/simpledifficulty/api/SDItems;canteen:Lnet/minecraft/item/Item;", remap = false, opcode = Opcodes.GETSTATIC))
    private Item utRainCollectorCanteen(@Local(name = "item") Item item)
    {
        return (item instanceof IItemCanteen) ? item : SDItems.canteen;
    }
}
