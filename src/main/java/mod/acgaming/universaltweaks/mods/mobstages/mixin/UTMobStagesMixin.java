package mod.acgaming.universaltweaks.mods.mobstages.mixin;

import net.minecraft.entity.EntityLiving;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import mod.acgaming.universaltweaks.config.UTConfig;
import net.darkhax.mobstages.MobStageInfo;
import net.darkhax.mobstages.MobStages;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MobStages.class, remap = false)
public abstract class UTMobStagesMixin
{
    @Inject(method = "allowSpawning", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityList;createEntityByIDFromName(Lnet/minecraft/util/ResourceLocation;Lnet/minecraft/world/World;)Lnet/minecraft/entity/Entity;", remap = true), cancellable = true)
    private static void utCheckSpawningRules(MobStageInfo info, LivingSpawnEvent.CheckSpawn event, CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfig.MOD_INTEGRATION.MOB_STAGES.utSpawningRules) return;
        EntityLiving entityLiving = (EntityLiving) event.getEntityLiving();
        if (!entityLiving.getCanSpawnHere() || !entityLiving.isNotColliding())
        {
            event.setResult(Event.Result.DENY);
            cir.setReturnValue(false);
        }
    }
}
