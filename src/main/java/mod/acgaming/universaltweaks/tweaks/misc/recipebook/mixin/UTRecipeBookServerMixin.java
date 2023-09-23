package mod.acgaming.universaltweaks.tweaks.misc.recipebook.mixin;

import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.RecipeBookServer;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RecipeBookServer.class)
public class UTRecipeBookServerMixin
{
    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    public void utRecipeBookInit(EntityPlayerMP player, CallbackInfo ci)
    {
        if (!UTConfigTweaks.MISC.utRecipeBookToggle) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTRecipeBookServer ::: Init");
        ci.cancel();
    }

    @Inject(method = "add", at = @At("HEAD"), cancellable = true)
    public void utRecipeBookAdd(List<IRecipe> recipesIn, EntityPlayerMP player, CallbackInfo ci)
    {
        if (!UTConfigTweaks.MISC.utRecipeBookToggle) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTRecipeBookServer ::: Add");
        ci.cancel();
    }

    @Inject(method = "remove", at = @At("HEAD"), cancellable = true)
    public void utRecipeBookRemove(List<IRecipe> recipesIn, EntityPlayerMP player, CallbackInfo ci)
    {
        if (!UTConfigTweaks.MISC.utRecipeBookToggle) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTRecipeBookServer ::: Remove");
        ci.cancel();
    }

    /**
     * Fallback inject for {@link UTRecipeBookPlayerMixin#utRecipeBookReadEntityFromNBT(NBTTagCompound, CallbackInfo)} ()}
     */
    @Inject(method = "write", at = @At("HEAD"), cancellable = true)
    public void utRecipeBookWrite(CallbackInfoReturnable<NBTTagCompound> cir)
    {
        if (!UTConfigTweaks.MISC.utRecipeBookToggle) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTRecipeBookServer ::: Write");
        cir.setReturnValue(new NBTTagCompound());
    }

    /**
     * Fallback inject for {@link UTRecipeBookPlayerMixin#utRecipeBookWriteEntityToNBT(NBTTagCompound, CallbackInfo)} ()}
     */
    @Inject(method = "read", at = @At("HEAD"), cancellable = true)
    public void utRecipeBookRead(NBTTagCompound tag, CallbackInfo ci)
    {
        if (!UTConfigTweaks.MISC.utRecipeBookToggle) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTRecipeBookServer ::: Read");
        ci.cancel();
    }
}