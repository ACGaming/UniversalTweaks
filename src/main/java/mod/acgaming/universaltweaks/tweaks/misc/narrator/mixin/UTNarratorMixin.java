package mod.acgaming.universaltweaks.tweaks.misc.narrator.mixin;

import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.text2speech.Narrator;
import com.mojang.text2speech.NarratorDummy;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NarratorChatListener.class)
public class UTNarratorMixin
{
    /**
     * @author WaitingIdly
     * @reason disable the initialization of the narrator and the narrator thread by redirecting to use {@link NarratorDummy}
     */
    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/mojang/text2speech/Narrator;getNarrator()Lcom/mojang/text2speech/Narrator;", remap = false))
    private Narrator utUseDummyNarrator(Operation<Narrator> original)
    {
        if (!UTConfigTweaks.MISC.utDisableNarratorToggle) return original.call();
        return new NarratorDummy();
    }

    @Inject(method = "say", at = @At("HEAD"), cancellable = true)
    public void utNarratorSay(ChatType chatTypeIn, ITextComponent message, CallbackInfo ci)
    {
        if (UTConfigTweaks.MISC.utDisableNarratorToggle) ci.cancel();
    }

    @Inject(method = "announceMode", at = @At("HEAD"), cancellable = true)
    public void utNarratorAnnounceMode(int p_193641_1_, CallbackInfo ci)
    {
        if (UTConfigTweaks.MISC.utDisableNarratorToggle) ci.cancel();
    }

    @Inject(method = "isActive", at = @At("RETURN"), cancellable = true)
    public void utNarratorState(CallbackInfoReturnable<Boolean> cir)
    {
        if (UTConfigTweaks.MISC.utDisableNarratorToggle) cir.setReturnValue(false);
    }
}