package mod.acgaming.universaltweaks.tweaks.world.timecommand.mixin;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandTime;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(CommandTime.class)
public abstract class UTCommandTimeMixin extends CommandBase
{
    @Inject(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/command/CommandTime;setAllWorldTimes(Lnet/minecraft/server/MinecraftServer;I)V"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    public void utCommandTime(MinecraftServer server, ICommandSender sender, String[] args, CallbackInfo ci, int timeValue)
    {
        if (!"day".equals(args[1]) && !"night".equals(args[1])) return;
        for (WorldServer world : server.worlds)
        {
            long currentTotal = world.getTotalWorldTime();
            long currentDaytime = currentTotal % 24000L;
            long days = currentTotal / 24000L;
            if (timeValue <= currentDaytime) days++;
            long newTotalTime = days * 24000L + timeValue;
            world.setWorldTime(timeValue);
            world.setTotalWorldTime(newTotalTime);
        }
        notifyCommandListener(sender, this, "commands.time.set", timeValue);
        ci.cancel();
    }
}
