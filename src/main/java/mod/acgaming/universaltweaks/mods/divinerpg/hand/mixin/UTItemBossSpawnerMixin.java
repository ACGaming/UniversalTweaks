package mod.acgaming.universaltweaks.mods.divinerpg.hand.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import divinerpg.objects.items.twilight.ItemBossSpawner;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(value = ItemBossSpawner.class, remap = false)
public abstract class UTItemBossSpawnerMixin
{
    /**
     * @author WaitingIdly
     * @reason fix item voiding and duplication when using any of the boss spawning items in the offhand
     */
    @WrapOperation(method = "onItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;getHeldItemMainhand()Lnet/minecraft/item/ItemStack;"), remap = true)
    private ItemStack utUseCorrectHand(EntityPlayer instance, Operation<ItemStack> original, @Local(argsOnly = true) EnumHand hand)
    {
        if (!UTConfigMods.DIVINE_RPG.utFixHandConsumption) return original.call(instance);
        return instance.getHeldItem(hand);
    }
}
