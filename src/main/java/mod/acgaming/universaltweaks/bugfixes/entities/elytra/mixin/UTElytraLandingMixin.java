package mod.acgaming.universaltweaks.bugfixes.entities.elytra.mixin;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// MC-111444
// https://bugs.mojang.com/browse/MC-111444
// Courtesy of masady
@Mixin(EntityLivingBase.class)
public abstract class UTElytraLandingMixin
{
    @Redirect(method = "travel", at = @At(value = "FIELD", ordinal = 1, target = "Lnet/minecraft/world/World;isRemote:Z"))
    public boolean utElytraLanding(World world)
    {
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTElytraLanding ::: Land elytra");
        return world.isRemote && (!UTConfigBugfixes.ENTITIES.utElytraDeploymentLandingToggle || !((Object) this instanceof EntityPlayerSP));
    }
}