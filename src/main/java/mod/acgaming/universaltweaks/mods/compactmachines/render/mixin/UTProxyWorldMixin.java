package mod.acgaming.universaltweaks.mods.compactmachines.mixin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

import mod.acgaming.universaltweaks.mods.compactmachines.render.DummyWorld;
import org.dave.compactmachines3.world.ProxyWorld;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin to avoid client memory leak when rendering miniaturization recipes.
 * Also reduces memory usage when Alfheim Lighting Engine is present.
 * <br>
 * Adapted from MMCE/Multiblocked's DummyWorld.
 * @author jchung01
 */
@Mixin(value = ProxyWorld.class)
public abstract class UTProxyWorldMixin extends World
{
    @Mutable
    @Shadow(remap = false)
    @Final
    private World realWorld;

    protected UTProxyWorldMixin(ISaveHandler saveHandlerIn, WorldInfo info, WorldProvider providerIn, Profiler profilerIn, boolean client)
    {
        super(saveHandlerIn, info, providerIn, profilerIn, client);
    }

    @Redirect(method = "<init>*", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getWorldInfo()Lnet/minecraft/world/storage/WorldInfo;", remap = true), remap = false)
    private static WorldInfo utDummyWorldInfo(WorldClient instance)
    {
        return new WorldInfo(DummyWorld.DEFAULT_SETTINGS, "Dummy");
    }

    @Redirect(method = "<init>*", at = @At(value = "FIELD", target = "Lnet/minecraft/client/multiplayer/WorldClient;provider:Lnet/minecraft/world/WorldProvider;", remap = true), remap = false)
    private static WorldProvider utDummyWorldProvider(WorldClient instance)
    {
        return new WorldProviderSurface();
    }

    @Redirect(method = "<init>*", at = @At(value = "FIELD", target = "Lnet/minecraft/client/multiplayer/WorldClient;profiler:Lnet/minecraft/profiler/Profiler;", remap = true), remap = false)
    private static Profiler utDummyProfiler(WorldClient instance)
    {
        return new Profiler();
    }

    @Inject(method = "<init>*", at = @At(value = "TAIL"), remap = false)
    private void utSetupDummyWorld(CallbackInfo ci)
    {
        // Guarantee the dimension ID was not reset by the provider
        this.provider.setDimension(Integer.MAX_VALUE - 1024);
        int providerDim = this.provider.getDimension();
        this.provider.setWorld(this);
        this.provider.setDimension(providerDim);
        this.chunkProvider = this.createChunkProvider();
        this.calculateInitialSkylight();
        this.calculateInitialWeather();
        this.getWorldBorder().setSize(30000000);
        ObfuscationReflectionHelper.setPrivateValue(World.class, this, null, FMLLaunchHandler.isDeobfuscatedEnvironment() ? "lightUpdateBlockList" : "field_72994_J");
        // De-allocate alfheim lighting engine
        if (DummyWorld.isAlfheimLoaded) {
            ObfuscationReflectionHelper.setPrivateValue(World.class, this, null,
                "alfheim$lightingEngine");
        }
        this.realWorld = null;
    }

    /**
     * @author jchung01
     * @reason Don't use realWorld
     */
    @Nonnull
    @Overwrite
    protected IChunkProvider createChunkProvider()
    {
        return new DummyWorld.DummyChunkProvider(this);
    }

    @Override
    protected void initCapabilities() {
        //NOOP - do not trigger forge events
    }

    @Override
    public void notifyNeighborsRespectDebug(@Nonnull BlockPos pos, @Nonnull Block blockType, boolean p_175722_3_) {
        //NOOP - do not trigger forge events
    }

    @Override
    public void notifyNeighborsOfStateChange(@Nonnull BlockPos pos, @Nonnull Block blockType, boolean updateObservers) {
        //NOOP - do not trigger forge events
    }

    @Override
    public void notifyNeighborsOfStateExcept(@Nonnull BlockPos pos, @Nonnull Block blockType, @Nonnull EnumFacing skipSide) {
        //NOOP - do not trigger forge events
    }

    @Override
    public void markAndNotifyBlock(@Nonnull BlockPos pos, @Nullable Chunk chunk, @Nonnull IBlockState iblockstate, @Nonnull IBlockState newState, int flags) {
        //NOOP - do not trigger forge events
    }

    @Override
    public void notifyBlockUpdate(@Nonnull BlockPos pos, @Nonnull IBlockState oldState, @Nonnull IBlockState newState, int flags) {
    }

    @Override
    public void markBlockRangeForRenderUpdate(@Nonnull BlockPos rangeMin, @Nonnull BlockPos rangeMax) {
    }

    @Override
    public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {
    }

    @Override
    public void updateObservingBlocksAt(@Nonnull BlockPos pos, @Nonnull Block blockType) {
    }

    @Override
    // De-allocated lightUpdateBlockList, default return
    public boolean checkLightFor(@Nonnull EnumSkyBlock lightType, @Nonnull BlockPos pos) {
        return true;
    }

    @Nonnull
    @Override
    @Optional.Method(modid = "alfheim")
    public World init() {
        return this;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    @Optional.Method(modid = "alfheim")
    public int getLightFromNeighborsFor(EnumSkyBlock type, BlockPos pos) {
        return 15;
    }

    @SuppressWarnings({"MissingUnique", "unused"})
    @Optional.Method(modid = "alfheim")
    public int alfheim$getLight(BlockPos pos, boolean checkNeighbors) {
        return 15;
    }
}
