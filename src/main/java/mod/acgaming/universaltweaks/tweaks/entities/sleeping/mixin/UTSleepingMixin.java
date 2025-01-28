package mod.acgaming.universaltweaks.tweaks.entities.sleeping.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityPlayer.class)
public class UTSleepingMixin
{
    @WrapWithCondition(method = "wakeUpPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;setSpawnPoint(Lnet/minecraft/util/math/BlockPos;Z)V"))
    public boolean utSleepingSpawn(EntityPlayer player, BlockPos pos, boolean forced)
    {
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTSleepingSpawn ::: Sleeping set spawn point");
        return !UTConfigTweaks.ENTITIES.SLEEPING.utDisableSettingSpawnToggle;
    }
}