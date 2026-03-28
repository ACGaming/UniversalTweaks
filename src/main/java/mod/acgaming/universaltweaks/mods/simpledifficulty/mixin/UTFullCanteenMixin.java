package mod.acgaming.universaltweaks.mods.simpledifficulty.mixin;

import net.minecraft.item.ItemStack;

import com.charles445.simpledifficulty.api.thirst.ThirstEnum;
import com.charles445.simpledifficulty.item.ItemCanteen;
import com.llamalad7.mixinextras.sugar.Local;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemCanteen.class)
public abstract class UTFullCanteenMixin
{
    @Shadow(remap = false)
    public abstract boolean isCanteenFull(ItemStack stack);

    @Redirect(method = "onItemRightClick", at = @At(value = "FIELD", target = "Lcom/charles445/simpledifficulty/api/thirst/ThirstEnum;NORMAL:Lcom/charles445/simpledifficulty/api/thirst/ThirstEnum;", remap = false, ordinal = 0, opcode = Opcodes.GETSTATIC))
    public ThirstEnum utFullCanteen(@Local(name = "stack") ItemStack stack)
    {
        return isCanteenFull(stack) ? ThirstEnum.RAIN : ThirstEnum.NORMAL;
    }
}
