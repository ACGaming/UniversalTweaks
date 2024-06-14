package mod.acgaming.universaltweaks.mods.tconstruct.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slimeknights.tconstruct.common.ClientProxy;

// Courtesy of jchung01
@Mixin(value = ClientProxy.class, remap = false)
public class UTClientProxyMixin
{
    /**
     * @reason It is never correct to use passed worlds for particles, only use the client's.
     * Ideally we would check sides in the relevant methods before even calling this,
     * but this is simpler.
     */
    @Inject(method = "spawnParticle", at = @At(value = "HEAD"))
    private void utSetWorld(CallbackInfo ci, @Local(argsOnly = true) LocalRef<World> worldRef)
    {
        worldRef.set(Minecraft.getMinecraft().world);
    }
}
