package mod.acgaming.universaltweaks.tweaks.world.voidfog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.tweaks.world.voidfog.UTVoidFog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.init.MobEffects;
import net.minecraft.world.WorldType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class UTVoidFogMixin
{
    @Shadow @Final private Minecraft mc;
    @Unique private boolean ut$shouldRender;

    /**
     * Injects ported 1.7.10 void fog code into the "setupFog" method of the EntityRenderer class.
     *
     * @param startCoords  starting coordinates of fog effects
     * @param partialTicks partial ticks for fog effects
     * @param ci           CallbackInfo object
     * @param f1           float reference for the far plane distance
     */
    @Inject(method = "setupFog", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;setFog(Lnet/minecraft/client/renderer/GlStateManager$FogMode;)V", ordinal = 4))
    public void utVoidFog(int startCoords, float partialTicks, CallbackInfo ci, @Local(ordinal = 2) LocalFloatRef f1)
    {
        // Only update conditions every second
        if (this.mc.player.ticksExisted < 20 || this.mc.player.ticksExisted % 20 == 19)
        {
            ut$shouldRender = false;
            if (!UTVoidFog.isEnabledForDimension(this.mc.player.dimension)) return;
            if (!UTConfigTweaks.WORLD.VOID_FOG.utVoidFogSuperflat && this.mc.world.getWorldInfo().getTerrainType() == WorldType.FLAT) return;
            if (!UTConfigTweaks.WORLD.VOID_FOG.utVoidFogCreativeSpectator && (this.mc.player.isCreative() || this.mc.player.isSpectator())) return;
            if (!UTConfigTweaks.WORLD.VOID_FOG.utVoidFogNightVision && this.mc.player.isPotionActive(MobEffects.NIGHT_VISION)) return;
            ut$shouldRender = true;
        }

        if (!ut$shouldRender) return;

        double d0 = (double) ((this.mc.getRenderViewEntity().getBrightnessForRender() & 15728640) >> 20) / 16.0D + (this.mc.getRenderViewEntity().lastTickPosY + (this.mc.getRenderViewEntity().posY - this.mc.getRenderViewEntity().lastTickPosY) * (double) partialTicks + 4.0D) / 32.0D;

        if (d0 < 1.0D)
        {
            if (d0 < 0.0D)
            {
                d0 = 0.0D;
            }

            d0 *= d0;
            float f2 = 100.0F * (float) d0;

            if (f2 < 5.0F)
            {
                f2 = 5.0F;
            }

            if (f1.get() > f2)
            {
                f1.set(f2);
            }
        }
    }
}
