package mod.acgaming.universaltweaks.mods.divinerpg.armorset.mixin;

import java.lang.ref.WeakReference;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

import divinerpg.capabilities.armor.PlayerForgeEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of jchung01
@Mixin(value = PlayerForgeEvent.class, remap = false)
public abstract class UTPlayerForgeEventMixin
{
    @Shadow
    @Final
    private WeakReference<EntityLivingBase> player;

    @Inject(method = "subscribe", at = @At("HEAD"), cancellable = true)
    private void ut$cancelClientRegister(CallbackInfo ci)
    {
        if (player == null)
        {
            ci.cancel();
            return;
        }
        EntityLivingBase playerObj = player.get();
        if (playerObj == null)
        {
            ci.cancel();
            return;
        }
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        // Prevent registering duplicate handler for integrated servers on client-side
        if (playerObj.world.isRemote && server != null && !server.isDedicatedServer())
        {
            ci.cancel();
        }
    }
}
