package mod.acgaming.universaltweaks.bugfixes.mixin;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// MC-9568
// https://bugs.mojang.com/browse/MC-9568
// Courtesy of mrgrim
@Mixin(Entity.class)
public abstract class UTEntitySuffocationMixin
{
    @Shadow
    public World world;
    @Shadow
    public double posX;
    @Shadow
    public double posY;
    @Shadow
    public double posZ;
    @Shadow
    public float width;
    @Shadow
    public float height;
    @Shadow
    protected boolean firstUpdate;

    @Shadow
    public abstract AxisAlignedBB getEntityBoundingBox();

    @Shadow
    public abstract void setEntityBoundingBox(AxisAlignedBB bb);

    @Inject(method = "setSize", at = @At("HEAD"), cancellable = true)
    public void utOnSetSize(float width, float height, CallbackInfo ci)
    {
        if (!UTConfig.BUGFIXES_ENTITIES.utEntitySuffocationToggle) return;
        //if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTEntitySuffocationMixin ::: Set entity size");
        if (width != this.width || height != this.height)
        {
            float f = this.width;
            this.width = width;
            this.height = height;
            AxisAlignedBB oldAABB = this.getEntityBoundingBox();
            double d0 = (double) width / 2.0D;
            this.setEntityBoundingBox(new AxisAlignedBB(this.posX - d0, this.posY, this.posZ - d0, this.posX + d0, this.posY + (double) this.height, this.posZ + d0));
            if (this.width > f && !this.firstUpdate && !this.world.isRemote) this.pushEntityOutOfBlocks(oldAABB); // new code proposed for fixing MC-9568
        }
        ci.cancel();
    }

    public void pushEntityOutOfBlocks(AxisAlignedBB oldHitbox)
    {
        // Pass "null" in first argument to only get _possible_ block collisions
        List<AxisAlignedBB> list1 = this.world.getCollisionBoxes(null, this.getEntityBoundingBox());
        AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
        for (AxisAlignedBB i : list1)
        {
            if (!oldHitbox.intersects(i) && axisalignedbb.intersects(i))
            {
                double minX = axisalignedbb.minX;
                double maxX = axisalignedbb.maxX;
                double minZ = axisalignedbb.minZ;
                double maxZ = axisalignedbb.maxZ;
                // Check for collisions on the X and Z axis, and only push the new AABB if the colliding blocks AABB
                // is completely to the opposite side of the original AABB
                if (i.maxX > axisalignedbb.minX && i.minX < axisalignedbb.maxX)
                {
                    if (i.maxX >= oldHitbox.maxX && i.minX >= oldHitbox.maxX)
                    {
                        minX = i.minX - this.width;
                        maxX = i.minX;
                    }
                    else if (i.maxX <= oldHitbox.minX && i.minX <= oldHitbox.minX)
                    {
                        minX = i.maxX;
                        maxX = i.maxX + this.width;
                    }
                }
                if (i.maxZ > axisalignedbb.minZ && i.minZ < axisalignedbb.maxZ)
                {
                    if (i.minZ >= oldHitbox.maxZ && i.maxZ >= oldHitbox.maxZ)
                    {
                        minZ = i.minZ - this.width;
                        maxZ = i.minZ;
                    }
                    else if (i.maxZ <= oldHitbox.minZ && i.minZ <= oldHitbox.minZ)
                    {
                        minZ = i.maxZ;
                        maxZ = i.maxZ + this.width;
                    }
                }
                axisalignedbb = new AxisAlignedBB(minX, axisalignedbb.minY, minZ, maxX, axisalignedbb.maxY, maxZ);
            }
        }
        this.setEntityBoundingBox(axisalignedbb);
    }
}