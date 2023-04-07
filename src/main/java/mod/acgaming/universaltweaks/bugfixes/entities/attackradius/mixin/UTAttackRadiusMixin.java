package mod.acgaming.universaltweaks.bugfixes.entities.attackradius.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// MC-2310
// https://bugs.mojang.com/browse/MC-2310
@Mixin(EntityMob.class)
public abstract class UTAttackRadiusMixin extends EntityCreature
{
    public UTAttackRadiusMixin(World worldIn)
    {
        super(worldIn);
    }

    @Inject(method = "attackEntityAsMob", at = @At("HEAD"), cancellable = true)
    public void utAttackRadius(Entity target, CallbackInfoReturnable<Boolean> cir)
    {
        if (UTConfig.BUGFIXES_ENTITIES.utAttackRadiusToggle && !this.canEntityBeSeen(target)) cir.setReturnValue(false);
    }
}