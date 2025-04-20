package mod.acgaming.universaltweaks.tweaks.entities.speed.player.mixin;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public abstract class UTPlayerAirSpeedMixin extends EntityLivingBase
{
    @Shadow
    protected float speedInAir;

    protected UTPlayerAirSpeedMixin(World world)
    {
        super(world);
    }

    @Override
    protected float getJumpUpwardsMotion()
    {
        if (!UTConfigTweaks.ENTITIES.PLAYER_SPEED.utPlayerJumpSpeed) return super.getJumpUpwardsMotion();
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTPlayerAirSpeed ::: Set jump upwards motion");
        float defaultMovementSpeed = (float) this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue();
        float currentMovementSpeed = (float) this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
        return Math.min(super.getJumpUpwardsMotion(), (super.getJumpUpwardsMotion() / defaultMovementSpeed) * currentMovementSpeed);
    }

    @Inject(method = "onLivingUpdate", at = @At(value = "HEAD"))
    private void utAirSpeed(CallbackInfo ci)
    {
        if (!UTConfigTweaks.ENTITIES.PLAYER_SPEED.utPlayerAirSpeed) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTPlayerAirSpeed ::: On living update -> Update speed in air");
        float defaultMovementSpeed = (float) this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue();
        float currentMovementSpeed = (float) this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
        this.speedInAir = Math.min(0.02F, (0.02F / defaultMovementSpeed) * currentMovementSpeed);
    }

    @ModifyConstant(method = "onLivingUpdate", constant = @Constant(doubleValue = 0.3))
    private double utAirSpeedSprinting(double factor)
    {
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTPlayerAirSpeed ::: On living update -> Set air speed sprinting factor");
        return UTConfigTweaks.ENTITIES.PLAYER_SPEED.utPlayerAirSpeedSprintingFactor != 0.3D ? UTConfigTweaks.ENTITIES.PLAYER_SPEED.utPlayerAirSpeedSprintingFactor : factor;
    }
}
