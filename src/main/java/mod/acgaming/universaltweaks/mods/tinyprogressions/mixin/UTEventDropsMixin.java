package mod.acgaming.universaltweaks.mods.tinyprogressions.mixin;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.kashdeya.tinyprogressions.events.EventDrops;
import com.kashdeya.tinyprogressions.handlers.ArmorHandler;
import com.kashdeya.tinyprogressions.handlers.ConfigHandler;
import com.kashdeya.tinyprogressions.inits.TechItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

// Courtesy of Focamacho
@Mixin(value = EventDrops.class, remap = false)
public class UTEventDropsMixin
{
    /**
     * @author DupeFix Project
     * @reason Fix Duplication Glitch
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    @Overwrite
    public static void onLivingDrops(LivingDropsEvent event)
    {
        Entity entity = event.getEntity();
        if (event.getEntity() instanceof EntityDragon && ArmorHandler.dragon_armor)
        {
            event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, new ItemStack(TechItems.dragon_scale, new Random().nextInt(16))));
        }
        else if (event.getEntity() instanceof EntityWither && ConfigHandler.wither_rib)
        {
            event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, new ItemStack(TechItems.wither_rib, new Random().nextInt(6))));
        }
    }
}