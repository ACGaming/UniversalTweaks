package mod.acgaming.universaltweaks.mods.modularrouters.mixin;


import java.awt.*;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.desht.modularrouters.client.fx.Vector3;
import me.desht.modularrouters.network.ParticleBeamMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of jchung01
@Mixin(value = ParticleBeamMessage.Handler.class, remap = false)
public class UTParticleBeamMessageMixin
{
    @WrapOperation(method = "onMessage(Lme/desht/modularrouters/network/ParticleBeamMessage;Lnet/minecraftforge/fml/common/network/simpleimpl/MessageContext;)Lnet/minecraftforge/fml/common/network/simpleimpl/IMessage;", at = @At(value = "INVOKE", target = "Lme/desht/modularrouters/client/fx/ParticleBeam;doParticleBeam(Lnet/minecraft/world/World;Lme/desht/modularrouters/client/fx/Vector3;Lme/desht/modularrouters/client/fx/Vector3;Ljava/awt/Color;F)V"))
    private void utScheduleParticle(World world, Vector3 orig, Vector3 end, Color flatColor, float size, Operation<Void> original)
    {
        Minecraft.getMinecraft().addScheduledTask(() ->
        {
            original.call(world, orig, end, flatColor, size);
        });
    }
}
