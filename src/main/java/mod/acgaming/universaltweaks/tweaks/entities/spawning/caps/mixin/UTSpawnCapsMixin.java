package mod.acgaming.universaltweaks.tweaks.entities.spawning.caps.mixin;

import net.minecraft.entity.EnumCreatureType;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EnumCreatureType.class)
public class UTSpawnCapsMixin
{
    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 70))
    private static int utSpawnCapsMonster(int constant)
    {
        if (!UTConfig.TWEAKS_ENTITIES.SPAWN_CAPS.utSpawnCapsToggle) return constant;
        UniversalTweaks.LOGGER.info("UTSpawnCaps ::: Setting spawn cap for monsters to {}", UTConfig.TWEAKS_ENTITIES.SPAWN_CAPS.utSpawnCapsMonster);
        return UTConfig.TWEAKS_ENTITIES.SPAWN_CAPS.utSpawnCapsMonster;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 10))
    private static int utSpawnCapsCreature(int constant)
    {
        if (!UTConfig.TWEAKS_ENTITIES.SPAWN_CAPS.utSpawnCapsToggle) return constant;
        UniversalTweaks.LOGGER.info("UTSpawnCaps ::: Setting spawn cap for creatures to {}", UTConfig.TWEAKS_ENTITIES.SPAWN_CAPS.utSpawnCapsCreature);
        return UTConfig.TWEAKS_ENTITIES.SPAWN_CAPS.utSpawnCapsCreature;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 15))
    private static int utSpawnCapsAmbient(int constant)
    {
        if (!UTConfig.TWEAKS_ENTITIES.SPAWN_CAPS.utSpawnCapsToggle) return constant;
        UniversalTweaks.LOGGER.info("UTSpawnCaps ::: Setting spawn cap for ambients to {}", UTConfig.TWEAKS_ENTITIES.SPAWN_CAPS.utSpawnCapsAmbient);
        return UTConfig.TWEAKS_ENTITIES.SPAWN_CAPS.utSpawnCapsAmbient;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 5))
    private static int utSpawnCapsWaterCreature(int constant)
    {
        if (!UTConfig.TWEAKS_ENTITIES.SPAWN_CAPS.utSpawnCapsToggle) return constant;
        UniversalTweaks.LOGGER.info("UTSpawnCaps ::: Setting spawn cap for water creatures to {}", UTConfig.TWEAKS_ENTITIES.SPAWN_CAPS.utSpawnCapsWaterCreature);
        return UTConfig.TWEAKS_ENTITIES.SPAWN_CAPS.utSpawnCapsWaterCreature;
    }
}