package mod.acgaming.universaltweaks.tweaks.misc.gui.lanserverproperties.mixin;

import net.minecraft.client.gui.GuiShareToLan;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiShareToLan.class)
public interface GuiShareToLanAccessor
{
    @Accessor("gameMode")
    String getGameMode();

    @Accessor("allowCheats")
    boolean getAllowCheats();
}