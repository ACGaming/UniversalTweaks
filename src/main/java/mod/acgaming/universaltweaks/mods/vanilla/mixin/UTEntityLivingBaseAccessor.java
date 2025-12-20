package mod.acgaming.universaltweaks.mods.vanilla.mixin;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityLivingBase.class)
public interface UTEntityLivingBaseAccessor
{
    @Accessor("activeItemStack")
    void setActiveItemStack(ItemStack stack);

    @Invoker("canBlockDamageSource")
    boolean callCanBlockDamageSource(DamageSource damageSource);
}
