package mod.acgaming.universaltweaks.bugfixes.entitytracker;

import net.minecraft.entity.Entity;

// MC-92916
// https://bugs.mojang.com/browse/MC-92916
// Courtesy of mrgrim
public interface IWorldServer
{
    void prepareLeaveDimension(Entity entityIn);
}