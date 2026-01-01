package mod.acgaming.universaltweaks.mods.xnet.mixin;

import net.minecraft.entity.player.EntityPlayerMP;

import com.llamalad7.mixinextras.sugar.Local;
import mcjty.lib.typed.TypedMap;
import mcjty.xnet.api.keys.SidedPos;
import mcjty.xnet.blocks.controller.TileEntityController;
import mcjty.xnet.logic.ChannelInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = TileEntityController.class, remap = false)
public class UTTileEntityControllerMixin {

    @Shadow
    @Final
    private ChannelInfo[] channels;

    /// java.util.concurrent.ExecutionException: java.lang.ArrayIndexOutOfBoundsException: Index -1 out of bounds for length 8
    ///     at java.util.concurrent.FutureTask.report(FutureTask.java:124)
    ///     at java.util.concurrent.FutureTask.get(FutureTask.java:193)
    ///     at net.minecraft.util.Util.runTask(SourceFile:531)
    ///     at net.minecraft.server.MinecraftServer.updateTimeLightAndEntities(MinecraftServer.java:723)
    ///     at net.minecraft.server.MinecraftServer.tick(MinecraftServer.java:668)
    ///     at net.minecraft.server.integrated.IntegratedServer.tick(IntegratedServer.java:185)
    ///     at net.minecraft.server.MinecraftServer.run(MinecraftServer.java:526)
    ///     at java.lang.Thread.run(Thread.java:1474)
    /// Caused by: java.lang.ArrayIndexOutOfBoundsException: Index -1 out of bounds for length 8
    ///     at mcjty.xnet.blocks.controller.TileEntityController.createChannel(TileEntityController.java:541)
    ///     at mcjty.xnet.blocks.controller.TileEntityController.execute(TileEntityController.java:999)
    ///     at mcjty.lib.network.PacketServerCommandTyped.lambda$handle$0(PacketServerCommandTyped.java:93)
    ///     at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:545)
    ///     at java.util.concurrent.FutureTask.run(FutureTask.java:328)
    ///     at net.minecraft.util.Util.runTask(SourceFile:529)
    ///     ... 5 more
    @Inject(method = "execute", at = @At(value = "INVOKE", target = "Lmcjty/xnet/blocks/controller/TileEntityController;createChannel(ILjava/lang/String;)V"), cancellable = true)
    private void ut$fixCreateChannel(EntityPlayerMP playerMP, String command, TypedMap params, CallbackInfoReturnable<Boolean> cir, @Local(type = int.class) int index) {
        if (index < 0 || index >= this.channels.length) {
            cir.setReturnValue(false);
        }
    }

    /// java.util.concurrent.ExecutionException: java.lang.NullPointerException: Cannot invoke "mcjty.xnet.logic.ChannelInfo.getType()" because "this.channels[channel]" is null
    ///     at java.util.concurrent.FutureTask.report(FutureTask.java:124)
    ///     at java.util.concurrent.FutureTask.get(FutureTask.java:193)
    ///     at net.minecraft.util.Util.runTask(SourceFile:531)
    ///     at net.minecraft.server.MinecraftServer.updateTimeLightAndEntities(MinecraftServer.java:723)
    ///     at net.minecraft.server.MinecraftServer.tick(MinecraftServer.java:668)
    ///     at net.minecraft.server.integrated.IntegratedServer.tick(IntegratedServer.java:185)
    ///     at net.minecraft.server.MinecraftServer.run(MinecraftServer.java:526)
    ///     at java.lang.Thread.run(Thread.java:1474)
    /// Caused by: java.lang.NullPointerException: Cannot invoke "mcjty.xnet.logic.ChannelInfo.getType()" because "this.channels[channel]" is null
    ///     at mcjty.xnet.blocks.controller.TileEntityController.pasteConnector(TileEntityController.java:782)
    ///     at mcjty.xnet.blocks.controller.TileEntityController.execute(TileEntityController.java:1010)
    ///     at mcjty.lib.network.PacketServerCommandTyped.lambda$handle$0(PacketServerCommandTyped.java:93)
    ///     at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:545)
    ///     at java.util.concurrent.FutureTask.run(FutureTask.java:328)
    ///     at net.minecraft.util.Util.runTask(SourceFile:529)
    ///     ... 5 more
    @Inject(method = "pasteConnector", at = @At("HEAD"), cancellable = true)
    private void ut$fixPasteConnector(EntityPlayerMP player, int channel, SidedPos sidedPos, String json, CallbackInfo ci) {
        if (channel < 0 || channel >= this.channels.length || this.channels[channel] == null) {
            ci.cancel();
        }
    }

