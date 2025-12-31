package mod.acgaming.universaltweaks.mods.abyssalcraft.mixin;

import java.util.Map;

import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = RitualRegistry.class, remap = false)
public class UTRitualRegistryMixin {

    @Shadow
    @Final
    private Map<Integer, Integer> dimToBookType;

    @Shadow
    @Final
    private Map<Integer, Integer> configDimToBookType;

    /// java.lang.NullPointerException: Cannot invoke "java.lang.Integer.intValue()" because the return value of "java.util.Map.get(Object)" is null
    ///     at com.shinoow.abyssalcraft.api.ritual.RitualRegistry.sameBookType(RitualRegistry.java:152)
    ///     at com.shinoow.abyssalcraft.lib.util.RitualUtil.tryAltar(RitualUtil.java:120)
    ///     at com.shinoow.abyssalcraft.common.items.ItemNecronomicon.onItemUse(ItemNecronomicon.java:80)
    ///     at net.minecraft.item.ItemStack.onItemUse(ItemStack.java:191)
    ///     at net.minecraft.client.multiplayer.PlayerControllerMP.processRightClickBlock(PlayerControllerMP.java:477)
    ///     at net.minecraft.client.Minecraft.rightClickMouse(Minecraft.java:1559)
    ///     at net.minecraft.client.Minecraft.processKeyBinds(Minecraft.java:2238)
    ///     at net.minecraft.client.Minecraft.runTickKeyboard(Minecraft.java:2004)
    ///     at net.minecraft.client.Minecraft.runTick(Minecraft.java:1792)
    ///     at net.minecraft.client.Minecraft.runGameLoop(Minecraft.java:1078)
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
    @Inject(method = "sameBookType", at = @At("HEAD"), cancellable = true)
    private void ut$fixSameBookType(int dim, int bookType, CallbackInfoReturnable<Boolean> cir) {
        if (!this.dimToBookType.containsKey(dim) || !this.configDimToBookType.containsKey(dim)) {
            cir.cancel();
        }
    }

    /// I didn't verify that this one crashes but it has the same code as sameBookType so I'm sure it's also a problem
    @Inject(method = "canPerformAction", at = @At("HEAD"), cancellable = true)
    private void ut$fixCanPerformAction(int dim, int bookType, CallbackInfoReturnable<Boolean> cir) {
        if (!this.dimToBookType.containsKey(dim) || !this.configDimToBookType.containsKey(dim)) {
            cir.cancel();
        }
    }
}
