package mod.acgaming.universaltweaks.mods.rftools.mixin;

import net.minecraft.item.ItemStack;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import mcjty.lib.varia.ItemStackList;
import mcjty.rftools.compat.xnet.StorageConnectorSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StorageConnectorSettings.class)
public class UTStorageConnectorSettingsMixin {

    /// java.util.concurrent.ExecutionException: java.lang.NullPointerException: The validated object is null
    ///     at java.util.concurrent.FutureTask.report(FutureTask.java:124)
    ///     at java.util.concurrent.FutureTask.get(FutureTask.java:193)
    ///     at net.minecraft.util.Util.runTask(SourceFile:531)
    ///     at net.minecraft.server.MinecraftServer.updateTimeLightAndEntities(MinecraftServer.java:723)
    ///     at net.minecraft.server.MinecraftServer.tick(MinecraftServer.java:668)
    ///     at net.minecraft.server.integrated.IntegratedServer.tick(IntegratedServer.java:185)
    ///     at net.minecraft.server.MinecraftServer.run(MinecraftServer.java:526)
    ///     at java.lang.Thread.run(Thread.java:1474)
    /// Caused by: java.lang.NullPointerException: The validated object is null
    ///     at java.util.Objects.requireNonNull(Objects.java:340)
    ///     at org.apache.commons.lang3.Validate.notNull(Validate.java:1063)
    ///     at org.apache.commons.lang3.Validate.notNull(Validate.java:1045)
    ///     at net.minecraft.util.NonNullList.set(SourceFile:49)
    ///     at mcjty.rftools.compat.xnet.StorageConnectorSettings.update(StorageConnectorSettings.java:191)
    ///     at mcjty.xnet.blocks.controller.TileEntityController.updateConnector(TileEntityController.java:555)
    ///     at mcjty.xnet.blocks.controller.TileEntityController.execute(TileEntityController.java:1038)
    ///     at mcjty.lib.network.PacketServerCommandTyped.lambda$handle$0(PacketServerCommandTyped.java:93)
    ///     at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:545)
    ///     at java.util.concurrent.FutureTask.run(FutureTask.java:328)
    ///     at net.minecraft.util.Util.runTask(SourceFile:529)
    ///     ... 5 more
    @WrapOperation(method = "update", at = @At(value = "INVOKE", target = "Lmcjty/lib/varia/ItemStackList;set(ILjava/lang/Object;)Ljava/lang/Object;"))
    private Object ut$fixUpdate(ItemStackList instance, int i, Object o, Operation<Object> original) {
        return original.call(instance, i, o == null ? ItemStack.EMPTY : o);
    }
}
