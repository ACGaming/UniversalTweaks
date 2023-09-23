package mod.acgaming.universaltweaks.mods.simpledifficulty.mixin;

import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.charles445.simpledifficulty.api.item.IItemCanteen;
import com.charles445.simpledifficulty.api.thirst.ThirstEnum;
import com.charles445.simpledifficulty.block.BlockRainCollector;
import com.charles445.simpledifficulty.item.ItemCanteen;
import com.charles445.simpledifficulty.util.SoundUtil;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockRainCollector.class)
public abstract class UTRainCollectorCanteenMixin
{
    @Shadow
    @Final
    public static PropertyInteger LEVEL;

    @Shadow
    public abstract void setWaterLevel(World world, BlockPos pos, IBlockState state, int level);

    @Inject(method = "onBlockActivated", at = @At(value = "RETURN", ordinal = 4), cancellable = true)
    public void utRainCollectorCanteen(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfigMods.SIMPLE_DIFFICULTY.utRainCollectorCanteenToggle) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTRainCollectorCanteen ::: On block activated");
        ItemStack itemStack = player.getHeldItem(hand);
        Item item = itemStack.getItem();
        int amount = state.getValue(LEVEL);
        if (item instanceof ItemCanteen && amount > 0 && !world.isRemote && !player.capabilities.isCreativeMode)
        {
            IItemCanteen canteen = (IItemCanteen) item;
            canteen.getThirstEnum(itemStack);
            if (canteen.tryAddDose(itemStack, ThirstEnum.PURIFIED))
            {
                this.setWaterLevel(world, pos, state, amount - 1);
                SoundUtil.serverPlayBlockSound(world, pos, SoundEvents.ITEM_BUCKET_FILL);
            }
            cir.setReturnValue(true);
        }
    }
}