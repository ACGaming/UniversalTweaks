package mod.acgaming.universaltweaks.mods.moartinkers.mixin;

import java.util.Collection;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import com.bartz24.moartinkers.traits.TraitEnergyRepair;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import mod.acgaming.universaltweaks.mods.moartinkers.UTBaubleCompat;
import mod.acgaming.universaltweaks.mods.moartinkers.UTBaubleHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of kurrycat2004
@Mixin(value = TraitEnergyRepair.class, remap = false)
public class UTTraitEnergyRepairMixin
{
    @ModifyReceiver(method = "onToolDamage", at = @At(value = "INVOKE", target = "Ljava/util/List;addAll(Ljava/util/Collection;)Z", ordinal = 0))
    private List<ItemStack> utInjectBaubleCompatToolDamage(List<ItemStack> instance, Collection<? extends ItemStack> es, ItemStack tool, int damage, int newDamage, EntityLivingBase entity)
    {
        if (UTBaubleCompat.isBaublesLoaded())
        {
            instance.addAll(UTBaubleHelper.getBaublesInventory(entity));
        }
        return instance;
    }
}
