package mod.acgaming.universaltweaks.mods.extrautilities.radar.mixin;

import net.minecraft.inventory.IInventory;
import net.minecraft.world.storage.loot.ILootContainer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.rwtema.extrautils2.crafting.Radar;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@SuppressWarnings({"MixinAnnotationTarget", "InvalidInjectorMethodSignature"}) // mixin doesn't register as applying
@Mixin(value = Radar.PacketPing.class, remap = false)
public abstract class UTRadarLoottableMixin
{
    /**
     * @author WaitingIdly
     * @reason Allows disabling the radar generating loot on scanning inventories with loottables.
     */
    @Definition(id = "handler", local = @Local(type = IItemHandler.class))
    @Expression("handler == null")
    @ModifyExpressionValue(method = "lambda$doStuffServer$1", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean utSkipLoottables(boolean original, @Local IItemHandler handler)
    {
        if (original) return true;
        if (!UTConfigMods.EXTRA_UTILITIES.utRadarSkipsLoottables) return false;

        if (handler instanceof InvWrapper)
        {
            IInventory inv = ((InvWrapper) handler).getInv();
            if (inv instanceof ILootContainer)
            {
                // this *can* be null - and in vanilla inventories,
                // it's set to null when loot is generated.
                //noinspection ConstantValue
                return ((ILootContainer) inv).getLootTable() != null;
            }
        }
        return true;
    }
}
