package mod.acgaming.universaltweaks.tweaks.entities.spawning.caps.mixin;

import net.minecraft.entity.EnumCreatureType;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EnumCreatureType.class)
public class UTSpawnCapsMixin
{
    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 70))
    private static int utSpawnCapsMonster(int constant)
    {
        if (!UTConfigTweaks.ENTITIES.SPAWN_CAPS.utSpawnCapsToggle) return constant;
        UniversalTweaks.LOGGER.info("UTSpawnCaps ::: Setting spawn cap for monsters to {}", UTConfigTweaks.ENTITIES.SPAWN_CAPS.utSpawnCapsMonster);
        return UTConfigTweaks.ENTITIES.SPAWN_CAPS.utSpawnCapsMonster;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 10))
    private static int utSpawnCapsCreature(int constant)
    {
        if (!UTConfigTweaks.ENTITIES.SPAWN_CAPS.utSpawnCapsToggle) return constant;
        UniversalTweaks.LOGGER.info("UTSpawnCaps ::: Setting spawn cap for creatures to {}", UTConfigTweaks.ENTITIES.SPAWN_CAPS.utSpawnCapsCreature);
        return UTConfigTweaks.ENTITIES.SPAWN_CAPS.utSpawnCapsCreature;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 15))
    private static int utSpawnCapsAmbient(int constant)
    {
        if (!UTConfigTweaks.ENTITIES.SPAWN_CAPS.utSpawnCapsToggle) return constant;
        UniversalTweaks.LOGGER.info("UTSpawnCaps ::: Setting spawn cap for ambients to {}", UTConfigTweaks.ENTITIES.SPAWN_CAPS.utSpawnCapsAmbient);
        return UTConfigTweaks.ENTITIES.SPAWN_CAPS.utSpawnCapsAmbient;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 5))
    private static int utSpawnCapsWaterCreature(int constant)
    {
        if (!UTConfigTweaks.ENTITIES.SPAWN_CAPS.utSpawnCapsToggle) return constant;
        UniversalTweaks.LOGGER.info("UTSpawnCaps ::: Setting spawn cap for water creatures to {}", UTConfigTweaks.ENTITIES.SPAWN_CAPS.utSpawnCapsWaterCreature);
        return UTConfigTweaks.ENTITIES.SPAWN_CAPS.utSpawnCapsWaterCreature;
    }
}