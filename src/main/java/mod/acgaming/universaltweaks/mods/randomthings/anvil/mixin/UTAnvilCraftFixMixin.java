package mod.acgaming.universaltweaks.mods.randomthings.anvil.mixin;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import lumien.randomthings.handler.RTEventHandler;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(value = RTEventHandler.class, remap = false)
public class UTAnvilCraftFixMixin
{
    @WrapOperation(method = "anvilUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/event/AnvilUpdateEvent;setOutput(Lnet/minecraft/item/ItemStack;)V"))
    private void utFixRecipeVoiding(AnvilUpdateEvent instance, ItemStack item, Operation<Void> original)
    {
        if (!UTConfigMods.RANDOM_THINGS.utAnvilCraftFix) original.call(instance, item);
        else original.call(instance, item.copy());
    }
}