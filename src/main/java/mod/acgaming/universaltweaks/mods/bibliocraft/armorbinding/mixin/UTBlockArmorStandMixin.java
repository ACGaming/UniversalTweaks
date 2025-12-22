package mod.acgaming.universaltweaks.mods.bibliocraft.armorbinding.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.llamalad7.mixinextras.sugar.Local;
import jds.bibliocraft.blocks.BlockArmorStand;
import jds.bibliocraft.tileentities.TileEntityArmorStand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of WaitingIdly
@Mixin(value = BlockArmorStand.class, remap = false)
public abstract class UTBlockArmorStandMixin
{
    /**
     * @author WaitingIdly
     * @reason Prevent removal of Curse of Binding items from the player unless the player is in creative mode.
     */
    @Inject(method = "handleArmorTransation", at = @At(value = "INVOKE", target = "Ljds/bibliocraft/tileentities/TileEntityArmorStand;getStackInSlot(I)Lnet/minecraft/item/ItemStack;"), cancellable = true)
    private void utEnsureValidity(EntityPlayer player, TileEntityArmorStand armorTile, int armortype, CallbackInfo ci, @Local ItemStack playerArmor)
    {
        if (EnchantmentHelper.hasBindingCurse(playerArmor) && !player.isCreative()) ci.cancel();
    }
}
