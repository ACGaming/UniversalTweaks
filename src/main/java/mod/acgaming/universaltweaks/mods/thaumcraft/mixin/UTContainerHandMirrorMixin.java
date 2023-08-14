package mod.acgaming.universaltweaks.mods.thaumcraft.mixin;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.container.ContainerHandMirror;

// Courtesy of Focamacho
@Mixin(ContainerHandMirror.class)
public class UTContainerHandMirrorMixin
{
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/InventoryPlayer;getCurrentItem()Lnet/minecraft/item/ItemStack;"))
    private ItemStack getCurrentItem(InventoryPlayer iinventory)
    {
        ItemStack mirror = iinventory.player.getHeldItemMainhand();
        if (!mirror.getItem().equals(ItemsTC.handMirror) || !mirror.hasTagCompound()) mirror = iinventory.player.getHeldItemOffhand();
        return mirror;
    }
}