package mod.acgaming.universaltweaks.mods.thaumcraft.mixin;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thaumcraft.common.entities.monster.EntityFireBat;

@Mixin(EntityFireBat.class)
public class UTFirebatParticlesMixin extends EntityMob
{
    public UTFirebatParticlesMixin(World worldIn)
    {
        super(worldIn);
    }

    @Inject(method = "onLivingUpdate", at = @At(value = "HEAD"))
    public void utFirebatParticles(CallbackInfo ci)
    {
        if (!UTConfig.mods.utTCFirebatParticlesToggle) return;
        if (this.world.isRemote)
        {
            this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.prevPosX + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F, this.prevPosY + this.height / 2 + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F, this.prevPosZ + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F, 0, 0, 0);
            this.world.spawnParticle(EnumParticleTypes.FLAME, this.prevPosX + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F, this.prevPosY + this.height / 2 + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F, this.prevPosZ + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F, 0, 0, 0);
        }
    }
}