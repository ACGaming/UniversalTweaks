package mod.acgaming.universaltweaks.tweaks.items.bottle.mixin;

import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of WaitingIdly
@Mixin(ItemGlassBottle.class)
public abstract class UTItemGlassBottleMixin
{
    @Inject(method = "onItemRightClick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/EntityPlayer;DDDLnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FF)V", ordinal = 1, shift = At.Shift.AFTER))
    private void utConsumeWaterSourceBlock(CallbackInfoReturnable<ActionResult<ItemStack>> cir, @Local(argsOnly = true) World world, @Local BlockPos blockpos)
    {
        if (!UTConfigTweaks.ITEMS.utGlassBottlesConsumeWaterSource) return;
        world.setBlockToAir(blockpos);
    }
}
