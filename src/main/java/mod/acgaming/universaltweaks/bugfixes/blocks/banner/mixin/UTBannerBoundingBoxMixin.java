package mod.acgaming.universaltweaks.bugfixes.blocks.banner.mixin;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.math.AxisAlignedBB;

import org.spongepowered.asm.mixin.Mixin;

// Courtesy of fonnymunkey
@Mixin(TileEntityBanner.class)
public abstract class UTBannerBoundingBoxMixin extends TileEntity
{
    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
        // 3 block tall bounding box, since getPos() changes depending on placed on wall or floor
        return new AxisAlignedBB(getPos().add(0, -1, 0), getPos().add(1, 2, 1));
    }
}