package mod.acgaming.universaltweaks.tweaks.misc.advancements.guisize.mixin;

import net.minecraft.client.gui.advancements.GuiAdvancementTab;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiAdvancementTab.class)
public interface UTGuiAdvancementTabAccessor
{
    @Accessor("scrollX")
    int getScrollX();

    @Accessor("scrollY")
    int getScrollY();

    @Accessor("index")
    int getIndex();
}
