package mod.acgaming.universaltweaks.bugfixes.entities.arrow.mixin;

import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;

import java.util.Set;

import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// MC-107941
// https://bugs.mojang.com/browse/MC-107941
// Courtesy of fonnymunkey
@Mixin(EntityTippedArrow.class)
public abstract class UTEntityTippedArrowMixin
{
    @Shadow
    private PotionType potion; 
    @Shadow
    @Final
    private Set<PotionEffect> customPotionEffects;

    @Redirect(method = "refreshColor", at = @At(value = "INVOKE", target = "Ljava/lang/Integer;valueOf(I)Ljava/lang/Integer;"))
    public Integer rlmixins_vanillaEntityTippedArrow_refreshColor(int i)
    {
        if (UTConfigBugfixes.ENTITIES.utUntippedArrowParticlesToggle && potion == PotionTypes.EMPTY && customPotionEffects.isEmpty()) return -1;
        return i;
    }
}