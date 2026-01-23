package mod.acgaming.universaltweaks.tweaks.misc.gui.modlist.mixin;

import java.util.Arrays;

import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.fml.client.GuiModList;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiModList.class)
public abstract class UTGuiModListMixin
{
    @Shadow(remap = false)
    private GuiTextField search;

    @Shadow(remap = false)
    private String lastFilterText;

    @Unique
    private static String savedFilterText = "";

    @Inject(method = "initGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiTextField;setCanLoseFocus(Z)V", shift = At.Shift.AFTER))
    public void utInitGuiModList(CallbackInfo ci)
    {
        search.setText(savedFilterText);
    }

    @Redirect(method = "reloadMods", at = @At(value = "INVOKE", target = "Ljava/lang/String;contains(Ljava/lang/CharSequence;)Z"), remap = false)
    public boolean utReloadMods(String string, CharSequence charSequence)
    {
        savedFilterText = lastFilterText;

        if (charSequence.toString().contains("|"))
        {
            String[] splits = charSequence.toString().split("\\|");
            return Arrays.stream(splits).map(String::trim).anyMatch(string::contains);
        }
        return string.contains(charSequence);
    }
}