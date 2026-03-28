package mod.acgaming.universaltweaks.mods.simpledifficulty;

import net.minecraft.block.BlockCauldron;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;

import com.charles445.simpledifficulty.api.SDCapabilities;
import com.charles445.simpledifficulty.api.item.IItemCanteen;
import com.charles445.simpledifficulty.api.thirst.ThirstEnum;
import com.charles445.simpledifficulty.api.thirst.ThirstUtil;
import com.charles445.simpledifficulty.compat.CompatRightClick;
import com.charles445.simpledifficulty.item.ItemDragonCanteen;
import com.charles445.simpledifficulty.util.SoundUtil;
import mod.acgaming.universaltweaks.config.UTConfigMods;

public class UTCanteenCauldron
{
    public static void replaceCauldronHandler()
    {
        CompatRightClick.cauldronHandler = (event, world, pos, state, player) -> {
            ItemStack heldItem = player.getHeldItemMainhand();
            if (heldItem.isEmpty() && player.isSneaking())
            {
                if (SDCapabilities.getThirstData(player).isThirsty() || UTConfigMods.SIMPLE_DIFFICULTY.utAlwaysDrinkToggle)
                {
                    int level = state.getValue(BlockCauldron.LEVEL);
                    if (level > 0)
                    {
                        ThirstUtil.takeDrink(player, ThirstEnum.NORMAL);
                        SoundUtil.serverPlayBlockSound(world, pos, SoundEvents.ENTITY_GENERIC_DRINK);
                        if (UTConfigMods.SIMPLE_DIFFICULTY.utDrinkingConsumesWaterSourceToggle && !event.getWorld().isRemote && !event.getEntityPlayer().capabilities.isCreativeMode)
                        {
                            BlockCauldron cauldron = (BlockCauldron) state.getBlock();
                            cauldron.setWaterLevel(event.getWorld(), event.getPos(), state, level - 1);
                        }
                    }
                }
            }
            else if (heldItem.getItem() instanceof IItemCanteen)
            {
                int level = state.getValue(BlockCauldron.LEVEL);
                if (level > 0)
                {
                    IItemCanteen canteen = (IItemCanteen) heldItem.getItem();
                    if (canteen.tryAddDose(heldItem, canteen instanceof ItemDragonCanteen ? ThirstEnum.PURIFIED : ThirstEnum.NORMAL))
                    {
                        SoundUtil.serverPlayBlockSound(world, pos, SoundEvents.ITEM_BUCKET_FILL);
                        if (UTConfigMods.SIMPLE_DIFFICULTY.utDrinkingConsumesWaterSourceToggle && !event.getWorld().isRemote && !event.getEntityPlayer().capabilities.isCreativeMode)
                        {
                            BlockCauldron cauldron = (BlockCauldron) state.getBlock();
                            cauldron.setWaterLevel(event.getWorld(), event.getPos(), state, level - 1);
                        }
                    }
                }
            }
        };
    }
}
