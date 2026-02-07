package mod.acgaming.universaltweaks.tweaks.entities.attributes;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.jetbrains.annotations.NotNull;

/**
 * Allows persistence for configured attribute values after player death. Player death normally resets all
 * attributes, undoing any changes done by mods such as <a href="https://www.curseforge.com/minecraft/mc-mods/player-attribute-commands">Player Attribute Command</a>.
 * <p>
 * Only the base attribute value is persistent, so any modifiers, such as those gained with potions, will
 * not be included.
 */
public class UTAttributeKeeper
{
    @SubscribeEvent
    public static void utAttributeKeeper(PlayerEvent.Clone event) {
        if(event.isWasDeath() && !event.isCanceled())
        {
            EntityPlayer original = event.getOriginal();
            AbstractAttributeMap originalAttributes = original.getAttributeMap();
            if(originalAttributes != null)
            {
                for(IAttributeInstance instance : originalAttributes.getAllAttributes())
                {
                    for(String configStr : UTConfigTweaks.ENTITIES.ATTRIBUTES.utAttributeLock)
                    {
                        if (instance.getAttribute().getName().equals(configStr))
                        {
                            transferAttribute(event.getEntityPlayer(), instance);
                            break;
                        }
                    }
                }
            }
        }
    }

    private static void transferAttribute(EntityPlayer player, @NotNull IAttributeInstance attributeValue)
    {
        IAttributeInstance instance = player.getEntityAttribute(attributeValue.getAttribute());
        if(instance != null)
        {
            instance.setBaseValue(attributeValue.getBaseValue());
        }
        else
        {
            player.getAttributeMap().registerAttribute(attributeValue.getAttribute()).setBaseValue(attributeValue.getBaseValue());
        }
    }
}
