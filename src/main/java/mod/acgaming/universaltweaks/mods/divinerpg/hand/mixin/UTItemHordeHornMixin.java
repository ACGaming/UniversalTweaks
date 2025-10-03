package mod.acgaming.universaltweaks.mods.divinerpg.hand.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import divinerpg.objects.items.twilight.ItemBossSpawner;
import divinerpg.objects.items.vanilla.ItemHordeHorn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import mod.acgaming.universaltweaks.config.UTConfigMods;

// Courtesy of WaitingIdly
@Mixin(value = ItemHordeHorn.class, remap = false)
public abstract class UTItemHordeHornMixin
{
    /**
     * @author WaitingIdly
     * @reason fix item voiding and duplication when using the horde horn in the offhand
     */
    @WrapOperation(method = "onItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;getHeldItemMainhand()Lnet/minecraft/item/ItemStack;"))
    private ItemStack utUseCorrectHand(EntityPlayer instance, Operation<ItemStack> original, @Local(argsOnly = true) EnumHand hand)
    {
        if (!UTConfigMods.DIVINE_RPG.utFixHandConsumption) return original.call(instance);
        return instance.getHeldItem(hand);
    }
}
