package mod.acgaming.universaltweaks.mods.tconstruct.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.tools.melee.item.LongSword;
import slimeknights.tconstruct.tools.ranged.TinkerRangedWeapons;

@Mixin(LongSword.class)
public class UTLongSwordMixin
{
    @Inject(method = "onItemRightClick", at = @At(value = "HEAD"), cancellable = true)
    public void utTConLongSwordOffhand(World worldIn, EntityPlayer playerIn, EnumHand hand, CallbackInfoReturnable<ActionResult<ItemStack>> cir)
    {
        if (!UTConfig.MOD_INTEGRATION.TINKERS_CONSTRUCT.utTConShurikenToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTLongSword ::: On item right click");
        if (!playerIn.getHeldItemOffhand().isEmpty() && (playerIn.getHeldItemOffhand().getItem() == TinkerRangedWeapons.shuriken))
        {
            cir.setReturnValue(ActionResult.newResult(EnumActionResult.PASS, playerIn.getHeldItem(hand)));
        }
    }
}