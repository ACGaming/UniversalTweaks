package mod.acgaming.universaltweaks.mods.incontrol.mixin;

import mcjty.tools.rules.RuleBase;
import mod.acgaming.universaltweaks.mods.incontrol.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RuleBase.class, remap = false)
public class UTRuleBaseMixin
{
    @Inject(method = "lambda$addHealthAction$23", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/SharedMonsterAttributes;MAX_HEALTH:Lnet/minecraft/entity/ai/attributes/IAttribute;", remap = true), cancellable = true)
    private static void utCheckHealthTag(float m, float a, RuleBase.EventGetter event, CallbackInfo ci)
    {
        if (event.getEntityLiving().getTags().contains(Attributes.HEALTH.getTag())) ci.cancel();
    }

    @Inject(method = "lambda$addHealthAction$23", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;setHealth(F)V", shift = At.Shift.AFTER, remap = true))
    private static void utAddHealthTag(float m, float a, RuleBase.EventGetter event, CallbackInfo ci)
    {
        event.getEntityLiving().addTag(Attributes.HEALTH.getTag());
    }

    @Inject(method = "lambda$addSpeedAction$24", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/SharedMonsterAttributes;MOVEMENT_SPEED:Lnet/minecraft/entity/ai/attributes/IAttribute;", remap = true), cancellable = true)
    private static void utCheckSpeedTag(float m, float a, RuleBase.EventGetter event, CallbackInfo ci)
    {
        if (event.getEntityLiving().getTags().contains(Attributes.SPEED.getTag())) ci.cancel();
    }

    @Inject(method = "lambda$addSpeedAction$24", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/attributes/IAttributeInstance;setBaseValue(D)V", shift = At.Shift.AFTER, remap = true))
    private static void utAddSpeedTag(float m, float a, RuleBase.EventGetter event, CallbackInfo ci)
    {
        event.getEntityLiving().addTag(Attributes.SPEED.getTag());
    }

    @Inject(method = "lambda$addDamageAction$26", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/SharedMonsterAttributes;ATTACK_DAMAGE:Lnet/minecraft/entity/ai/attributes/IAttribute;", remap = true), cancellable = true)
    private static void utCheckDamageTag(float m, float a, RuleBase.EventGetter event, CallbackInfo ci)
    {
        if (event.getEntityLiving().getTags().contains(Attributes.DAMAGE.getTag())) ci.cancel();
    }

    @Inject(method = "lambda$addDamageAction$26", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/attributes/IAttributeInstance;setBaseValue(D)V", shift = At.Shift.AFTER, remap = true))
    private static void utAddDamageTag(float m, float a, RuleBase.EventGetter event, CallbackInfo ci)
    {
        event.getEntityLiving().addTag(Attributes.DAMAGE.getTag());
    }
}
