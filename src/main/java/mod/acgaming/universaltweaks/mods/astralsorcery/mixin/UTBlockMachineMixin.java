package mod.acgaming.universaltweaks.mods.astralsorcery.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import hellfirepvp.astralsorcery.common.block.BlockMachine;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = BlockMachine.class, remap = false)
public abstract class UTBlockMachineMixin
{
    @ModifyExpressionValue(method = "handleSpecificActivateEvent", at = @At(value = "INVOKE", target = "Lhellfirepvp/astralsorcery/common/util/MiscUtils;isPlayerFakeMP(Lnet/minecraft/entity/player/EntityPlayerMP;)Z"))
    private boolean utAllowFakePlayers(boolean original)
    {
        if (!UTConfigMods.ASTRAL_SORCERY.utAllowFakePlayers) return original;
        return false;
    }
}
