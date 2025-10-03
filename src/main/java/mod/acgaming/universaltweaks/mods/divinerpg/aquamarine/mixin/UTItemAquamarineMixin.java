package mod.acgaming.universaltweaks.mods.divinerpg.aquamarine.mixin;

import divinerpg.objects.items.arcana.ItemAquamarine;
import divinerpg.objects.items.base.ItemMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of WaitingIdly
@Mixin(value = ItemAquamarine.class, remap = false)
public abstract class UTItemAquamarineMixin extends ItemMod
{
    private UTItemAquamarineMixin(String name)
    {
        super(name);
    }

    /**
     * @author WaitingIdly
     * @reason Aquamarine has durability, but doesn't set max stack size to 1.
     */
    @Inject(method = "<init>", at = @At("TAIL"))
    private void utCorrectMaxStackSize(String name, CallbackInfo ci)
    {
        setMaxStackSize(1);
    }
}
