package mod.acgaming.universaltweaks.mods.bloodmagic.mixin;

import java.util.Map;

import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;

import WayofTime.bloodmagic.ritual.AreaDescriptor;
import WayofTime.bloodmagic.ritual.IMasterRitualStone;
import WayofTime.bloodmagic.util.handler.event.GenericHandler;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

// Courtesy of michaelPoul
@Mixin(value = GenericHandler.class, remap = false)
public class UTGenericHandlerMixin
{
    @Shadow
    public static Map<World, Map<IMasterRitualStone, AreaDescriptor>> forceSpawnMap;
    @Shadow
    public static Map<World, Map<IMasterRitualStone, AreaDescriptor>> preventSpawnMap;

    @Inject(method = "onWorldUnload", at = @At(value = "TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void utRemoveRemainingWorld(WorldEvent.Unload event, CallbackInfo ci, World world)
    {
        if (!UTConfigMods.BLOOD_MAGIC.utBMWorldUnloadToggle) return;
        forceSpawnMap.remove(world);
        preventSpawnMap.remove(world);
    }
}
