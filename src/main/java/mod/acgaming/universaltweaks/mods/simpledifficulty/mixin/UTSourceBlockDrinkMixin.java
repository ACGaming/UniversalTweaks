package mod.acgaming.universaltweaks.mods.simpledifficulty.mixin;

import net.minecraft.entity.player.EntityPlayerMP;

import com.charles445.simpledifficulty.api.thirst.ThirstEnumBlockPos;
import com.charles445.simpledifficulty.network.MessageDrinkWater;
import com.llamalad7.mixinextras.sugar.Local;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MessageDrinkWater.Handler.class, remap = false)
public abstract class UTSourceBlockDrinkMixin
{
    @Inject(method = "lambda$onMessage$0", at = @At(value = "INVOKE", target = "Lcom/charles445/simpledifficulty/util/SoundUtil;commonPlayPlayerSound(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/SoundEvent;)V"))
    private static void utSourceBlockDrink(EntityPlayerMP player, CallbackInfo ci, @Local(name = "traceResult") ThirstEnumBlockPos traceResult)
    {
        if (UTConfigMods.SIMPLE_DIFFICULTY.utDrinkingConsumesWaterSourceToggle)
        {
            player.world.setBlockToAir(traceResult.pos);
        }
    }
}
