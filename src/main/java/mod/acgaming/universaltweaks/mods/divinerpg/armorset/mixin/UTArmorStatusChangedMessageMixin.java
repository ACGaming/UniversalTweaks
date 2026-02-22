package mod.acgaming.universaltweaks.mods.divinerpg.armorset.mixin;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import divinerpg.networking.message.ArmorStatusChangedMessage;
import org.spongepowered.asm.mixin.Mixin;

// Courtesy of jchung01
@Mixin(value = ArmorStatusChangedMessage.Handler.class, remap = false)
public class UTArmorStatusChangedMessageMixin
{
    @WrapMethod(method = "onMessage(Ldivinerpg/networking/message/ArmorStatusChangedMessage;Lnet/minecraftforge/fml/common/network/simpleimpl/MessageContext;)Lnet/minecraftforge/fml/common/network/simpleimpl/IMessage;")
    private IMessage ut$scheduleChange(ArmorStatusChangedMessage message, MessageContext ctx, Operation<IMessage> original){
        FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> original.call(message, ctx));
        return null;
    }
}
