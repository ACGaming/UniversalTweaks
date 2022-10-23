package mod.acgaming.universaltweaks.bugfixes.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.util.Constants;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// MC-2025
// https://bugs.mojang.com/browse/MC-2025
@Mixin(Entity.class)
public abstract class UTEntityAABBMixin
{
    @Shadow
    public abstract AxisAlignedBB getEntityBoundingBox();

    @Shadow
    public abstract void setEntityBoundingBox(AxisAlignedBB bb);

    @Inject(method = "writeToNBT", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NBTTagCompound;setTag(Ljava/lang/String;Lnet/minecraft/nbt/NBTBase;)V", ordinal = 2))
    public void utWriteAABBToNBT(NBTTagCompound compound, CallbackInfoReturnable<NBTTagCompound> cir)
    {
        AxisAlignedBB aabb = this.getEntityBoundingBox();
        if (aabb != null) compound.setTag("AABB", newDoubleNBTList(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ));
    }

    @Inject(method = "readFromNBT", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setRotation(FF)V"))
    public void utReadAABBFromNBT(NBTTagCompound compound, CallbackInfo ci)
    {
        if (compound.hasKey("AABB"))
        {
            NBTTagList aabbNbt = compound.getTagList("AABB", Constants.NBT.TAG_DOUBLE);
            this.setEntityBoundingBox(new AxisAlignedBB(aabbNbt.getDoubleAt(0), aabbNbt.getDoubleAt(1), aabbNbt.getDoubleAt(2), aabbNbt.getDoubleAt(3), aabbNbt.getDoubleAt(4), aabbNbt.getDoubleAt(5)));
        }
    }

    @Shadow
    protected abstract NBTTagList newDoubleNBTList(double... numbers);
}