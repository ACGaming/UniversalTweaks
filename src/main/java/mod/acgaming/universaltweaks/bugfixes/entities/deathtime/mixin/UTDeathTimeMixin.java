package mod.acgaming.universaltweaks.bugfixes.entities.deathtime.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

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
    public int utModifyDeathTimeCheck(int deathTime)
    {
        if (!UTConfigBugfixes.ENTITIES.utDeathTimeToggle || this.isPlayer()) return deathTime;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTDeathTime ::: Check death time");
        return Math.min(deathTime, 20);
    }

    @Shadow
    protected abstract boolean isPlayer();
}