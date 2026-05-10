package mod.acgaming.universaltweaks.mods.hammerlib.mixin;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.ItemStack;

import com.zeitheron.hammercore.api.items.IRenderAwareItem;
import com.zeitheron.hammercore.client.render.item.ItemRenderingHandler;
import com.zeitheron.hammercore.client.render.shader.GlShaderStack;
import com.zeitheron.hammercore.client.utils.ItemColorHelper;
import mod.acgaming.universaltweaks.mods.hammerlib.QuarkCompat;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of jchung01
@Mixin(value = ItemColorHelper.class, remap = false)
public abstract class UTItemColorHelperMixin
{
    @Shadow
    @Final
    public static int DEFAULT_GLINT_COLOR;
    @Shadow
    static ItemStack target;

    @Shadow
    public static int getColorFromStack(ItemStack stack, int prev) {
        return 0;
    }

    /**
     * @reason The current shader program is queried by {@link GlShaderStack} for EVERY ItemStack on screen, EVERY frame, which is super slow.
     */
    @Inject(method = "renderItemModelIntoGUIPre", at = @At("HEAD"), cancellable = true)
    private static void utSkipPushShader(ItemStack stack, int x, int y, IBakedModel bakedmodel, CallbackInfo ci)
    {
        if ((stack.isEmpty() || ItemRenderingHandler.INSTANCE.getRenderHooks(stack.getItem()).isEmpty())
                && IRenderAwareItem.get(stack) == null) {
            ci.cancel();
        }
    }

    @Inject(method = "renderItemModelIntoGUIPost", at = @At("HEAD"), cancellable = true)
    private static void utSkipPopShader(ItemStack stack, int x, int y, IBakedModel bakedmodel, CallbackInfo ci)
    {
        if ((stack.isEmpty() || ItemRenderingHandler.INSTANCE.getRenderHooks(stack.getItem()).isEmpty())
                && IRenderAwareItem.get(stack) == null) {
            ci.cancel();
        }
    }

    /**
     * @reason Get Quark rune color without expensive reflection.
     */
    @Inject(method = "getCustomColor", at = @At(value = "INVOKE", target = "Ljava/lang/Class;forName(Ljava/lang/String;)Ljava/lang/Class;"), cancellable = true)
    private static void utGetQuarkRuneColor(int prev, CallbackInfoReturnable<Integer> cir) {
        int runeColor = QuarkCompat.getRuneColor(target);
        cir.setReturnValue(runeColor != DEFAULT_GLINT_COLOR ? runeColor : getColorFromStack(target, prev));
    }
}
