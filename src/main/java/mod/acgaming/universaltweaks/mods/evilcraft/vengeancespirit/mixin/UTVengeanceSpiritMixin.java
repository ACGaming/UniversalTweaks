package mod.acgaming.universaltweaks.mods.evilcraft.vengeancespirit.mixin;

import net.minecraft.util.ResourceLocation;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import org.cyclops.evilcraft.entity.monster.VengeanceSpirit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of WaitingIdly
@Mixin(value = VengeanceSpirit.class, remap = false)
public class UTVengeanceSpiritMixin
{
    @Unique
    private static final Object2BooleanMap<ResourceLocation> REGEX_CACHE_MAP = new Object2BooleanOpenHashMap<>();

    /**
     * @author WaitingIdly
     * @reason check if the ResourceLocation for the target entity is cached, and use it if so
     */
    @Inject(method = {"canSustain", "canSustainClass"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ResourceLocation;toString()Ljava/lang/String;", remap = true), cancellable = true)
    private static void utUseCache(CallbackInfoReturnable<Boolean> cir, @Local ResourceLocation id)
    {
        if (REGEX_CACHE_MAP.containsKey(id)) cir.setReturnValue(REGEX_CACHE_MAP.getBoolean(id));
    }

    /**
     * @author WaitingIdly
     * @reason cache the return result
     */
    @ModifyReturnValue(method = {"canSustain", "canSustainClass"}, at = @At("RETURN"))
    private static boolean utStoreReturnValue(boolean original, @Local ResourceLocation id)
    {
        REGEX_CACHE_MAP.put(id, original);
        return original;
    }

    /**
     * @author WaitingIdly
     * @reason clear the cache when reloading the blacklist
     */
    @Inject(method = "setBlacklist", at = @At("HEAD"))
    private static void utClearCache(CallbackInfo ci)
    {
        REGEX_CACHE_MAP.clear();
    }
}
