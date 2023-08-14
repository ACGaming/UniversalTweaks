package mod.acgaming.universaltweaks.mods.quark.mixin;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.quark.base.sounds.QuarkSounds;
import vazkii.quark.misc.feature.Pickarang;
import vazkii.quark.world.entity.EntityPickarang;

// Courtesy of Focamacho
@Mixin(value = EntityPickarang.class, remap = false)
public abstract class UTEntityPickarangMixin extends EntityThrowable
{
    @Shadow
    @Final
    private static ThreadLocal<Boolean> IS_PICKARANG_UPDATING;

    @Shadow
    @Final
    private static DataParameter<Boolean> RETURNING;

    @Shadow
    private int liveTime;
    @Shadow
    private int slot;

    public UTEntityPickarangMixin(World worldIn)
    {
        super(worldIn);
    }

    @Shadow
    public abstract ItemStack getStack();

    @Shadow
    public abstract int getEfficiencyModifier();

    @Inject(method = "func_70071_h_", at = @At("HEAD"), cancellable = true)
    public void onUpdate(CallbackInfo info)
    {
        IS_PICKARANG_UPDATING.set(true);
        super.onUpdate();
        IS_PICKARANG_UPDATING.set(false);

        if (isDead)
        {
            info.cancel();
            return;
        }

        boolean returning = dataManager.get(RETURNING);
        this.liveTime++;

        if (!returning)
        {
            if (liveTime > Pickarang.timeout)
                this.setReturning();
        }
        else
        {
            noClip = true;

            ItemStack stack = this.getStack();
            int eff = this.getEfficiencyModifier();

            List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, getEntityBoundingBox().grow(2));
            List<EntityXPOrb> xp = world.getEntitiesWithinAABB(EntityXPOrb.class, getEntityBoundingBox().grow(2));

            Vec3d ourPos = getPositionVector();
            for (EntityItem item : items)
            {
                if (item.isRiding())
                    continue;
                item.startRiding(this);

                item.setPickupDelay(2);
            }
            for (EntityXPOrb xpOrb : xp)
            {
                if (xpOrb.isRiding())
                    continue;
                xpOrb.startRiding(this);

                xpOrb.delayBeforeCanPickup = 2;
            }


            EntityLivingBase owner = getThrower();
            if (owner == null || owner.isDead || !(owner instanceof EntityPlayer))
            {
                if (!world.isRemote)
                {
                    entityDropItem(stack, 0);
                    setDead();
                }

                info.cancel();
                return;
            }

            Vec3d ownerPos = owner.getPositionVector().add(0, 1, 0);
            Vec3d motion = ownerPos.subtract(ourPos);
            double motionMag = 3.25 + eff * 0.25;

            if (motion.lengthSquared() < motionMag)
            {
                EntityPlayer player = (EntityPlayer) owner;
                ItemStack stackInSlot = player.inventory.getStackInSlot(this.slot);

                if (!world.isRemote)
                {
                    playSound(QuarkSounds.ENTITY_PICKARANG_PICKUP, 1, 1);

                    if (!stack.isEmpty())
                    {
                        if (!player.isDead && stackInSlot.isEmpty())
                            player.inventory.setInventorySlotContents(slot, stack);
                        else if (player.isDead || !player.inventory.addItemStackToInventory(stack))
                            player.dropItem(stack, false);
                    }

                    if (!player.isDead)
                    {
                        for (EntityItem item : items)
                        {
                            if (!item.isDead)
                            {
                                ItemStack drop = item.getItem();
                                if (!player.addItemStackToInventory(drop))
                                    player.dropItem(drop, false);
                                item.setDead();
                            }
                        }

                        for (EntityXPOrb xpOrb : xp)
                        {
                            if (!xpOrb.isDead) xpOrb.onCollideWithPlayer(player);
                        }

                        for (Entity riding : getPassengers())
                        {
                            if (riding.isDead)
                                continue;

                            if (riding instanceof EntityItem)
                            {
                                ItemStack drop = ((EntityItem) riding).getItem();
                                if (!player.addItemStackToInventory(drop))
                                    player.dropItem(drop, false);
                                riding.setDead();
                            }
                            else if (riding instanceof EntityXPOrb)
                                riding.onCollideWithPlayer(player);
                        }
                    }

                    setDead();
                }
            }
            else
            {
                motion = motion.normalize().scale(0.7 + eff * 0.325F);
                motionX = motion.x;
                motionY = motion.y;
                motionZ = motion.z;
            }
        }

        info.cancel();
    }

    @Shadow
    protected abstract void setReturning();
}