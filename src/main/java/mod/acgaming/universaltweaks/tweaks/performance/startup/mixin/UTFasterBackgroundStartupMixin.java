package mod.acgaming.universaltweaks.tweaks.performance.startup.mixin;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.FMLClientHandler;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of EverNife
@Mixin(ItemStack.class)
public class UTFasterBackgroundStartupMixin
{
    /**
     * @author EverNife
     * @reason In some rare cases when you start your modpack on alt-tab
     * the startup can take several minutes, because some mods (CraftTweaker)
     * will try to ToolTip some ItemStacks and some mods (EnderIO) can try to check
     * if the user is pressing a Keyboard button to show some info, but, as you are on alt-tab
     * this process can take a looong time (a modpack startup from 3 min can go up to 9 min)
     */
    @WrapWithCondition(method = "getTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/event/ForgeEventFactory;onItemTooltip(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/EntityPlayer;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)Lnet/minecraftforge/event/entity/player/ItemTooltipEvent;"))
    public boolean utFasterBackgroundStartup(ItemStack itemStack, EntityPlayer entityPlayer, List<String> toolTip, ITooltipFlag flags)
    {
        return !UTConfigTweaks.PERFORMANCE.utFasterBackgroundStartupToggle || !FMLClientHandler.instance().isLoading();
    }
}