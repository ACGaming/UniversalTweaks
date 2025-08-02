package mod.acgaming.universaltweaks.mods.woot.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.llamalad7.mixinextras.sugar.Local;
import ipsis.woot.spawning.EntitySpawner;
import ipsis.woot.util.WootMobName;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of jchung01
@Mixin(value = EntitySpawner.class, remap = false)
public class UTEntitySpawnerMixin
{
    /**
     * @reason Set correct entity values BEFORE passing to events.
     */
    @Inject(method = "spawnEntity", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/event/ForgeEventFactory;doSpecialSpawn(Lnet/minecraft/entity/EntityLiving;Lnet/minecraft/world/World;FFF)Z"))
    private void ut$fixInitSpawn(WootMobName wootMobName, World world, BlockPos pos, CallbackInfoReturnable<Entity> cir, @Local Entity entity)
    {
        ((EntityLivingBase) entity).recentlyHit = 100;
        entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
    }
}
