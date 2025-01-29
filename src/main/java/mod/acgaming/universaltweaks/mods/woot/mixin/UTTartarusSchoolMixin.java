package mod.acgaming.universaltweaks.mods.woot.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import ipsis.Woot;
import ipsis.woot.farming.ITickTracker;
import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.loot.schools.TartarusManager;
import ipsis.woot.loot.schools.TartarusSchool;
import mod.acgaming.universaltweaks.mods.woot.ITartarusCleaner;
import mod.acgaming.universaltweaks.mods.woot.UTWootTicketManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of jchung01
@Mixin(value = TartarusSchool.class, remap = false)
public class UTTartarusSchoolMixin
{
    @Shadow
    private int spawnId;

    /**
     * @reason Load Woot dim earlier so {@link UTWootTicketManager}'s callback doesn't fire late and clear the allocated spawn box.
     */
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lipsis/woot/loot/schools/TartarusManager;allocateSpawnBoxId()I"))
    private void ut$loadWorldBeforeAllocate(ITickTracker tickTracker, World world, BlockPos origin, IFarmSetup farmSetup, CallbackInfo ci)
    {
        Woot.wootDimensionManager.getWorldServer(world);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lipsis/woot/loot/schools/TartarusManager;spawnInBox(Lnet/minecraft/world/World;ILipsis/woot/util/WootMobName;Lipsis/woot/util/EnumEnchantKey;)V", shift = At.Shift.AFTER))
    private void ut$cleanupOnDeath(ITickTracker tickTracker, World world, BlockPos origin, IFarmSetup farmSetup, CallbackInfo ci)
    {
        if (spawnId == -1) return;
        ((ITartarusCleaner) Woot.tartarusManager).ut$clean(world, spawnId, false);
        UTWootTicketManager.forceChunk(world, spawnId);
    }

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lipsis/woot/loot/schools/TartarusManager;freeSpawnBoxId(I)I"))
    private int ut$cleanupOnFree(TartarusManager instance, int id, Operation<Integer> original, ITickTracker tickTracker, World world)
    {
        int oldSpawnId = spawnId;
        ((ITartarusCleaner) Woot.tartarusManager).ut$clean(world, oldSpawnId, true);
        spawnId = original.call(instance, oldSpawnId);
        UTWootTicketManager.releaseChunk(oldSpawnId);
        return spawnId;
    }
}
