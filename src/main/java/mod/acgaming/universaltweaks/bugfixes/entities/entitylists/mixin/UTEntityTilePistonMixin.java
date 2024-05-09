package mod.acgaming.universaltweaks.bugfixes.entities.entitylists.mixin;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;

import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Surrogate;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

// MC-108469
// https://bugs.mojang.com/browse/MC-108469
// Courtesy of mrgrim
@Mixin(TileEntityPiston.class)
public abstract class UTEntityTilePistonMixin extends TileEntity
{
    @Inject(method = "moveCollidedEntities", at = @At(value = "INVOKE", target = "Ljava/lang/ThreadLocal;set(Ljava/lang/Object;)V", ordinal = 1, shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
    public void utUpdateEntity(float p_184322_1_, CallbackInfo ci, EnumFacing enumfacing, double d0, List list, AxisAlignedBB axisalignedbb, List list1, boolean flag, int i, Entity entity, double d1)
    {
        if (UTConfigBugfixes.ENTITIES.ENTITY_LISTS.utChunkUpdatesToggle) world.updateEntityWithOptionalForce(entity, false);
    }

    @Surrogate
    public void utUpdateEntity(float p_184322_1_, CallbackInfo ci, EnumFacing enumfacing, double d0, List list, AxisAlignedBB axisalignedbb, List list1, boolean flag, int i, Entity entity, double d1, int quark0)
    {
        if (UTConfigBugfixes.ENTITIES.ENTITY_LISTS.utChunkUpdatesToggle) world.updateEntityWithOptionalForce(entity, false);
    }
}