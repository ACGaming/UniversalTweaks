package mod.acgaming.universaltweaks.tweaks.mixin;

import java.util.List;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleFirework;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.ItemDye;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of SR2610
@Mixin(EntityCreeper.class)
public abstract class UTCreeperConfettiMixin extends EntityMob
{
    public UTCreeperConfettiMixin(World worldIn)
    {
        super(worldIn);
    }

    @Shadow
    public abstract boolean getPowered();

    @Inject(method = "explode", at = @At("HEAD"), cancellable = true)
    public void utCreeperConfetti(CallbackInfo ci)
    {
        if (!UTConfig.TWEAKS_ENTITIES.utCreeperConfettiToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTCreeperConfetti ::: Explode");
        if (this.world.isRemote) spawnParticles(this.getEntityWorld(), this.getPosition(), this.getPowered());
        else
        {
            this.dead = true;
            this.setDead();
            this.spawnLingeringCloud();
        }
        ci.cancel();
    }

    @SideOnly(Side.CLIENT)
    public void spawnParticles(World world, BlockPos pos, boolean powered)
    {
        ParticleManager particleManager = Minecraft.getMinecraft().effectRenderer;
        particleManager.addEffect(new ParticleFirework.Starter(world, pos.getX(), pos.getY() + (powered ? 2.5F : 0.5F), pos.getZ(), 0, 0, 0, particleManager, generateTag(powered)));
    }

    public NBTTagCompound generateTag(boolean powered)
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

    @Shadow
    protected abstract void spawnLingeringCloud();
}