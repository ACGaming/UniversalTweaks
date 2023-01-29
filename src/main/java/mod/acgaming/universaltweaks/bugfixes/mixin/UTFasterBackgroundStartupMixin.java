package mod.acgaming.universaltweaks.bugfixes.mixin;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.client.FMLClientHandler;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of EverNife
@Mixin(value = ForgeEventFactory.class, remap = false)
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
    @Inject(method = "onItemTooltip", at = @At(value = "HEAD"), cancellable = true)
    private static void utFasterBackgroundStartup(ItemStack itemStack, EntityPlayer entityPlayer, List<String> toolTip, ITooltipFlag flags, CallbackInfoReturnable<ItemTooltipEvent> cir)
    {
        if (!UTConfig.BUGFIXES_MISC.utFasterBackgroundStartupToggle) return;
        //if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTFasterBackgroundStartup ::: On item tooltip");
        ItemTooltipEvent event = new ItemTooltipEvent(itemStack, entityPlayer, toolTip, flags);
        if (!FMLClientHandler.instance().isLoading()) MinecraftForge.EVENT_BUS.post(event);
        cir.setReturnValue(event);
    }
}