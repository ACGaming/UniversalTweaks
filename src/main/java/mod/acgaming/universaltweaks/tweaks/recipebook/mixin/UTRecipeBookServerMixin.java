package mod.acgaming.universaltweaks.tweaks.recipebook.mixin;

import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.stats.RecipeBookServer;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RecipeBookServer.class)
public class UTRecipeBookServerMixin
{
    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    public void utRecipeBookInit(EntityPlayerMP player, CallbackInfo ci)
    {
        if (!UTConfig.TWEAKS_MISC.utRecipeBookToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTRecipeBookServer ::: Init");
        ci.cancel();
    }

    @Inject(method = "add", at = @At("HEAD"), cancellable = true)
    public void utRecipeBookAdd(List<IRecipe> recipesIn, EntityPlayerMP player, CallbackInfo ci)
    {
        if (!UTConfig.TWEAKS_MISC.utRecipeBookToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTRecipeBookServer ::: Add");
        ci.cancel();
    }

    @Inject(method = "remove", at = @At("HEAD"), cancellable = true)
    public void utRecipeBookRemove(List<IRecipe> recipesIn, EntityPlayerMP player, CallbackInfo ci)
    {
        if (!UTConfig.TWEAKS_MISC.utRecipeBookToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTRecipeBookServer ::: Remove");
        ci.cancel();
    }
}