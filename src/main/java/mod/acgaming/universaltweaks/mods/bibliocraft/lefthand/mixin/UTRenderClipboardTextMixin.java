package mod.acgaming.universaltweaks.mods.bibliocraft.lefthand.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.client.event.RenderSpecificHandEvent;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import jds.bibliocraft.events.RenderClipboardText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(value = RenderClipboardText.class, remap = false)
public class UTRenderClipboardTextMixin
{
    @Definition(id = "event", local = @Local(type = RenderSpecificHandEvent.class, argsOnly = true))
    @Definition(id = "getHand", method = "Lnet/minecraftforge/client/event/RenderSpecificHandEvent;getHand()Lnet/minecraft/util/EnumHand;")
    @Definition(id = "MAIN_HAND", field = "Lnet/minecraft/util/EnumHand;MAIN_HAND:Lnet/minecraft/util/EnumHand;")
    @Expression("event.getHand() == MAIN_HAND")
    @ModifyExpressionValue(method = "render", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean utEnsureProperHand(boolean original)
    {
        boolean isRightHanded = Minecraft.getMinecraft().player.getPrimaryHand() == EnumHandSide.RIGHT;
        return original == isRightHanded;
    }
}
