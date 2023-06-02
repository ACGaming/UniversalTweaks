package mod.acgaming.universaltweaks.tweaks.entities.damage.velocity.mixin;

import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityLivingBase.class)
public class UTDamageVelocityMixin
{
    @WrapWithCondition(method = "attackEntityFrom", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;markVelocityChanged()V"))
    public boolean utDamageVelocity(EntityLivingBase entity, DamageSource source, float amount)
    {
        boolean isWhitelist = UTConfig.TWEAKS_ENTITIES.DAMAGE_VELOCITY.utDamageVelocityListMode == UTConfig.EnumLists.WHITELIST;
        List<String> damageTypes = Arrays.asList(UTConfig.TWEAKS_ENTITIES.DAMAGE_VELOCITY.utDamageVelocityList);
        return damageTypes.contains(source.getDamageType()) == isWhitelist;
    }
}