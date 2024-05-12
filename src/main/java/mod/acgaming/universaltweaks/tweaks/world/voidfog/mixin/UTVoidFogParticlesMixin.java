package mod.acgaming.universaltweaks.tweaks.world.voidfog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldClient.class)
public abstract class UTVoidFogParticlesMixin extends World
{
    protected UTVoidFogParticlesMixin(ISaveHandler saveHandlerIn, WorldInfo info, WorldProvider providerIn, Profiler profilerIn, boolean client)
    {
        super(saveHandlerIn, info, providerIn, profilerIn, client);
    }

    @Inject(method = "doVoidFogParticles", at = @At("TAIL"))
    public void utVoidFogParticles(int posX, int posY, int posZ, CallbackInfo ci, @Local BlockPos.MutableBlockPos mutableBlockPos)
    {
        if (posY > 8) return;

        byte b0 = 16;

        for (int l = 0; l < 1000; ++l)
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