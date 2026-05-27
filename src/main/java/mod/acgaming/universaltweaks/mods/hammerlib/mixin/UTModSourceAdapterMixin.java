package mod.acgaming.universaltweaks.mods.hammerlib.mixin;

import java.util.ArrayList;
import java.util.List;

import com.zeitheron.hammercore.utils.java.io.win32.ModSourceAdapter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of WaitingIdly
@Mixin(value = ModSourceAdapter.class, remap = false)
public abstract class UTModSourceAdapterMixin
{
    @Mutable
    @Shadow
    @Final
    public static List<ModSourceAdapter.IllegalSite> ILLEGAL_SITES;

    /**
     * @author WaitingIdly
     * @reason Prevent timeouts when attempting to load the url
     * from a location that has it blocked.
     */
    @Inject(method = "<clinit>", at = @At("HEAD"), cancellable = true)
    private static void utSkipURLCheck(CallbackInfo ci)
    {
        ILLEGAL_SITES = new ArrayList<>();
        ci.cancel();
    }
}
