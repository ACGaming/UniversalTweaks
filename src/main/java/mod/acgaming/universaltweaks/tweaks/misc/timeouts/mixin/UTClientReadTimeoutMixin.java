package mod.acgaming.universaltweaks.tweaks.misc.timeouts.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

// Courtesy of jchung01, TheRandomLabs (RandomPatches)
@Mixin(targets = "net.minecraft.network.NetworkManager$5")
public class UTClientReadTimeoutMixin
{
    @SuppressWarnings("UnresolvedMixinReference")
    @ModifyConstant(method = "initChannel", constant = @Constant(intValue = 30))
    private int utModifyReadTimeout(int original)
    {
        return 90;
    }
}
