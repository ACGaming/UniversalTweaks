package mod.acgaming.universaltweaks.tweaks.misc.recipebook.mixin;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerMP.class)
public class UTRecipeBookPlayerMixin
{
    @Inject(method = "readEntityFromNBT", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NBTTagCompound;hasKey(Ljava/lang/String;I)Z", ordinal = 2), cancellable = true)
    public void utRecipeBookReadEntityFromNBT(NBTTagCompound compound, CallbackInfo ci)
    {
        if (!UTConfig.TWEAKS_MISC.utRecipeBookToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTRecipeBookPlayer ::: Read entity from NBT");
        ci.cancel();
    }

    @Inject(method = "writeEntityToNBT", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NBTTagCompound;setTag(Ljava/lang/String;Lnet/minecraft/nbt/NBTBase;)V", ordinal = 3), cancellable = true)
    public void utRecipeBookWriteEntityToNBT(NBTTagCompound compound, CallbackInfo ci)
    {
        if (!UTConfig.TWEAKS_MISC.utRecipeBookToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTRecipeBookPlayer ::: Write entity to NBT");
        ci.cancel();
    }
}