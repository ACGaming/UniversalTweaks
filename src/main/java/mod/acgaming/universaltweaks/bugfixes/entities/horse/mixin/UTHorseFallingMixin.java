package mod.acgaming.universaltweaks.bugfixes.entities.horse.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of Nova-Committee
@Mixin(AbstractHorse.class)
public abstract class UTHorseFallingMixin extends EntityAnimal
{
    protected UTHorseFallingMixin(World worldIn)
    {
        super(worldIn);
    }

    @Inject(method = "fall", at = @At("HEAD"), cancellable = true)
    public void utHorseFalling(float distance, float damageMultiplier, CallbackInfo ci)
    {
        if (!UTConfigBugfixes.ENTITIES.utHorseFallingToggle) return;
        ci.cancel();
        final float[] ret = ForgeHooks.onLivingFall(this, distance, damageMultiplier);
        if (ret == null) return;
        distance = ret[0];
        damageMultiplier = ret[1];
        if (distance > 1.0F) this.playSound(SoundEvents.ENTITY_HORSE_LAND, 0.4F, 1.0F);
        final PotionEffect potioneffect = this.getActivePotionEffect(MobEffects.JUMP_BOOST);
        final float f = potioneffect == null ? 0.0F : (float) (potioneffect.getAmplifier() + 1);
        final int i = MathHelper.ceil((distance * 0.5F - 3.0F - f) * damageMultiplier);
        if (i <= 0) return;
        this.attackEntityFrom(DamageSource.FALL, i);
        if (this.isBeingRidden()) for (Entity entity : this.getRecursivePassengers()) entity.attackEntityFrom(DamageSource.FALL, i);
        final BlockPos pos = new BlockPos(this.posX, this.posY - 0.2D - this.prevRotationYaw, this.posZ);
        final IBlockState state = this.world.getBlockState(pos);
        final Block block = state.getBlock();
        if (state.getMaterial() != Material.AIR && !this.isSilent())
        {
            final SoundType soundtype = block.getSoundType(state, world, pos, this);
            this.world.playSound(null, this.posX, this.posY, this.posZ, soundtype.getStepSound(), this.getSoundCategory(), soundtype.getVolume() * 0.5F, soundtype.getPitch() * 0.75F);
        }
    }
}