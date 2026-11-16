package mod.acgaming.universaltweaks.bugfixes.world.tick.mixin;

import java.util.Set;

import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.WorldServer;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldServer.class)
public abstract class UTTickNextTickListMixin
{
    @Mutable
    @Shadow
    @Final
    private Set<NextTickListEntry> pendingTickListEntriesHashSet;

    @Redirect(method = "<init>", at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lnet/minecraft/world/WorldServer;pendingTickListEntriesHashSet:Ljava/util/Set;"))
    private void utTickNextTickList(WorldServer world, Set<NextTickListEntry> set)
    {
        this.pendingTickListEntriesHashSet = new ObjectOpenHashSet<>();
    }
}
