package mod.acgaming.universaltweaks.tweaks.misc.armorcurve.mixin;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.tweaks.misc.armorcurve.UTArmorCurve;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

// Courtesy of Jackiecrazy
@Mixin(ItemStack.class)
public abstract class UTArmorDegradationMixin
{
    @Unique
    private static final Cache<ItemStack, ImmutableMultimap<String, AttributeModifier>> CACHE = CacheBuilder.newBuilder().weakKeys().expireAfterAccess(1, TimeUnit.SECONDS).build();

    @Shadow
    public abstract Item getItem();

    @Shadow
    public abstract boolean isEmpty();

    @Shadow
    public abstract int getItemDamage();

    @Shadow
    public abstract int getMaxDamage();

    @Inject(cancellable = true, at = @At("RETURN"), method = "getAttributeModifiers", locals = LocalCapture.CAPTURE_FAILSOFT)
    private void utGetAttributeModifiers(EntityEquipmentSlot equipmentSlot, CallbackInfoReturnable<Multimap<String, AttributeModifier>> info, Multimap<String, AttributeModifier> m)
    {
        if (!this.isEmpty() && (equipmentSlot != EntityEquipmentSlot.MAINHAND && equipmentSlot != EntityEquipmentSlot.OFFHAND) && this.getItem().isDamageable())
        {
            ImmutableMultimap<String, AttributeModifier> cached = CACHE.getIfPresent((ItemStack) (Object) this);
            if (cached != null) info.setReturnValue(cached);
            ImmutableMultimap.Builder<String, AttributeModifier> copy = ImmutableMultimap.builder();
            if (UTArmorCurve.degrade == null || UTConfigTweaks.MISC.ARMOR_CURVE.utArmorCurveDegradation.equals("1")) return;
            float retDegrade = UTArmorCurve.degrade.with("remaining", BigDecimal.valueOf(this.getMaxDamage() - this.getItemDamage())).and("max", BigDecimal.valueOf(this.getMaxDamage())).eval().floatValue();
            if (UTConfigTweaks.MISC.ARMOR_CURVE.utArmorCurveLogging) UniversalTweaks.LOGGER.info("UTArmorCurve ::: Armor Degradation: " + retDegrade);
            for (String e : m.keySet())
                for (AttributeModifier eam : m.get(e))
                {
                    AttributeModifier degradedEAM = new AttributeModifier(eam.getID(), eam.getName(), (retDegrade) * eam.getAmount(), eam.getOperation());
                    copy.put(e, degradedEAM);
                }
            cached = copy.build();
            CACHE.put((ItemStack) (Object) this, cached);
            info.setReturnValue(cached);
        }
    }
}