package mod.acgaming.universaltweaks.mods.extrautilities.deepdarkstats.mixin;

import com.rwtema.extrautils2.dimensions.deep_dark.WorldProviderDeepDark;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.UUID;

// Courtesy of WaitingIdly
@Mixin(value = WorldProviderDeepDark.class, remap = false)
public abstract class UTDeepDarkStatRunawayFix
{
    @Unique
    private static final UUID HEALTH_MODIFIER_ID = UUID.fromString("e12a4c74-5fe0-4b33-8461-d0320345eb61");
    @Unique
    private static final AttributeModifier HEALTH_MODIFIER = new AttributeModifier(HEALTH_MODIFIER_ID, "Deep Dark Health Doubling", 1, 2);
    @Unique
    private static final UUID ATTACK_MODIFIER_ID = UUID.fromString("468afd07-b07b-4031-ba34-6fa29e154569");
    @Unique
    private static final AttributeModifier ATTACK_MODIFIER = new AttributeModifier(ATTACK_MODIFIER_ID, "Deep Dark Attack Doubling", 1, 2);

    @Redirect(method = "noMobs", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/attributes/IAttributeInstance;setBaseValue(D)V", ordinal = 0, remap = true))
    private static void utFixMaxHealth(IAttributeInstance attributeInstance, double original)
    {
        if (!UTConfigMods.EXTRA_UTILITIES.utDeepDarkStats)
        {
            attributeInstance.setBaseValue(original);
            return;
        }
        attributeInstance.removeModifier(HEALTH_MODIFIER);
        attributeInstance.applyModifier(HEALTH_MODIFIER);
    }

    @Redirect(method = "noMobs", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/attributes/IAttributeInstance;setBaseValue(D)V", ordinal = 1, remap = true))
    private static void utFixAttackDamage(IAttributeInstance attributeInstance, double original)
    {
        if (!UTConfigMods.EXTRA_UTILITIES.utDeepDarkStats)
        {
            attributeInstance.setBaseValue(original);
            return;
        }
        attributeInstance.removeModifier(ATTACK_MODIFIER);
        attributeInstance.applyModifier(ATTACK_MODIFIER);
    }
}
