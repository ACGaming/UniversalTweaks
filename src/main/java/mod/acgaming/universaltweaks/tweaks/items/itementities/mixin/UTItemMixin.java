package mod.acgaming.universaltweaks.tweaks.items.itementities.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Item.class, priority = 1002)
public abstract class UTItemMixin
{
    @Inject(method = "getEntityLifespan", at = @At("RETURN"), cancellable = true, remap = false)
    public void utIEGetEntityLifespan(ItemStack itemStack, World world, CallbackInfoReturnable<Integer> cir)
    {
        if (UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utIELifespan > -1) cir.setReturnValue(UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utIELifespan);
    }
}