    /// java.util.concurrent.ExecutionException: java.lang.ArrayIndexOutOfBoundsException: Index -1 out of bounds for length 8
    ///     at java.util.concurrent.FutureTask.report(FutureTask.java:124)
    ///     at java.util.concurrent.FutureTask.get(FutureTask.java:193)
    ///     at net.minecraft.util.Util.runTask(SourceFile:531)
    ///     at net.minecraft.server.MinecraftServer.updateTimeLightAndEntities(MinecraftServer.java:723)
    ///     at net.minecraft.server.MinecraftServer.tick(MinecraftServer.java:668)
    ///     at net.minecraft.server.integrated.IntegratedServer.tick(IntegratedServer.java:185)
    ///     at net.minecraft.server.MinecraftServer.run(MinecraftServer.java:526)
    ///     at java.lang.Thread.run(Thread.java:1474)
    /// Caused by: java.lang.ArrayIndexOutOfBoundsException: Index -1 out of bounds for length 8
    ///     at mcjty.xnet.blocks.controller.TileEntityController.removeChannel(TileEntityController.java:533)
    ///     at mcjty.xnet.blocks.controller.TileEntityController.execute(TileEntityController.java:1028)
    ///     at mcjty.lib.network.PacketServerCommandTyped.lambda$handle$0(PacketServerCommandTyped.java:93)
    ///     at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:545)
    ///     at java.util.concurrent.FutureTask.run(FutureTask.java:328)
    ///     at net.minecraft.util.Util.runTask(SourceFile:529)
    ///     ... 5 more
    @Inject(method = "removeChannel", at = @At("HEAD"), cancellable = true)
    private void ut$fixRemoveChannel(int channel, CallbackInfo ci) {
        if (channel < 0 || channel >= this.channels.length) {
            ci.cancel();
        }
    }

    /// java.util.concurrent.ExecutionException: java.lang.ArrayIndexOutOfBoundsException: Index -1 out of bounds for length 8
    ///     at java.util.concurrent.FutureTask.report(FutureTask.java:124)
    ///     at java.util.concurrent.FutureTask.get(FutureTask.java:193)
    ///     at net.minecraft.util.Util.runTask(SourceFile:531)
    ///     at net.minecraft.server.MinecraftServer.updateTimeLightAndEntities(MinecraftServer.java:723)
    ///     at net.minecraft.server.MinecraftServer.tick(MinecraftServer.java:668)
    ///     at net.minecraft.server.integrated.IntegratedServer.tick(IntegratedServer.java:185)
    ///     at net.minecraft.server.MinecraftServer.run(MinecraftServer.java:526)
    ///     at java.lang.Thread.run(Thread.java:1474)
    /// Caused by: java.lang.ArrayIndexOutOfBoundsException: Index -1 out of bounds for length 8
    ///     at mcjty.xnet.blocks.controller.TileEntityController.copyChannel(TileEntityController.java:667)
    ///     at mcjty.xnet.blocks.controller.TileEntityController.execute(TileEntityController.java:1014)
    ///     at mcjty.lib.network.PacketServerCommandTyped.lambda$handle$0(PacketServerCommandTyped.java:93)
    ///     at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:545)
    ///     at java.util.concurrent.FutureTask.run(FutureTask.java:328)
    ///     at net.minecraft.util.Util.runTask(SourceFile:529)
    ///     ... 5 more
    @Inject(method = "copyChannel", at = @At("HEAD"), cancellable = true)
    private void ut$fixCopyChannel(EntityPlayerMP player, int index, CallbackInfo ci) {
        if (index < 0 || index >= this.channels.length) {
            ci.cancel();
        }
    }
}
