package mod.acgaming.universaltweaks.tweaks.misc.commands.time.mixin;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandTime;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

import com.llamalad7.mixinextras.sugar.Local;
import mod.acgaming.universaltweaks.tweaks.misc.commands.time.UTTimeCommandMaps;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandTime.class)
public abstract class UTTimeCommandMixin extends CommandBase
{
    @Inject(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/command/CommandTime;setAllWorldTimes(Lnet/minecraft/server/MinecraftServer;I)V"), cancellable = true)
    public void utCommandTime(MinecraftServer server, ICommandSender sender, String[] args, CallbackInfo ci, @Local int timeValue)
    {
        boolean setDay = args[1].equals("day");
        boolean setNight = args[1].equals("night");
        if (!setDay && !setNight) return;
        long dayLength = 24000L;
        for (WorldServer world : server.worlds)
        {
            int dimID = world.provider.getDimension();
            if (UTTimeCommandMaps.DIM_DAY_LENGTH_MAP.containsKey(dimID))
            {
                dayLength = UTTimeCommandMaps.DIM_DAY_LENGTH_MAP.get(dimID);
                if (setNight) timeValue = UTTimeCommandMaps.DIM_NIGHT_ADD_MAP.get(dimID);
                else timeValue = UTTimeCommandMaps.DIM_DAY_ADD_MAP.get(dimID);
            }
            long currentTotal = world.getTotalWorldTime();
            long currentDaytime = currentTotal % dayLength;
            long days = currentTotal / dayLength;
            if (timeValue <= currentDaytime) days++;
            long newTotalTime = days * dayLength + timeValue;
            world.setWorldTime(timeValue);
            world.setTotalWorldTime(newTotalTime);
            notifyCommandListener(sender, this, "cmd.universaltweaks.time.set", dimID, newTotalTime);
        }
        ci.cancel();
    }
}
