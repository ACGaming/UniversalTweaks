package mod.acgaming.universaltweaks.mods.backpack.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import de.eydamos.backpack.item.ItemBackpack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemBackpack.class)
public abstract class UTItemBackpackMixin
{
    @Inject(method = "onItemRightClick", at = @At("RETURN"), cancellable = true)
    public void utOnItemBackpackRightClick(World world, EntityPlayer player, EnumHand hand, CallbackInfoReturnable<ActionResult<ItemStack>> cir)
    {
        cir.setReturnValue(new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand)));
    }
}
