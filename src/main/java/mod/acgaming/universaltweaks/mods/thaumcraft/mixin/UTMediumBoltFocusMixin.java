package mod.acgaming.universaltweaks.mods.thaumcraft.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import thaumcraft.api.casters.Trajectory;
import thaumcraft.common.items.casters.foci.FocusMediumBolt;
import thaumcraft.common.items.casters.foci.FocusMediumTouch;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.lib.utils.EntityUtils;

// Courtesy of Turkey9002
@Mixin(FocusMediumBolt.class)
public class UTMediumBoltFocusMixin extends FocusMediumTouch
{
    @Inject(method = "execute", at = @At(value = "HEAD"), cancellable = true, remap = false)
	public void utMediumBoltFocusSound(final Trajectory trajectory, CallbackInfoReturnable<Boolean> cir)
	{
        if (!UTConfig.MOD_INTEGRATION.THAUMCRAFT.utTCBoltMediumSoundToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTMediumBoltFocus ::: Execute");
		final float range = 16.0F;
        Vec3d end = trajectory.direction.normalize();
		RayTraceResult ray = EntityUtils.getPointedEntityRay(this.getPackage().world, (Entity)this.getPackage().getCaster(), trajectory.source, end, 0.25F, range, 0.25F, false);
		if (ray == null || ray != null) 
		{
			this.getPackage().world.playSound((EntityPlayer)null, this.getPackage().getCaster().getPosition().up(), SoundsTC.shock, SoundCategory.PLAYERS, 0.175F, 1.0F + 
	        		(float)(this.getPackage().getCaster().world.rand.nextGaussian() * 0.05F));
        }
    }
}
