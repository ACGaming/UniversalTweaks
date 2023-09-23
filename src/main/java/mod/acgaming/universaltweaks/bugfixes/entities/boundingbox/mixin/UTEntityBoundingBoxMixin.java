package mod.acgaming.universaltweaks.bugfixes.entities.boundingbox.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.AxisAlignedBB;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// MC-2025
// https://bugs.mojang.com/browse/MC-2025
// Courtesy of mrgrim
@Mixin(Entity.class)
public abstract class UTEntityBoundingBoxMixin
{
    @Shadow
    public double posX;
    @Shadow
    public double posY;
    @Shadow
    public double posZ;

    @Shadow
    public abstract AxisAlignedBB getEntityBoundingBox();

    @Shadow
    public abstract void setEntityBoundingBox(AxisAlignedBB bb);

    @Shadow
    public abstract void setPosition(double x, double y, double z);

    @Inject(method = "writeToNBT", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NBTTagCompound;setUniqueId(Ljava/lang/String;Ljava/util/UUID;)V", shift = At.Shift.AFTER, ordinal = 0))
    public void utSaveAABBToNBT(NBTTagCompound compound, CallbackInfoReturnable<NBTTagCompound> ci)
    {
        if (UTConfigBugfixes.ENTITIES.utEntityAABBToggle)
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTEntityBoundingBox ::: Write AABB to NBT");
            AxisAlignedBB aabb = this.getEntityBoundingBox();
            compound.setTag("AABB", this.newDoubleNBTList(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ));
        }
    }

    @Redirect(method = "readFromNBT", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;shouldSetPosAfterLoading()Z"))
    public boolean utReadAABBFromNBT(Entity entity, NBTTagCompound compound)
    {
        if (this.shouldSetPosAfterLoading()) this.setPosition(this.posX, this.posY, this.posZ);
        if (UTConfigBugfixes.ENTITIES.utEntityAABBToggle && compound.hasKey("AABB", 9))
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTEntityBoundingBox ::: Read AABB from NBT");
            NBTTagList aabb_tag = compound.getTagList("AABB", 6);
            AxisAlignedBB aabb = new AxisAlignedBB(aabb_tag.getDoubleAt(0), aabb_tag.getDoubleAt(1), aabb_tag.getDoubleAt(2), aabb_tag.getDoubleAt(3), aabb_tag.getDoubleAt(4), aabb_tag.getDoubleAt(5));
            double deltaX = ((aabb.minX + aabb.maxX) / 2.0D) - this.posX;
            double deltaY = aabb.minY - this.posY;
            double deltaZ = ((aabb.minZ + aabb.maxZ) / 2.0D) - this.posZ;
            // If the position and AABB center point are > 0.1 blocks apart then do not restore the AABB. In vanilla
            // this should never happen, but mods might not be aware that the AABB is stored and that the entity
            // position will be reset to it. A good example of this is the ExtraUtils2 golden lasso.
            if (((deltaX * deltaX) + (deltaY * deltaY) + (deltaZ * deltaZ)) < 0.01D) this.setEntityBoundingBox(aabb);
        }
        return false;
    }

    @Shadow
    protected abstract NBTTagList newDoubleNBTList(double... numbers);

    @Shadow
    protected abstract boolean shouldSetPosAfterLoading();
}