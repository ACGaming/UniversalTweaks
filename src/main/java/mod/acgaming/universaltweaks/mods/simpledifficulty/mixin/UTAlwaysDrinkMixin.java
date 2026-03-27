package mod.acgaming.universaltweaks.mods.simpledifficulty.mixin;

import com.charles445.simpledifficulty.api.thirst.IThirstCapability;
import com.charles445.simpledifficulty.item.ItemCanteen;
import com.charles445.simpledifficulty.item.ItemDragonCanteen;
import com.charles445.simpledifficulty.item.ItemDrinkBase;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({ItemDrinkBase.class, ItemCanteen.class, ItemDragonCanteen.class})
public abstract class UTAlwaysDrinkMixin
{
    @Redirect(method = "onItemRightClick", at = @At(value = "INVOKE", target = "Lcom/charles445/simpledifficulty/api/thirst/IThirstCapability;isThirsty()Z", remap = false))
    public boolean utAlwaysDrink(IThirstCapability capability)
    {
        return capability.isThirsty() || UTConfigMods.SIMPLE_DIFFICULTY.utAlwaysDrinkToggle;
    }
}
