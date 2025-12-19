package mod.acgaming.universaltweaks.mods.bibliocraft.version.mixin;

import jds.bibliocraft.BiblioCraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of jchung01
@Mixin(value = BiblioCraft.class, remap = false)
public class UTBiblioCraftMixin
{
    /**
     * @reason Skip version checking because it retains the first WorldClient
     */
    @Inject(method = "postInitClient", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/common/eventhandler/EventBus;register(Ljava/lang/Object;)V", ordinal = 0, shift = At.Shift.AFTER), cancellable = true)
    private void utDisableVersionCheck(CallbackInfo ci)
    {
        ci.cancel();
    }
}
