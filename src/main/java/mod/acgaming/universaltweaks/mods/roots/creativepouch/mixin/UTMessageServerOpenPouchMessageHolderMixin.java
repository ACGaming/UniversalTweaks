package mod.acgaming.universaltweaks.mods.roots.creativepouch.mixin;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.llamalad7.mixinextras.sugar.Local;
import epicsquid.roots.item.ItemPouch;
import epicsquid.roots.item.PouchType;
import epicsquid.roots.network.MessageServerOpenPouch;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of WaitingIdly
@Mixin(value = MessageServerOpenPouch.MessageHolder.class, remap = false)
public abstract class UTMessageServerOpenPouchMessageHolderMixin
{

    /**
     * @author WaitingIdly
     * @reason The Creative Pouch has no inventory slots, and crashes when opened.
     * Make it so it doesn't open.
     */
    @Inject(method = "handleMessage(Lepicsquid/roots/network/MessageServerOpenPouch;Lnet/minecraftforge/fml/common/network/simpleimpl/MessageContext;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayerMP;openGui(Ljava/lang/Object;ILnet/minecraft/world/World;III)V"), cancellable = true)
    private void utSkipCreativePouchGui(MessageServerOpenPouch message, MessageContext ctx, CallbackInfo ci, @Local ItemStack pouch)
    {
        if (ItemPouch.getPouchType(pouch) == PouchType.CREATIVE) ci.cancel();
    }
}
