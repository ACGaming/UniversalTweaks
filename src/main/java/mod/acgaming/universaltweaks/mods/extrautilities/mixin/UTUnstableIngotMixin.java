package mod.acgaming.universaltweaks.mods.extrautilities.mixin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.inventory.Slot;

import com.rwtema.extrautils2.items.ItemUnstableIngots;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ItemUnstableIngots.class, remap = false)
public abstract class UTUnstableIngotMixin
{
    @Redirect(method = "checkForExplosion(Lnet/minecraftforge/fml/common/gameevent/TickEvent$PlayerTickEvent;)V", at = @At(value = "INVOKE", target = "Ljava/util/List;iterator()Ljava/util/Iterator;"))
    private Iterator<Slot> utCheckForExplosion(List<Slot> inventorySlots)
    {
        return new ArrayList<>(inventorySlots).iterator();
    }
}
