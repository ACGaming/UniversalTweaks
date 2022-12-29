package mod.acgaming.universaltweaks.mods.thaumcraft.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import thaumcraft.common.items.tools.ItemThaumometer;

@Mixin(ItemThaumometer.class)
public class UTStableThaumometerMixin
{
    @Inject(method = "onItemRightClick", at = @At(value = "RETURN"), cancellable = true)
    public void utStableThaumometer(World world, EntityPlayer p, EnumHand hand, CallbackInfoReturnable<ActionResult<ItemStack>> cir)
    {
        if (!UTConfig.mods.utTCStableThaumometerToggle) return;
        cir.setReturnValue(new ActionResult<>(EnumActionResult.FAIL, p.getHeldItem(hand)));
    }
}