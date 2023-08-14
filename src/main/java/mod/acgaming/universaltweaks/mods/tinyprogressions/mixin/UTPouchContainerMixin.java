package mod.acgaming.universaltweaks.mods.tinyprogressions.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

import com.kashdeya.tinyprogressions.container.PouchContainer;
import com.kashdeya.tinyprogressions.inits.TechItems;
import org.spongepowered.asm.mixin.Mixin;

// Courtesy of Focamacho
@Mixin(value = PouchContainer.class, remap = false)
public class UTPouchContainerMixin extends Container
{
    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return player.getHeldItemMainhand().getItem() == TechItems.pouch;
    }
}