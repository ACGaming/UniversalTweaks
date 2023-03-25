package mod.acgaming.universaltweaks.bugfixes.entities.maxhealth.mixin;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// MC-17876
// https://bugs.mojang.com/browse/MC-17876
// Courtesy of Darkhax
@Mixin(EntityLivingBase.class)
public abstract class UTMaxHealthMixin
{
    /**
     * This float is used to temporarily hold the actual health of the entity while the entity data is being
     * deserialized. A null value is used to indicate that the health does not need correcting.
     */
    @Unique
    @Nullable
    public Float actualHealth = null;

    @Shadow
    public abstract float getMaxHealth();

    @Shadow
    public abstract float getHealth();

    @Shadow
    public abstract void setHealth(float health);

    /**
     * The vanilla code will reset the entity health when the deserialized value exceeds {@link
     * EntityLivingBase#getMaxHealth()}. This generally is not an issue, however when entities are initially loaded their
     * max health attribute has not been properly initialized. This is the source of MC-17876.
     * <p>
     * This mixin is used to circumvent this faulty logic by capturing the deserialized value early and storing it in
     * {@link #actualHealth}. This approach is favoured over attempting to initialize attributes early as there is no
     * standard way to do this that would reasonably account for modded attribute sources.
     */
    @Inject(method = "readEntityFromNBT", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;setHealth(F)V"))
    public void utGetHealth(NBTTagCompound compound, CallbackInfo ci)
    {
        if (!UTConfig.BUGFIXES_ENTITIES.utMaxHealthToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTMaxHealth ::: Read entity from NBT");
        final float savedHealth = compound.getFloat("Health");
        if (savedHealth > getMaxHealth() && savedHealth > 0) actualHealth = savedHealth;
    }

    /**
     * This mixin is used to apply the {@link #actualHealth} value after entity equipment has been deserialized and
     * properly applied to the entity. This approach is favoured over directly setting the health during deserialization
     * as it has less potential for de-syncs. An example scenario of concern would be a player saving their game and
     * removing a mod that added the attribute, resulting in too much health.
     */
    @Inject(method = "onUpdate", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityLivingBase;ticksExisted:I"))
    public void utSetHealth(CallbackInfo ci)
    {
        if (!UTConfig.BUGFIXES_ENTITIES.utMaxHealthToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTMaxHealth ::: On update");
        if (actualHealth != null && actualHealth > 0 && actualHealth > this.getHealth())
        {
            this.setHealth(actualHealth);
            actualHealth = null;
        }
    }
}