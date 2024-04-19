package mod.acgaming.universaltweaks.mods.extrautilities.mutabledrops.mixin;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.rwtema.extrautils2.machine.BlockMachine;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(value = BlockMachine.class, remap = false)
public abstract class UTMutableBlockMachineDrops
{
    @ModifyReturnValue(method = "getDrops", at = @At(value = "RETURN"))
    private List<ItemStack> utEnforceMutableDrops(List<ItemStack> original)
    {
        if (!UTConfigMods.EXTRA_UTILITIES.utMutableBlockDrops) return original;
        return new ArrayList<>(original);
    }
}
