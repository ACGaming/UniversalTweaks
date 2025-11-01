package mod.acgaming.universaltweaks.bugfixes.entities.boat.mixin;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// MC-98160
// https://bugs.mojang.com/browse/MC/issues/MC-98160
@Mixin(EntityBoat.class)
public abstract class UTBoatBreakingMixin extends Entity
{
    @Shadow
    private EntityBoat.Status status;

    public UTBoatBreakingMixin(World world)
    {
        super(world);
    }

    /*
     * Cancel updating fall state when boat breaking is disabled
     */
    @Inject(method = "updateFallState", at = @At(value = "HEAD"), cancellable = true)
    public void utBoatBreaking(double y, boolean onGround, IBlockState state, BlockPos pos, CallbackInfo ci)
    {
        if (UTConfigBugfixes.ENTITIES.utBoatBreakingFallHeight <= 0 && onGround) ci.cancel();
    }

    /*
     * Account for specified fall height in config
     */
    @ModifyConstant(method = "updateFallState", constant = @Constant(floatValue = 3.0F))
    public float utBoatBreaking(float fallDistance)
    {
        return UTConfigBugfixes.ENTITIES.utBoatBreakingFallHeight;
    }

    /*
     * Also update fall state when airborne to correctly calculate fall distance
     */
    @Redirect(method = "updateFallState", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/item/EntityBoat;status:Lnet/minecraft/entity/item/EntityBoat$Status;"))
    public EntityBoat.Status utBoatBreaking(EntityBoat boat)
    {
        if (UTConfigBugfixes.ENTITIES.utBoatBreakingFallHeight > 0 && this.status == EntityBoat.Status.IN_AIR) return EntityBoat.Status.ON_LAND;
        return this.status;
    }
}
