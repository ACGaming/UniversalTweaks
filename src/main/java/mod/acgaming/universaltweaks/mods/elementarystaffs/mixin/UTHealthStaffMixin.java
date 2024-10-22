package mod.acgaming.universaltweaks.mods.elementarystaffs.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;

import de.krokoyt.element.items.HealthStaff;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HealthStaff.class)
public abstract class UTHealthStaffMixin extends Item
{
    @Inject(method = "onLeftClickEntity", at = @At("HEAD"), cancellable = true, remap = false)
    public void utHealthStaff(ItemStack stack, EntityPlayer player, Entity entity, CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfigMods.ELEMENTARY_STAFFS.utESHealthStaffToggle) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTHealthStaff ::: On left click entity");
        if (!(entity instanceof EntityLivingBase))
        {
            cir.setReturnValue(false);
        }
        else
        {
            ((EntityLivingBase) entity).heal(2.0F);
            player.getHeldItem(EnumHand.MAIN_HAND).damageItem(1, player);

            for (int i = 0; i < 5; ++i)
            {
                entity.world.spawnParticle(EnumParticleTypes.HEART, entity.posX + (double) itemRand.nextFloat() - 0.5, entity.posY + 1.0 + (double) itemRand.nextFloat(), entity.posZ + (double) itemRand.nextFloat() - 0.5, (double) (itemRand.nextFloat() - 0.5F), (double) (itemRand.nextFloat() - 0.5F), (double) (itemRand.nextFloat() - 0.5F), new int[0]);
            }

            BlockPos bp = new BlockPos(entity.posX, entity.posY, entity.posZ);
            ResourceLocation location = new ResourceLocation("element", "magic");
            SoundEvent event = new SoundEvent(location);
            player.world.playSound(player, bp, event, SoundCategory.MASTER, 4.0F, 0.0F);
            cir.setReturnValue(true);
        }
    }
}