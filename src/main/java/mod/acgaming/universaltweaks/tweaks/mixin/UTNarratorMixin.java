package mod.acgaming.universaltweaks.tweaks.mixin;

import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NarratorChatListener.class)
public class UTNarratorMixin
{
    @Inject(method = "say", at = @At("HEAD"), cancellable = true)
    public void utNarratorSay(ChatType chatTypeIn, ITextComponent message, CallbackInfo ci)
    {
        if (UTConfig.TWEAKS_MISC.utDisableNarratorToggle) ci.cancel();
    }

    @Inject(method = "announceMode", at = @At("HEAD"), cancellable = true)
    public void utNarratorAnnounceMode(int p_193641_1_, CallbackInfo ci)
    {
        if (UTConfig.TWEAKS_MISC.utDisableNarratorToggle) ci.cancel();
    }

    @Inject(method = "isActive", at = @At("RETURN"), cancellable = true)
    public void utNarratorState(CallbackInfoReturnable<Boolean> cir)
    {
        if (UTConfig.TWEAKS_MISC.utDisableNarratorToggle) cir.setReturnValue(false);
    }
}