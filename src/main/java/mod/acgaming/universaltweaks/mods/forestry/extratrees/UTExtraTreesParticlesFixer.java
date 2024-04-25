package mod.acgaming.universaltweaks.mods.forestry.extratrees;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.registry.IRegistry;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.List;

public class UTExtraTreesParticlesFixer
{
    @SubscribeEvent
    public void onModelBaked(ModelBakeEvent event)
    {
        IRegistry<ModelResourceLocation, IBakedModel> registry = event.getModelRegistry();
        for (ModelResourceLocation key : registry.getKeys())
        {
            if ("extratrees".equals(key.getNamespace()) && "machine".equals(key.getPath()))
            {
                registry.putObject(key, new UTParticleEnabledBakedModel(registry.getObject(key)));
            }
        }
    }

    private static class UTParticleEnabledBakedModel implements IBakedModel
    {
        private final IBakedModel parent;

        private UTParticleEnabledBakedModel(IBakedModel parent)
        {
            this.parent = parent;
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
            return this.parent.getQuads(state, side, rand);
        }

        @Override
        public boolean isAmbientOcclusion() {
            return this.parent.isAmbientOcclusion();
        }

        @Override
        public boolean isGui3d() {
            return this.parent.isGui3d();
        }

        @Override
        public boolean isBuiltInRenderer() {
            return this.parent.isBuiltInRenderer();
        }

        @Override
        public TextureAtlasSprite getParticleTexture() {
            if (UTConfigMods.FORESTRY.utETParticleFixesToggle)
            {
                try
                {
                    return this.getQuads(null, null, 0).get(0).getSprite();
                }
                catch (Throwable t)
                {
                    UniversalTweaks.LOGGER.fatal("Unable to resolve particle texture with parent baked model, swallowing exception and returning broken texture", t);
                }
            }
            return this.parent.getParticleTexture();
        }

        @Override
        public ItemOverrideList getOverrides() {
            return this.parent.getOverrides();
        }

        @Override
        public ItemCameraTransforms getItemCameraTransforms() {
            return this.parent.getItemCameraTransforms();
        }

        @Override
        public boolean isAmbientOcclusion(IBlockState state) {
            return this.parent.isAmbientOcclusion(state);
        }

        @Override
        public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
            return this.parent.handlePerspective(cameraTransformType);
        }
    }
}