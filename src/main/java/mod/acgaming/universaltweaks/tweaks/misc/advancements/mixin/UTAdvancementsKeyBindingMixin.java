package mod.acgaming.universaltweaks.tweaks.misc.advancements.mixin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameSettings.class)
public abstract class UTAdvancementsKeyBindingMixin
{
    @Shadow
    public KeyBinding[] keyBindings;

    @Inject(method = "<init>()V", at = @At("TAIL"))
    public void utAdvancementsKeyBinding1(CallbackInfo ci)
    {
        if (UTConfigTweaks.MISC.utDisableAdvancementsToggle)
        {
            ut$removeAdvancementsButton();
        }
    }

    @Inject(method = "<init>(Lnet/minecraft/client/Minecraft;Ljava/io/File;)V", at = @At("TAIL"))
    public void utAdvancementsKeyBinding2(Minecraft mc, File mcDataDir, CallbackInfo ci)
    {
        if (UTConfigTweaks.MISC.utDisableAdvancementsToggle)
        {
            ut$removeAdvancementsButton();
        }
    }

    @Unique
    private void ut$removeAdvancementsButton()
    {
        List<KeyBinding> keyBindingsNew = new ArrayList<>();
        for (KeyBinding keyBinding : this.keyBindings)
        {
            if (!keyBinding.getKeyDescription().equals("key.advancements")) keyBindingsNew.add(keyBinding);
        }
        this.keyBindings = keyBindingsNew.toArray(new KeyBinding[0]);
    }
}
