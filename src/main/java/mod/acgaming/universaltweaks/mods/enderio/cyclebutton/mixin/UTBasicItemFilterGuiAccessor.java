package mod.acgaming.universaltweaks.mods.enderio.cyclebutton.mixin;

import com.enderio.core.client.gui.button.CycleButton;
import crazypants.enderio.base.filter.gui.BasicItemFilterGui;
import crazypants.enderio.base.filter.gui.DamageModeIconHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = BasicItemFilterGui.class, remap = false)
public interface UTBasicItemFilterGuiAccessor
{
    @Accessor("damageB")
    CycleButton<DamageModeIconHolder> getDamageB();
}
