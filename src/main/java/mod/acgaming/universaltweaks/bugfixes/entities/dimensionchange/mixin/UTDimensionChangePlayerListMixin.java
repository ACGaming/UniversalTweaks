package mod.acgaming.universaltweaks.bugfixes.entities.dimensionchange.mixin;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketServerDifficulty;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraftforge.common.util.ITeleporter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// MC-44471
// https://bugs.mojang.com/browse/MC-44471
@Mixin(PlayerList.class)
public class UTDimensionChangePlayerListMixin
{
    @Shadow
    @Final
    private MinecraftServer server;

    @Inject(method = "transferPlayerToDimension(Lnet/minecraft/entity/player/EntityPlayerMP;ILnet/minecraftforge/common/util/ITeleporter;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/management/PlayerList;updatePermissionLevel(Lnet/minecraft/entity/player/EntityPlayerMP;)V"))
    public void utTransferPlayerToDimension(EntityPlayerMP player, int dimensionIn, ITeleporter teleporter, CallbackInfo ci)
    {
        if (!UTConfigBugfixes.ENTITIES.utDimensionChangeToggle) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTDimensionChangePlayerList ::: Change dimension");
        player.connection.sendPacket(new SPacketServerDifficulty(this.server.getWorld(player.dimension).getWorldInfo().getDifficulty(), this.server.getWorld(player.dimension).getWorldInfo().isDifficultyLocked()));
    }
}