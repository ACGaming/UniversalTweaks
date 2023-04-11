package mod.acgaming.universaltweaks.mods.elementarystaffs.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import de.krokoyt.element.items.ElectricStaff;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ElectricStaff.class)
public abstract class UTElectricStaffMixin extends Item
{
    @Inject(method = "onItemRightClick", at = @At("HEAD"), cancellable = true)
    public void utElectricStaff(World worldIn, EntityPlayer p, EnumHand handIn, CallbackInfoReturnable<ActionResult<ItemStack>> cir)
    {
        if (!UTConfig.MOD_INTEGRATION.ELEMENTARY_STAFFS.utESElectricStaffToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTElectricStaff ::: On item right click");
        double x = -Math.sin(Math.toRadians(p.rotationYaw));
        double z = Math.cos(Math.toRadians(p.rotationYaw));
        double y = -Math.sin(Math.toRadians(p.rotationPitch));
        x *= 1.0D - Math.abs(y);
        z *= 1.0D - Math.abs(y);
        p.motionX = x * 2.0D;
        p.motionY = y * 1.5D;
        p.motionZ = z * 2.0D;
        p.fallDistance = -25.0F;
        for (int i = 0; i < 3; i++) worldIn.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, p.posX, p.posY + 0.5D, p.posZ, worldIn.rand.nextGaussian() * 0.05D, -p.motionY * 0.5D, worldIn.rand.nextGaussian() * 0.05D);
        worldIn.playSound(p, p.posX, p.posY, p.posZ, SoundEvents.ENTITY_FIREWORK_LAUNCH, SoundCategory.AMBIENT, 4.0F, (1.0F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.2F) * 0.7F);
        p.swingArm(handIn);
        p.getHeldItem(handIn).damageItem(1, p);
        cir.setReturnValue(super.onItemRightClick(worldIn, p, handIn));
    }
}