package mod.acgaming.universaltweaks.bugfixes.entities.arrowdesync.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author jubitus
 *
 * The server syncs an arrow's position after its first tick, but not its velocity.
 * The client then flies from the tick 1 position with the tick 0 velocity (one tick of gravity and drag missing),
 * lands too far and snaps back on the next position sync, up to a second later for non-critical arrows.
 * Skipping that first correction lets the client simulate the tick itself, matching the server trajectory.
 */
@Mixin(EntityArrow.class)
public abstract class UTEntityArrowMixin extends Entity
{
    @Shadow
    protected boolean inGround;

    protected UTEntityArrowMixin(World worldIn)
    {
        super(worldIn);
    }

    @Inject(method = "setPositionAndRotationDirect", at = @At("HEAD"), cancellable = true)
    private void utSkipFirstTickCorrection(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport, CallbackInfo ci)
    {
        if (this.ticksExisted == 0 && !this.inGround) ci.cancel();
    }
}