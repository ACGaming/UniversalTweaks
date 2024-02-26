package mod.acgaming.universaltweaks.mods.thaumicwonders.mixin;

import com.verdantartifice.thaumicwonders.client.renderers.models.gear.ModelVoidFortressArmor;
import com.verdantartifice.thaumicwonders.common.items.armor.ItemVoidFortressArmor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// Courtesy of 2302479492 ("Aqua")
@Mixin(value = ItemVoidFortressArmor.class, remap = false)
public class UTItemVoidFortressArmorMixin
{
    @Unique
    private static final ModelVoidFortressArmor universalTweaks$legModel = new ModelVoidFortressArmor(0.5F);
    @Unique
    private static final ModelVoidFortressArmor universalTweaks$generalModel = new ModelVoidFortressArmor(1.0F);

    @Redirect(method = "getArmorModel", at = @At(value = "NEW", target = "(F)Lcom/verdantartifice/thaumicwonders/client/renderers/models/gear/ModelVoidFortressArmor;"))
    private ModelVoidFortressArmor utUseStaticModels(float f)
    {
        return f == 0.5 ? universalTweaks$legModel : universalTweaks$generalModel;
    }
}
