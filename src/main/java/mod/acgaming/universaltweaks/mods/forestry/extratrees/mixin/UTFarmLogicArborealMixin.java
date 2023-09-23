package mod.acgaming.universaltweaks.mods.forestry.extratrees.mixin;

import net.minecraft.item.ItemStack;

import binnie.extratrees.items.ItemETFood;
import forestry.api.farming.IFarmProperties;
import forestry.farming.logic.FarmLogicArboreal;
import forestry.farming.logic.FarmLogicHomogeneous;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = FarmLogicArboreal.class, remap = false)
public abstract class UTFarmLogicArborealMixin extends FarmLogicHomogeneous
{
    public UTFarmLogicArborealMixin(IFarmProperties properties, boolean isManual)
    {
        super(properties, isManual);
    }

    @Override
    public boolean isAcceptedWindfall(ItemStack stack)
    {
        return UTConfigMods.FORESTRY.utFOGatherWindfallToggle && stack.getItem() instanceof ItemETFood;
    }
}