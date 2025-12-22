package mod.acgaming.universaltweaks.mods.vanilla.mixin;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityThrowable.class)
public interface UTEntityThrowableAccessor
{
    @Accessor("thrower")
    void setThrower(EntityLivingBase thower);
}
