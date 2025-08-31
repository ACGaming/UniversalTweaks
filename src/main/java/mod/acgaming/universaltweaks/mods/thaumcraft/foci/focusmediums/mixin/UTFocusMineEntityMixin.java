package mod.acgaming.universaltweaks.mods.thaumcraft.foci.focusmediums.mixin;

import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thaumcraft.api.casters.FocusEffect;
import thaumcraft.common.entities.projectile.EntityFocusMine;
import thaumcraft.common.lib.SoundsTC;

// Courtesy of Turkey9002
@Mixin(EntityFocusMine.class)
public abstract class UTFocusMineEntityMixin extends EntityThrowable
{
    @Shadow(remap = false)
    public int counter;

    @Shadow(remap = false)
    FocusEffect[] effects;

    public UTFocusMineEntityMixin(World worldIn)
    {
        super(worldIn);
    }

    @Inject(method = "onUpdate", at = @At(value = "HEAD"))
    public void utUpdateSounds(CallbackInfo ci)
    {
        if (!UTConfigMods.THAUMCRAFT_FOCI.FOCUS_MEDIUMS.utTCMineMediumSoundToggle) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTMediumMineFocus ::: On update");
        try
        {
            if (this.ticksExisted > 1200 || (!this.world.isRemote && this.getThrower() == null))
            {
                this.playSound(SoundsTC.craftfail, 1.0F, 1.0F + (rand.nextFloat() * 0.5F));
            }

            if (this.isEntityAlive())
            {
                if (this.counter == 1 && this.effects == null)
                {
                    this.playSound(SoundsTC.hhoff, 1.0F, 1.0F + (rand.nextFloat() * 0.5F));
                }
            }
        }
        catch (Exception ignored) {}
    }

    @Inject(method = "onImpact", at = @At(value = "HEAD"))
    protected void utImpactSound(final RayTraceResult mop, CallbackInfo ci)
    {
        if (!UTConfigMods.THAUMCRAFT_FOCI.FOCUS_MEDIUMS.utTCMineMediumSoundToggle) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTMediumMineFocus ::: On impact");
        try
        {
            if (this.counter > 0)
            {
                this.playSound(SoundsTC.ticks, 1.0F, 1.0F + (rand.nextFloat() * 0.5F));
            }
        }
        catch (Exception ignored) {}
    }
}