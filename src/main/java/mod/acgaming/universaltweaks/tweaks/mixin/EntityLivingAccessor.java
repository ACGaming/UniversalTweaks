package mod.acgaming.universaltweaks.tweaks.mixin;

import net.minecraft.entity.EntityLiving;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityLiving.class)
public interface EntityLivingAccessor
{
    @Accessor("persistenceRequired")
    boolean getPersistenceRequired();

    @Accessor("persistenceRequired")
    void setPersistenceRequired(boolean persistenceRequired);
}