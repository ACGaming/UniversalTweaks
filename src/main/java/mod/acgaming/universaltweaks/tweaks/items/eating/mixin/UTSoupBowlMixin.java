package mod.acgaming.universaltweaks.tweaks.items.eating.mixin;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemSoup.class)
public abstract class UTSoupBowlMixin
{
    @Inject(method = "onItemUseFinish", at = @At(value = "RETURN"), cancellable = true)
    public void utSoupBowl(ItemStack stack, World world, EntityLivingBase entityLiving, CallbackInfoReturnable<ItemStack> cir)
    {
        if (!(entityLiving instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) entityLiving;
        if (!stack.isEmpty())
        {
            if (!player.inventory.addItemStackToInventory(new ItemStack(Items.BOWL)))
            {
                player.dropItem(new ItemStack(Items.BOWL), false);
            }
            cir.setReturnValue(stack);
        }
    }
}
