package mod.acgaming.universaltweaks.tweaks.misc.viewbobbing.mixin;

import net.minecraft.client.renderer.EntityRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityRenderer.class)
public interface UTApplyBobbingInvoker
{
    @Invoker("applyBobbing")
    void applyBobbing(float partialTicks);
}