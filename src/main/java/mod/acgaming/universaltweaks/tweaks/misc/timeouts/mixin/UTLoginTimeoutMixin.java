package mod.acgaming.universaltweaks.tweaks.misc.timeouts.mixin;

import net.minecraft.server.network.NetHandlerLoginServer;

import mod.acgaming.universaltweaks.tweaks.misc.timeouts.UTTimeoutManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

// Courtesy of jchung01, TheRandomLabs (RandomPatches)
@Mixin(value = NetHandlerLoginServer.class)
public class UTLoginTimeoutMixin
{
    @ModifyConstant(method = "update", constant = @Constant(intValue = 600))
    private int utModifyLoginTimeout(int original)
    {
        return UTTimeoutManager.loginTimeoutTicks;
    }
}
