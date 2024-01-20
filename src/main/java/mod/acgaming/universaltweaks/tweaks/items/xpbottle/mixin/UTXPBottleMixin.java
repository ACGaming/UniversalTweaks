package mod.acgaming.universaltweaks.tweaks.items.xpbottle.mixin;

import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityExpBottle.class)
public abstract class UTXPBottleMixin extends EntityThrowable
{
    protected UTXPBottleMixin(World worldIn)
    {
        super(worldIn);
    }

    @Inject(method = "onImpact", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", ordinal = 0), cancellable = true)
    public void utExpBottle(RayTraceResult result, CallbackInfo ci)
    {
        if (UTConfigTweaks.ITEMS.utXPBottleAmount < 0) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTXPBottle ::: On impact");
        int xp = UTConfigTweaks.ITEMS.utXPBottleAmount;
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