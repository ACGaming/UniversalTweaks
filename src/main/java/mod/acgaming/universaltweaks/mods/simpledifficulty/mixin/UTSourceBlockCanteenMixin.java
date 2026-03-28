package mod.acgaming.universaltweaks.mods.simpledifficulty.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import com.charles445.simpledifficulty.api.thirst.ThirstEnum;
import com.charles445.simpledifficulty.api.thirst.ThirstEnumBlockPos;
import com.charles445.simpledifficulty.item.ItemCanteen;
import com.charles445.simpledifficulty.item.ItemDragonCanteen;
import com.llamalad7.mixinextras.sugar.Local;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ItemCanteen.class, ItemDragonCanteen.class})
public abstract class UTSourceBlockCanteenMixin extends Item
{
    @Inject(method = "onItemRightClick", at = @At(value = "INVOKE", target = "Lcom/charles445/simpledifficulty/util/SoundUtil;commonPlayPlayerSound(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/SoundEvent;)V", remap = false))
    public void utSourceBlockCanteen(World world, EntityPlayer player, EnumHand hand, CallbackInfoReturnable<ActionResult<ItemStack>> cir, @Local(name = "traceBlockPos") ThirstEnumBlockPos traceBlockPos)
    {
        if (UTConfigMods.SIMPLE_DIFFICULTY.utDrinkingConsumesWaterSourceToggle && traceBlockPos.thirstEnum != ThirstEnum.PURIFIED)
        {
            player.world.setBlockToAir(traceBlockPos.pos);
        }
    }
}
