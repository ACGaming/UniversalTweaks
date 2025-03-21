package mod.acgaming.universaltweaks.mods.moartinkers.mixin;

import java.util.Collection;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;

import com.bartz24.moartinkers.traits.TraitEnergyEater;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import mod.acgaming.universaltweaks.mods.moartinkers.UTBaubleCompat;
import mod.acgaming.universaltweaks.mods.moartinkers.UTBaubleHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = TraitEnergyEater.class, remap = false)
public class UTTraitEnergyEaterMixin
{
    @ModifyReceiver(method = "miningSpeed",
        at = @At(value = "INVOKE", target = "Ljava/util/List;addAll(Ljava/util/Collection;)Z", ordinal = 0))
    private List<ItemStack> utInjectBaubleCompatMining(List<ItemStack> instance, Collection<? extends ItemStack> es,
                                                       ItemStack tool, PlayerEvent.BreakSpeed event)
    {
        if (UTBaubleCompat.isBaublesLoaded())
        {
            instance.addAll(UTBaubleHelper.getBaublesInventory(event.getEntityPlayer()));
        }
        return instance;
    }

    @ModifyReceiver(method = "damage",
        at = @At(value = "INVOKE", target = "Ljava/util/List;addAll(Ljava/util/Collection;)Z", ordinal = 0))
    private List<ItemStack> utInjectBaubleCompatDamage(List<ItemStack> instance, Collection<? extends ItemStack> es,
                                                       ItemStack tool, EntityLivingBase player)
    {
        if (UTBaubleCompat.isBaublesLoaded())
        {
            instance.addAll(UTBaubleHelper.getBaublesInventory(player));
        }
        return instance;
    }
}
