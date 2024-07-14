package mod.acgaming.universaltweaks.tweaks.blocks.endportal.mixin;

import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.EnumFacing;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of WaitingIdly, TheRandomLabs (RandomPatches)
@Mixin(value = TileEntityEndPortal.class)
public abstract class UTTileEntityEndPortalMixin
{
    @ModifyReturnValue(method = "shouldRenderFace", at = @At("RETURN"))
    private boolean utEnsureDownIsRendered(boolean original, EnumFacing side)
    {
        if (!UTConfigTweaks.BLOCKS.utRenderEndPortalBottom) return original;
        return side == EnumFacing.UP || side == EnumFacing.DOWN;
    }
}