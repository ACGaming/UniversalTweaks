package mod.acgaming.universaltweaks.tweaks.mixin;

import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityExpBottle.class)
public abstract class UTExpBottleMixin extends EntityThrowable
{
    public UTExpBottleMixin(World worldIn)
    {
        super(worldIn);
    }

    @Inject(method = "onImpact", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", ordinal = 0), cancellable = true)
    public void utExpBottle(RayTraceResult result, CallbackInfo ci)
    {
        if (UTConfig.tweaks.utXPBottleAmount < 0) return;
        if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTExpBottle ::: On impact");
        int xp = UTConfig.tweaks.utXPBottleAmount;
        while (xp > 0)
        {
            int split = EntityXPOrb.getXPSplit(xp);
            xp -= split;
            this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, split));
        }
        this.setDead();
        ci.cancel();
    }
}