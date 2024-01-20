package mod.acgaming.universaltweaks.tweaks.misc.gui.lanserverproperties.mixin;

import net.minecraft.server.management.PlayerList;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerList.class)
public interface PlayerListAccessor
{
    @Accessor("maxPlayers")
    void setMaxPlayers(int maxPlayers);
}