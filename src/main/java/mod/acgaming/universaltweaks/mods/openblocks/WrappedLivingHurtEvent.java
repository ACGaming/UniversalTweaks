package mod.acgaming.universaltweaks.mods.openblocks;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

/**
 * Same as LivingHurtEvent, but only fired manually by the last stand handler.
 * @author jchung01
 */
public class WrappedLivingHurtEvent extends LivingHurtEvent
{
    public WrappedLivingHurtEvent(EntityLivingBase entity, DamageSource source, float amount)
    {
        super(entity, source, amount);
    }
}
