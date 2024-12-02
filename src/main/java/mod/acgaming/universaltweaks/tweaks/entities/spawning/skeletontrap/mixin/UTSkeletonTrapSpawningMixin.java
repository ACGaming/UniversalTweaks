package mod.acgaming.universaltweaks.tweaks.entities.spawning.skeletontrap.mixin;

import net.minecraft.world.GameRules;
import net.minecraft.world.WorldServer;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldServer.class)
public class UTSkeletonTrapSpawningMixin
{
    @Redirect(method = "updateBlocks()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Ljava/lang/String;)Z"))
    private boolean utSkeletonTrapSpawning(GameRules rules, String name)
    {
        return !UTConfigTweaks.ENTITIES.UNDEAD_HORSES.utSkeletonTrapSpawningToggle && rules.getBoolean(name);
    }
}