package mod.acgaming.universaltweaks.mods.corpse.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import de.maxhenkel.corpse.net.MessageOpenHistory;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = MessageOpenHistory.class, remap = false)
public class UTMessageOpenHistoryMixin
{
    @WrapMethod(method = "onMessage(Lde/maxhenkel/corpse/net/MessageOpenHistory;Lnet/minecraftforge/fml/common/network/simpleimpl/MessageContext;)Lnet/minecraftforge/fml/common/network/simpleimpl/IMessage;")
    private IMessage wrapOnMessage(MessageOpenHistory message, MessageContext ctx, Operation<IMessage> original)
    {
        Minecraft.getMinecraft().addScheduledTask(() -> original.call(message, ctx));
        return null;
    }

}
