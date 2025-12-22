package mod.acgaming.universaltweaks.mods.bibliocraft.lefthand.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.client.event.RenderSpecificHandEvent;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import jds.bibliocraft.events.RenderAtlasFace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of WaitingIdly
@Mixin(value = RenderAtlasFace.class, remap = false)
public class UTRenderAtlasFaceMixin
{
    @Inject(method = "renderItem", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/event/RenderSpecificHandEvent;getSwingProgress()F"))
    private void utAdjustForLeftHand(RenderSpecificHandEvent event, CallbackInfo ci, @Local(ordinal = 1) LocalBooleanRef isSmallLeft, @Local(ordinal = 2) LocalBooleanRef isSmallRight)
    {
        if (Minecraft.getMinecraft().player.getPrimaryHand() == EnumHandSide.RIGHT) return;
        boolean swap = isSmallLeft.get();
        isSmallLeft.set(isSmallRight.get());
        isSmallRight.set(swap);
    }
}
