package mod.acgaming.universaltweaks.bugfixes.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import zone.rong.mixinextras.injector.ModifyExpressionValue;

// MC-84873
// https://bugs.mojang.com/browse/MC-84873
// Courtesy of isXander
@Mixin(EntityLivingBase.class)
public abstract class UTDeathTimeMixin extends Entity
{
    public UTDeathTimeMixin(World worldIn)
    {
        super(worldIn);
    }

    @ModifyExpressionValue(method = "onDeathUpdate", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityLivingBase;deathTime:I", ordinal = 1, opcode = Opcodes.GETFIELD))
    public int UTModifyDeathTimeCheck(int deathTime)
    {
        if (!UTConfig.BUGFIXES_ENTITIES.utDeathTimeToggle || this.isPlayer()) return deathTime;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTDeathTime ::: Check death time");
        return Math.min(deathTime, 20);
    }

    @Shadow
    protected abstract boolean isPlayer();
}