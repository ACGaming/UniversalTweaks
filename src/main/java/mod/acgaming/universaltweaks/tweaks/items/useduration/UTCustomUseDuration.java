package mod.acgaming.universaltweaks.tweaks.items.useduration;

import java.util.Map;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTCustomUseDuration
{
    public static Map<String, Integer> itemUseDurationMap = new Object2IntOpenHashMap<>();
    public static Map<String, Integer> itemUseCooldownMap = new Object2IntOpenHashMap<>();
    public static int used = 0;

    public static void initItemUseMaps()
    {
        itemUseDurationMap.clear();
        itemUseCooldownMap.clear();
        try
        {
            for (String config : UTConfig.TWEAKS_ITEMS.utCustomUseDurations)
            {
                String[] configParts = config.split(";");
                String[] itemParts = configParts[0].split(":");
                ResourceLocation resLoc = new ResourceLocation(itemParts[0], itemParts[1]);
                int cooldown = 0;
                if (configParts.length > 2) cooldown = Integer.parseInt(configParts[2]);
                int meta = 0;
                if (itemParts.length > 2) meta = Integer.parseInt(itemParts[2]);
                int duration = Integer.parseInt(configParts[1]);
                if (ForgeRegistries.ITEMS.containsKey(resLoc))
                {
                    itemUseDurationMap.put(ForgeRegistries.ITEMS.getValue(resLoc).toString() + meta, duration);
                    itemUseCooldownMap.put(ForgeRegistries.ITEMS.getValue(resLoc).toString() + meta, cooldown);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        UniversalTweaks.LOGGER.info("Item use duration map initialized");
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void utCustomUseDurationTick(LivingEntityUseItemEvent.Tick event)
    {
        if (event.getEntityLiving().getEntityWorld().isRemote) return;
        ItemStack stack = event.getItem();
        int duration = getCustomDuration(stack);
        if (duration < 0) return;
        used++;
        if (used >= duration)
        {
            used = 0;
            EntityLivingBase entity = event.getEntityLiving();
            entity.stopActiveHand();
            int cooldown = getCustomCooldown(stack);
            if (cooldown > 0 && entity instanceof EntityPlayer) ((EntityPlayer) entity).getCooldownTracker().setCooldown(stack.getItem(), cooldown);
            event.setCanceled(true);
        }
    }

    public static int getCustomDuration(ItemStack stack)
    {
        int meta = 0;
        try
        {
            if (stack.getHasSubtypes()) meta = stack.getMetadata();
        }
        catch (Exception ignored) {}
        String itemKey = stack.getItem().toString() + meta;
        if (UTCustomUseDuration.itemUseDurationMap.containsKey(itemKey)) return UTCustomUseDuration.itemUseDurationMap.get(itemKey);
        return -1;
    }

    public static int getCustomCooldown(ItemStack stack)
    {
        int meta = 0;
        try
        {
            if (stack.getHasSubtypes()) meta = stack.getMetadata();
        }
        catch (Exception ignored) {}
        String itemKey = stack.getItem().toString() + meta;
        if (UTCustomUseDuration.itemUseCooldownMap.containsKey(itemKey)) return UTCustomUseDuration.itemUseCooldownMap.get(itemKey);
        return -1;
    }
}