package mod.acgaming.universaltweaks.tweaks.entities.exhaustion.mixin;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public abstract class UTRidingExhaustionMixin extends EntityLivingBase
{
    protected UTRidingExhaustionMixin(World worldIn)
    {
        super(worldIn);
    }

    @Inject(method = "addMountedMovementStat", at = @At(value = "TAIL"))
    public void utRidingExhaustion(double speedX, double speedY, double speedZ, CallbackInfo ci)
    {
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTRidingExhaustion ::: Add exhaustion while riding");
        if (this.isRiding())
        {
            int i = Math.round(MathHelper.sqrt(speedX * speedX + speedY * speedY + speedZ * speedZ));

            if (i > 0)
            {
                ((EntityPlayer) (Object) this).addExhaustion((float) (i * UTConfigTweaks.ENTITIES.utRidingExhaustion));
            }
        }
    }
}