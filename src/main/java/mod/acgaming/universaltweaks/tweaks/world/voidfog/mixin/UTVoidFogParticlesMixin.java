package mod.acgaming.universaltweaks.tweaks.world.voidfog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.tweaks.world.voidfog.UTVoidFog;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldClient.class)
public abstract class UTVoidFogParticlesMixin extends World
{
    @Shadow @Final private Minecraft mc;
    @Unique private boolean ut$shouldRender;

    protected UTVoidFogParticlesMixin(ISaveHandler saveHandlerIn, WorldInfo info, WorldProvider providerIn, Profiler profilerIn, boolean client)
    {
        super(saveHandlerIn, info, providerIn, profilerIn, client);
    }

    /**
     * Injects ported 1.7.10 void fog particles into the game world based on configuration settings and player state.
     *
     * @param posX            x-coordinate of the player position
     * @param posY            y-coordinate of the player position
     * @param posZ            z-coordinate of the player position
     * @param ci              CallbackInfo object
     * @param mutableBlockPos mutable block position already used for barrier particles
     */
    @Inject(method = "doVoidFogParticles", at = @At("TAIL"))
    public void utVoidFogParticles(int posX, int posY, int posZ, CallbackInfo ci, @Local BlockPos.MutableBlockPos mutableBlockPos)
    {
        // Only update conditions every second
        if (this.mc.player.ticksExisted < 20 || this.mc.player.ticksExisted % 20 == 19)
        {
            ut$shouldRender = false;
            if (!UTVoidFog.isEnabledForDimension(this.mc.player.dimension)) return;
            if (!UTConfigTweaks.WORLD.VOID_FOG.utVoidParticlesSuperflat && this.mc.world.getWorldInfo().getTerrainType() == WorldType.FLAT) return;
            if (!UTConfigTweaks.WORLD.VOID_FOG.utVoidParticlesCreativeSpectator && (this.mc.player.isCreative() || this.mc.player.isSpectator())) return;
            if (posY > UTConfigTweaks.WORLD.VOID_FOG.utVoidParticleSpawnYLevel) return;
            ut$shouldRender = true;
        }

        if (!ut$shouldRender) return;

        byte b0 = 16;

        for (int l = 0; l < UTConfigTweaks.WORLD.VOID_FOG.utVoidParticleSpawnIterations; ++l)
        {
            int i1 = MathHelper.floor(posX) + this.rand.nextInt(b0) - this.rand.nextInt(b0);
            int j1 = MathHelper.floor(posY) + this.rand.nextInt(b0) - this.rand.nextInt(b0);
            int k1 = MathHelper.floor(posZ) + this.rand.nextInt(b0) - this.rand.nextInt(b0);
            IBlockState blockState = this.getBlockState(mutableBlockPos);

            if (blockState.getMaterial() == Material.AIR && this.rand.nextInt(8) > j1)
            {
                this.spawnParticle(EnumParticleTypes.SUSPENDED_DEPTH, (float) i1 + this.rand.nextFloat(), (float) j1 + this.rand.nextFloat(), (float) k1 + this.rand.nextFloat(), 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
