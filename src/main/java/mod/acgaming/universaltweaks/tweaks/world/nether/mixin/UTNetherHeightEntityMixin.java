package mod.acgaming.universaltweaks.tweaks.world.nether.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class UTNetherHeightEntityMixin
{
    @Shadow
    public World world;

    @Shadow
    public double posY;

    @Shadow
    protected abstract void outOfWorld();

    @Inject(method = "onEntityUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;spawnRunningParticles()V"))
    public void utNetherHeightEntity(CallbackInfo ci)
    {
        if (world.provider.isNether() && posY >= world.provider.getActualHeight()) outOfWorld();
    }
}
