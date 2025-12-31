package mod.acgaming.universaltweaks.mods.incontrol.mixin;

import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mcjty.incontrol.ForgeEventHandlers;

@Mixin(value = ForgeEventHandlers.class, remap = false)
public class UTForgeEventHandlersMixin {

    /// java.util.concurrent.ExecutionException: java.lang.RuntimeException: A severe problem occurred during the spawning of an entity at (-92.86881406101907, 65.0, 469.4996410616231)
    ///     at java.util.concurrent.FutureTask.report(FutureTask.java:124)
    ///     at java.util.concurrent.FutureTask.get(FutureTask.java:193)
    ///     at net.minecraft.util.Util.runTask(SourceFile:531)
    ///     at net.minecraft.client.Minecraft.runGameLoop(Minecraft.java:1068)
    ///     at net.minecraft.client.Minecraft.run(Minecraft.java:5605)
    ///     at net.minecraft.client.main.Main.main(SourceFile:123)
    ///     at top.outlands.foundation.LaunchHandler.launch(LaunchHandler.java:125)
    ///     at top.outlands.foundation.boot.Foundation.main(Foundation.java:39)
    ///     at jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
    ///     at java.lang.reflect.Method.invoke(Method.java:565)
    ///     at org.polymc.impl.OneSixLauncher.invokeMain(OneSixLauncher.java:104)
    ///     at org.polymc.impl.OneSixLauncher.launchWithMainClass(OneSixLauncher.java:176)
    ///     at org.polymc.impl.OneSixLauncher.launch(OneSixLauncher.java:186)
    ///     at org.polymc.EntryPoint.listen(EntryPoint.java:144)
    ///     at org.polymc.EntryPoint.main(EntryPoint.java:74)
    /// Caused by: java.lang.RuntimeException: A severe problem occurred during the spawning of an entity at (-92.86881406101907, 65.0, 469.4996410616231)
    ///     at net.minecraftforge.fml.common.network.internal.EntitySpawnHandler.spawnEntity(EntitySpawnHandler.java:135)
    ///     at net.minecraftforge.fml.common.network.internal.EntitySpawnHandler.process(EntitySpawnHandler.java:64)
    ///     at net.minecraftforge.fml.common.network.internal.EntitySpawnHandler.lambda$channelRead0$0(EntitySpawnHandler.java:55)
    ///     at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:545)
    ///     at java.util.concurrent.FutureTask.run(FutureTask.java:328)
    ///     at net.minecraft.util.Util.runTask(SourceFile:529)
    ///     ... 12 more
    /// Caused by: java.lang.NullPointerException: Cannot read field "total" because "x0" is null
    ///     at mcjty.incontrol.rules.RuleCache$CountPerMod.access$008(RuleCache.java:96)
    ///     at mcjty.incontrol.rules.RuleCache$CachePerWorld.count(RuleCache.java:203)
    ///     at mcjty.incontrol.rules.RuleCache$CachePerWorld.registerSpawn(RuleCache.java:227)
    ///     at mcjty.incontrol.rules.RuleCache.registerSpawn(RuleCache.java:78)
    ///     at mcjty.incontrol.ForgeEventHandlers.onEntityJoinWorldLast(ForgeEventHandlers.java:63)
    ///     at net.minecraftforge.fml.common.eventhandler.ASMEventHandler.invoke(ASMEventHandler.java:78)
    ///     at net.minecraftforge.fml.common.eventhandler.EventBus.post(EventBus.java:212)
    ///     at net.minecraft.world.World.spawnEntity(World.java:1209)
    ///     at net.minecraft.client.multiplayer.WorldClient.spawnEntity(WorldClient.java:196)
    ///     at net.minecraft.client.multiplayer.WorldClient.addEntityToWorld(WorldClient.java:259)
    ///     at net.minecraftforge.fml.common.network.internal.EntitySpawnHandler.spawnEntity(EntitySpawnHandler.java:131)
    ///     ... 17 more
    @Inject(method = "onEntityJoinWorldLast", at = @At("HEAD"), cancellable = true)
    private void ut$fixOnEntityJoinWorldLast(EntityJoinWorldEvent event, CallbackInfo ci) {
        if (event.getWorld().isRemote) {
            ci.cancel();
        }
    }
}
