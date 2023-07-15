package mod.acgaming.universaltweaks.mods.thefarlanders.mixin;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import com.legacy.farlanders.entity.hostile.EntityLooter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// Courtesy of Focamacho
@Mixin(value = EntityLooter.class, remap = false)
public abstract class UTEntityLooterMixin extends EntityMob
{
    public UTEntityLooterMixin(World worldIn)
    {
        super(worldIn);
    }

    @Shadow
    public abstract boolean getHasSword();

    @Shadow
    public abstract void setHasSword(boolean hasSword);

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (source.getImmediateSource() instanceof EntityPlayer && !source.isCreativePlayer() && !source.getDamageType().equalsIgnoreCase("thorns"))
        {
            EntityPlayer ep = (EntityPlayer) source.getImmediateSource();

            if (!this.getHasSword())
            {
                ep.inventory.getCurrentItem();
                if (!ep.inventory.getCurrentItem().isEmpty())
                {
                    if (ep.inventory.getCurrentItem().getItem() instanceof ItemSword)
                    {
                        ItemStack copy = ep.getHeldItemMainhand().copy();
                        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, copy);
                        ep.inventory.getStackInSlot(ep.inventory.currentItem).setItemDamage(ep.inventory.getCurrentItem().getMaxDamage() + 1);
                        this.setHasSword(true);
                        this.world.playSound(ep, this.getPosition(), SoundEvents.ENTITY_CHICKEN_EGG, SoundCategory.HOSTILE, 1.0F, 1.0F);
                        ItemStack var2 = this.getHeldItem(EnumHand.MAIN_HAND);
                        double var3 = 1.5D;
                        try
                        {
                            var3 += ((ItemSword) var2.getItem()).getAttackDamage();
                        }
                        catch (Exception e)
                        {
                            var3 = 3.0F;
                        }
                        finally
                        {
                            this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(var3);
                        }
                    }
                }
            }
        }
        return super.attackEntityFrom(source, amount);
    }
}