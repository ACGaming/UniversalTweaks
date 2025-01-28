package mod.acgaming.universaltweaks.tweaks.misc.recipebook.compat.mixin;

import java.util.List;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import thebetweenlands.client.gui.inventory.GuiWeedwoodWorkbench;

@Mixin(GuiWeedwoodWorkbench.class)
public abstract class UTRecipeBookBetweenlandsCompatMixin
{
    @WrapWithCondition(method = "initGui", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
    public boolean utHideRecipeBookWeedwoodWorkbench(List instance, Object e)
    {
        return !UTConfigTweaks.MISC.utRecipeBookToggle;
    }
}