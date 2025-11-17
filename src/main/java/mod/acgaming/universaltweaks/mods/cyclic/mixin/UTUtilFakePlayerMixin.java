package mod.acgaming.universaltweaks.mods.cyclic.mixin;

import java.util.UUID;

import com.lothrazar.cyclicmagic.util.Const;
import com.lothrazar.cyclicmagic.util.UtilFakePlayer;
import com.mojang.authlib.GameProfile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// Courtesy of jchung01
@Mixin(value = UtilFakePlayer.class, remap = false)
public class UTUtilFakePlayerMixin
{
    // Shared profile so new PlayerAdvancements aren't created for every fake player
    @Unique
    private static final GameProfile ut$CYCLIC_PROFILE = new GameProfile(UUID.nameUUIDFromBytes(Const.MODID.getBytes()), "[Cyclic]");

    @Redirect(method = "initFakePlayer", at = @At(value = "NEW", target = "(Ljava/util/UUID;Ljava/lang/String;)Lcom/mojang/authlib/GameProfile;"))
    private static GameProfile utUseSharedProfile(UUID id, String name) {
        return ut$CYCLIC_PROFILE;
    }
}
