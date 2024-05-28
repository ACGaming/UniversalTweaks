package mod.acgaming.universaltweaks.tweaks.items.bucket.mixin;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBucket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of WaitingIdly, TheRandomLabs (RandomPatches)
@Mixin(value = ItemBucket.class)
public abstract class UTPreventBucketReplacingPortalMixin
{
    @WrapOperation(method = "tryPlaceContainedLiquid", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/material/Material;isSolid()Z"))
    private boolean utPreventPlacementIfPortalBlock(Material instance, Operation<Boolean> original, @Nullable EntityPlayer player, World worldIn, BlockPos posIn)
    {
        if (original.call(instance)) return true;
        if (!UTConfigTweaks.ITEMS.utPreventBucketPlacingInPortal) return true;
        return worldIn.getBlockState(posIn).getBlock() == Blocks.PORTAL || instance == Material.PORTAL;
    }
}