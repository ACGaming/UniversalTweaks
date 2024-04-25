package mod.acgaming.universaltweaks.util.particle;

import forestry.core.blocks.BlockBase;
import forestry.core.blocks.IBlockType;
import forestry.core.blocks.IMachinePropertiesTesr;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleBlockDust;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * {@link UTParticleSpawnerMessage} uses this enum to understand which particle situation we are handling
 * This should not differ on server and client!
 * The packet currently handles this enum in bytes, if the elements in this enum do exceed max byte
 * change the buffer writing and reading to a varInt
 */
public enum UTParticleSituationEnum
{
    FORESTRY;

    public static final UTParticleSituationEnum[] VALUES = values();

    @SideOnly(Side.CLIENT)
    public void spawn(EnumParticleTypes particleType, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters)
    {
        switch (this.ordinal())
        {
            case 0:
                forestry$spawn(particleType, ignoreRange, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
                break;
            default:
                Minecraft.getMinecraft().world.spawnParticle(particleType, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
        }
    }

    @SideOnly(Side.CLIENT)
    public void forestry$spawn(EnumParticleTypes particleType, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters)
    {
        ParticleManager effectRenderer = Minecraft.getMinecraft().effectRenderer;
        ParticleBlockDust particle = (ParticleBlockDust) effectRenderer.spawnEffectParticle(particleType.getParticleID(), xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);

        IBlockState state = Block.getStateById(parameters[0]);
        Block block = state.getBlock();
        if (block instanceof BlockBase)
        {
            BlockBase blockBase = (BlockBase) block;
            if (blockBase.blockType instanceof IBlockType)
            {
                IBlockType type = (IBlockType) blockBase.blockType;
                if (type.getMachineProperties() instanceof IMachinePropertiesTesr)
                {
                    particle.setParticleTexture(getTexture(((IMachinePropertiesTesr) type.getMachineProperties()).getParticleTextureLocation()));
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite getTextureFromState(IBlockState state)
    {
        Minecraft minecraft = Minecraft.getMinecraft();
        BlockRendererDispatcher blockRendererDispatcher = minecraft.getBlockRendererDispatcher();
        BlockModelShapes blockModelShapes = blockRendererDispatcher.getBlockModelShapes();
        return blockModelShapes.getTexture(state);
    }

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite getTexture(String textureLocation)
    {
        return Minecraft.getMinecraft().modelManager.getTextureMap().getAtlasSprite(textureLocation);
    }

}