package mod.acgaming.universaltweaks.mods.forestry.mixin;

import forestry.core.blocks.BlockBase;
import forestry.core.blocks.IBlockType;
import forestry.core.blocks.IMachinePropertiesTesr;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import mod.acgaming.universaltweaks.util.particle.UTParticleSituationEnum;
import mod.acgaming.universaltweaks.util.particle.UTParticleSpawnerMessage;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleDigging;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(BlockBase.class)
public abstract class UTBaseBlockMixin<P extends Enum<P> & IBlockType & IStringSerializable> extends Block
{
    @Shadow(remap = false)
    @Final
    public P blockType;

    public UTBaseBlockMixin(Material materialIn)
    {
        super(materialIn);
    }

    @Override
    public boolean addRunningEffects(IBlockState state, World world, BlockPos pos, Entity entity)
    {
        if (!UTConfigMods.FORESTRY.utParticleFixesToggle)
        {
            return false;
        }
        if (world.isRemote && this.blockType.getMachineProperties() instanceof IMachinePropertiesTesr)
        {
            addRunningEffectsOnClient(world, entity, state, (IMachinePropertiesTesr) this.blockType.getMachineProperties());
        }
        return true;
    }

    @Override
    public boolean addLandingEffects(IBlockState state, WorldServer world, BlockPos pos, IBlockState unused, EntityLivingBase entity, int numberOfParticles)
    {
        if (!UTConfigMods.FORESTRY.utParticleFixesToggle)
        {
            return false;
        }
        UTParticleSpawnerMessage.send(world, EnumParticleTypes.BLOCK_DUST, UTParticleSituationEnum.FORESTRY, entity.posX, entity.posY, entity.posZ, 0.0D, 0.0D, 0.0D, 0.15D, numberOfParticles, getStateId(state));
        return true;
    }

    @SideOnly(Side.CLIENT)
    private void addRunningEffectsOnClient(World world, Entity entity, IBlockState state, IMachinePropertiesTesr tesrProps)
    {
        Minecraft mc = Minecraft.getMinecraft();
        Random rand = world.rand;
        ParticleManager effectRenderer = mc.effectRenderer;
        ParticleDigging particle = (ParticleDigging) effectRenderer.spawnEffectParticle(EnumParticleTypes.BLOCK_CRACK.getParticleID(), entity.posX + ((double) rand.nextFloat() - 0.5D) * (double) entity.width, entity.getEntityBoundingBox().minY + 0.1D, entity.posZ + ((double) rand.nextFloat() - 0.5D) * (double) entity.width, -entity.motionX * 4.0D, 1.5D, -entity.motionZ * 4.0D, new int[] { getStateId(state)} );

        particle.setParticleTexture(mc.modelManager.getTextureMap().getAtlasSprite(tesrProps.getParticleTextureLocation()));
    }
}