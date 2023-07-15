package mod.acgaming.universaltweaks.mods.bloodmagic.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

import WayofTime.bloodmagic.core.RegistrarBloodMagicItems;
import WayofTime.bloodmagic.item.inventory.ContainerHolding;
import org.spongepowered.asm.mixin.Mixin;

// Courtesy of Focamacho
@Mixin(ContainerHolding.class)
public class UTContainerHoldingMixin extends Container
{
    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return player.getHeldItemMainhand().getItem() == RegistrarBloodMagicItems.SIGIL_HOLDING;
    }
}