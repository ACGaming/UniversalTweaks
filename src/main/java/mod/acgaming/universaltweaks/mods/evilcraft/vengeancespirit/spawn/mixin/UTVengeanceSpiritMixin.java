package mod.acgaming.universaltweaks.mods.evilcraft.vengeancespirit.spawn.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.cyclops.evilcraft.entity.monster.VengeanceSpirit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of WaitingIdly
@Mixin(value = VengeanceSpirit.class, remap = false)
public abstract class UTVengeanceSpiritMixin
{
    @Shadow
    public static boolean canSpawnNew(World world, BlockPos blockPos)
    {
        return false;
    }

    /**
     * @author WaitingIdly
     * @reason check {@link VengeanceSpirit#spawnRandom} at the start of the method,
     * as all calls to it use the same parameters. The only way the return value of it could change
     * is if a {@link VengeanceSpirit} was spawned - but if that happens, the loop returns that entity,
     * meaning this method is called up to 50 times every time this check is requested.
     * <p>
     * This can be significantly intensive, as by default every call checks {@code (15*2)^3} block positions
     * against {@link org.cyclops.evilcraft.block.GemStoneTorchConfig#getBlockInstance() GemStoneTorchConfig#getBlockInstance()}.
     * This effect can be triggered every time a block is broken or entity attacked via
     * {@link org.cyclops.evilcraft.enchantment.EnchantmentVengeance EnchantmentVengeance}.
     */
    @Inject(method = "spawnRandom", at = @At("HEAD"), cancellable = true)
    private static void failFastIfSpawnBlocked(World world, BlockPos blockPos, int area, CallbackInfoReturnable<VengeanceSpirit> cir)
    {
        if (!UTConfigMods.EVIL_CRAFT.utVengeanceSpiritRandom) return;
        if (canSpawnNew(world, blockPos)) return;
        cir.setReturnValue(null);
    }

    /**
     * @author WaitingIdly
     * @reason skip further {@link VengeanceSpirit#spawnRandom} checks, as they will always be true due to {@link #failFastIfSpawnBlocked}.
     */
    @WrapOperation(method = "spawnRandom", at = @At(value = "INVOKE", target = "Lorg/cyclops/evilcraft/entity/monster/VengeanceSpirit;canSpawnNew(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Z"))
    private static boolean skipCanSpawnNewCheck(World world, BlockPos blockPos, Operation<Boolean> original)
    {
        if (!UTConfigMods.EVIL_CRAFT.utVengeanceSpiritRandom) return original.call(world, blockPos);
        return true;
    }
}
