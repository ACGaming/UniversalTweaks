package mod.acgaming.universaltweaks.bugfixes.misc.particlespawning.mixin;

import java.util.Objects;

import net.minecraft.world.ServerWorldEventHandler;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

// MC-10369, MC-93826
// https://bugs.mojang.com/browse/MC-10369
// https://bugs.mojang.com/browse/MC-93826
// Courtesy of Fuzs, TheRandomLabs, fonnymunkey
@Mixin(ServerWorldEventHandler.class)
public interface UTServerWorldEventHandlerAccessor
{
    @Accessor("world")
    WorldServer getWorld();
}