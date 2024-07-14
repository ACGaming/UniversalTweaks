package mod.acgaming.universaltweaks.bugfixes.entities.tracker.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.server.management.PlayerList;
import net.minecraft.world.WorldServer;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.bugfixes.entities.tracker.IWorldServer;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

// MC-92916
// https://bugs.mojang.com/browse/MC-92916
// Courtesy of mrgrim
@Mixin(PlayerList.class)
public abstract class UTPlayerListMixin
{
    // This is enough for vanilla
    @Redirect(method = "transferEntityToWorld(Lnet/minecraft/entity/Entity;ILnet/minecraft/world/WorldServer;Lnet/minecraft/world/WorldServer;Lnet/minecraftforge/common/util/ITeleporter;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldServer;updateEntityWithOptionalForce(Lnet/minecraft/entity/Entity;Z)V"), slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=moving"), to = @At(value = "CONSTANT", args = "stringValue=placing")))
    public void utDoPrepareLeaveDimension(WorldServer worldIn, Entity entityIn, boolean forceUpdate)
    {
        ut$prepareLeaveDimension(worldIn, entityIn, forceUpdate);
    }

    // This is needed for Forge
    @Redirect(method = "transferEntityToWorld(Lnet/minecraft/entity/Entity;ILnet/minecraft/world/WorldServer;Lnet/minecraft/world/WorldServer;Lnet/minecraftforge/common/util/ITeleporter;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldServer;updateEntityWithOptionalForce(Lnet/minecraft/entity/Entity;Z)V", ordinal = 0), slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=placing"), to = @At(value = "INVOKE", target = "Lnet/minecraftforge/common/util/ITeleporter;placeEntity(Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;F)V", remap = false)))
    public void utDoPrepareLeaveDimensionForge(WorldServer worldIn, Entity entityIn, boolean forceUpdate)
    {
        ut$prepareLeaveDimension(worldIn, entityIn, forceUpdate);
    }

    @Unique
    public void ut$prepareLeaveDimension(WorldServer worldIn, Entity entityIn, boolean forceUpdate)
    {
        if (UTConfigBugfixes.ENTITIES.utEntityTrackerToggle)
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTPlayerListMixin ::: Prepare leave dimension");
            try
            {
                ((IWorldServer) worldIn).prepareLeaveDimension(entityIn);
            }
            catch (Exception e)
            {
                worldIn.updateEntityWithOptionalForce(entityIn, forceUpdate);
            }
        }
        else
        {
            worldIn.updateEntityWithOptionalForce(entityIn, forceUpdate);
        }
    }
}