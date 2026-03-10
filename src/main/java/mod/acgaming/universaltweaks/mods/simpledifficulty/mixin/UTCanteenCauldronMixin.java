package mod.acgaming.universaltweaks.mods.simpledifficulty.mixin;

import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.charles445.simpledifficulty.handler.ThirstHandler;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ThirstHandler.class, remap = false)
public class UTCanteenCauldronMixin
{
    @Inject(method = "onRightClickBlock", at = @At(value = "INVOKE", target = "Lcom/charles445/simpledifficulty/compat/CompatRightClick$IRightClick;process(Lnet/minecraftforge/event/entity/player/PlayerInteractEvent$RightClickBlock;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/entity/player/EntityPlayer;)V"))
    public void utCanteenCauldron(PlayerInteractEvent.RightClickBlock event, CallbackInfo ci)
    {
        if (UTConfigMods.SIMPLE_DIFFICULTY.utCanteensConsumeWaterSourceToggle && !event.getWorld().isRemote && !event.getEntityPlayer().capabilities.isCreativeMode)
        {
            IBlockState blockState = event.getWorld().getBlockState(event.getPos());
            BlockCauldron cauldron = (BlockCauldron) blockState.getBlock();
            int i = blockState.getValue(BlockCauldron.LEVEL);
            cauldron.setWaterLevel(event.getWorld(), event.getPos(), blockState, i - 1);
        }
    }
}
