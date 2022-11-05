package mod.acgaming.universaltweaks.bugfixes.entitytracker.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

import mod.acgaming.universaltweaks.bugfixes.entitytracker.IWorldServer;
import org.spongepowered.asm.mixin.Mixin;

// MC-92916
// https://bugs.mojang.com/browse/MC-92916
// Courtesy of mrgrim
@Mixin(WorldServer.class)
public abstract class UTWorldServerMixin extends World implements IWorldServer
{
    public UTWorldServerMixin(MinecraftServer server, ISaveHandler saveHandlerIn, WorldInfo info, int dimensionId, Profiler profilerIn)
    {
        super(saveHandlerIn, info, DimensionType.getById(dimensionId).createDimension(), profilerIn, false);
    }

    public void prepareLeaveDimension(Entity entityIn)
    {
        entityIn.lastTickPosX = entityIn.posX;
        entityIn.lastTickPosY = entityIn.posY;
        entityIn.lastTickPosZ = entityIn.posZ;
        entityIn.prevRotationYaw = entityIn.rotationYaw;
        entityIn.prevRotationPitch = entityIn.rotationPitch;
    }
}