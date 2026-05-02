package mod.acgaming.universaltweaks.tweaks.entities.spawning.lightlevel.mixin;

import net.minecraft.entity.monster.EntityMob;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EntityMob.class)
public abstract class UTMobSpawningLightLevelMixin
{
    @ModifyConstant(method = "isValidLightLevel", constant = @Constant(intValue = 8))
    public int utMobSpawningLightLevel(int constant)
    {
        return UTConfigTweaks.ENTITIES.utMobSpawningLightLevel + 1;
    }
}
