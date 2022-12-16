package mod.acgaming.universaltweaks.bugfixes.disconnectdupe.mixin;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.PlayerList;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PlayerList.class)
public interface PlayerListInvoker
{
    @Invoker("writePlayerData")
    void invokeWritePlayerData(EntityPlayerMP playerIn);
}