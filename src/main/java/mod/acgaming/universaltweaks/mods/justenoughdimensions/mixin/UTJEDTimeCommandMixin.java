package mod.acgaming.universaltweaks.mods.justenoughdimensions.mixin;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.world.World;

import com.llamalad7.mixinextras.sugar.Local;
import fi.dy.masa.justenoughdimensions.command.CommandJED;
import fi.dy.masa.justenoughdimensions.command.utils.CommandJEDTime;
import mod.acgaming.universaltweaks.tweaks.misc.commands.time.UTTimeCommandMaps;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandJEDTime.class)
public abstract class UTJEDTimeCommandMixin
{
    @Inject(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setWorldTime(J)V", ordinal = 0), cancellable = true)
    private static void utCommandJEDTime(CommandJED cmd, int dimension, String[] args, ICommandSender sender, CallbackInfo ci, @Local(name = "world") World world, @Local(argsOnly = true) int timeValue)
    {
        boolean setDay = args[1].equals("day");
        boolean setNight = args[1].equals("night");
        if (!setDay && !setNight) return;
        long dayLength = 24000L;
        if (UTTimeCommandMaps.DIM_DAY_LENGTH_MAP.containsKey(dimension))
        {
            dayLength = UTTimeCommandMaps.DIM_DAY_LENGTH_MAP.get(dimension);
            if (setNight) timeValue = UTTimeCommandMaps.DIM_NIGHT_ADD_MAP.get(dimension);
            else timeValue = UTTimeCommandMaps.DIM_DAY_ADD_MAP.get(dimension);
        }
        long currentTotal = world.getTotalWorldTime();
        long currentDaytime = currentTotal % dayLength;
        long days = currentTotal / dayLength;
        if (timeValue <= currentDaytime) days++;
        long newTotalTime = days * dayLength + timeValue;
        world.setWorldTime(timeValue);
        world.setTotalWorldTime(newTotalTime);
        CommandBase.notifyCommandListener(sender, cmd, "cmd.universaltweaks.time.set", dimension, newTotalTime);
        ci.cancel();
    }
}
