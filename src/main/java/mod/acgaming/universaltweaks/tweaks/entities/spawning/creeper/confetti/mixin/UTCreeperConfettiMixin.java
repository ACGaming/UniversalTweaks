package mod.acgaming.universaltweaks.tweaks.entities.spawning.creeper.confetti.mixin;

import java.util.List;

import com.google.common.collect.Lists;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.ItemDye;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.util.UTRandomUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of SR2610, jchung01
@Mixin(EntityCreeper.class)
public abstract class UTCreeperConfettiMixin extends EntityMob
{
    @SuppressWarnings("WrongEntityDataParameterClass")
    @Unique
    private static final DataParameter<Boolean> ut$CONFETTI = EntityDataManager.createKey(EntityCreeper.class, DataSerializers.BOOLEAN);

    protected UTCreeperConfettiMixin(World worldIn)
    {
        super(worldIn);
    }

    @Shadow
    public abstract boolean getPowered();

    @Shadow
    protected abstract void spawnLingeringCloud();

    /**
     * Register confetti DataParameter.
     *
     * @author jchung01
     */
    @Inject(method = "entityInit", at = @At(value = "TAIL"))
    private void utRegisterConfetti(CallbackInfo ci)
    {
        this.dataManager.register(ut$CONFETTI, Boolean.FALSE);
    }

    /**
     * Determine on spawn if this creeper should explode into confetti.
     *
     * @author jchung01
     */
    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void utInitConfetti(World worldIn, CallbackInfo ci)
    {
        if (worldIn == null || worldIn.isRemote) return;
        double chargedChance = UTConfigTweaks.ENTITIES.CREEPER_CONFETTI.utCreeperConfettiChance;
        if (chargedChance > 0.0D && UTRandomUtil.chance(chargedChance, this.rand))
        {
            this.dataManager.set(ut$CONFETTI, Boolean.TRUE);
        }
    }

    @Inject(method = "explode", at = @At("HEAD"), cancellable = true)
    private void utDoConfetti(CallbackInfo ci)
    {
        if (!this.dataManager.get(ut$CONFETTI)) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTCreeperConfetti ::: Explode");
        if (this.world.isRemote)
        {
            BlockPos pos = this.getPosition();
            boolean powered = this.getPowered();
            this.world.makeFireworks(pos.getX(), pos.getY() + (powered ? 2.5F : 0.5F), pos.getZ(), 0, 0, 0, ut$generateTag(powered));
        }
        else
        {
            this.dead = true;
            this.setDead();
            this.spawnLingeringCloud();
            this.ut$damagePlayers(((EntityCreeper) (Object) this));
        }
        ci.cancel();
    }

    @Unique
    private NBTTagCompound ut$generateTag(boolean powered)
    {
        NBTTagCompound fireworkTag = new NBTTagCompound();
        NBTTagCompound fireworkItemTag = new NBTTagCompound();
        NBTTagList nbttaglist = new NBTTagList();
        List<Integer> list = Lists.newArrayList();
        list.add(ItemDye.DYE_COLORS[1]);
        list.add(ItemDye.DYE_COLORS[4]);
        list.add(ItemDye.DYE_COLORS[11]);
        for (int i = 0; i < rand.nextInt(3) + 3; i++) list.add(ItemDye.DYE_COLORS[rand.nextInt(15)]);
        int[] colours = new int[list.size()];
        for (int j = 0; j < colours.length; j++) colours[j] = list.get(j);
        fireworkTag.setIntArray("Colors", colours);
        fireworkTag.setBoolean("Flicker", true);
        fireworkTag.setByte("Type", (byte) (powered ? 3 : 4));
        nbttaglist.appendTag(fireworkTag);
        fireworkItemTag.setTag("Explosions", nbttaglist);
        return fireworkItemTag;
    }

    @Unique
    private void ut$damagePlayers(EntityCreeper creeper)
    {
        float explosionStrength = (float) (creeper.getPowered() ? UTConfigTweaks.ENTITIES.CREEPER_CONFETTI.utCreeperConfettiDamage * 2 : UTConfigTweaks.ENTITIES.CREEPER_CONFETTI.utCreeperConfettiDamage);
        Explosion explosion = new Explosion(creeper.getEntityWorld(), creeper, creeper.posX, creeper.posY, creeper.posZ, 3.0F * explosionStrength, false, false);
        explosion.doExplosionA();
    }
}