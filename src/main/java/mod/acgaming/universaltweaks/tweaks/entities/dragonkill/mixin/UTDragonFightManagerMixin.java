package mod.acgaming.universaltweaks.tweaks.entities.dragonkill.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import net.minecraft.world.end.DragonFightManager;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DragonFightManager.class)
public class UTDragonFightManagerMixin
{
    /**
     * @author Invadermonky
     * @reason Allows dragon egg spawning on Ender Dragon kills following the first kill.
     */
    @ModifyExpressionValue(
        method = "processDragonDeath",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/world/end/DragonFightManager;previouslyKilled:Z",
            opcode = Opcodes.GETFIELD
        )
    )
    private boolean utSpawnEggOnDeath(boolean previouslyKilled)
    {
        //The egg spawn check inverts this logic
        return previouslyKilled && !UTConfigTweaks.ENTITIES.DRAGON_KILL.utSecondKillEgg;
    }
}
