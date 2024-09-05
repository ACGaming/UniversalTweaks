package mod.acgaming.universaltweaks.mods.woot.mixin;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import ipsis.Woot;
import ipsis.woot.farming.ITickTracker;
import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.loot.schools.TartarusSchool;
import mod.acgaming.universaltweaks.mods.woot.ITartarusCleaner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of jchung01
@Mixin(value = TartarusSchool.class, remap = false)
public class UTTartarusSchoolMixin
{
    @Shadow
    private int spawnId;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lipsis/woot/loot/schools/TartarusManager;spawnInBox(Lnet/minecraft/world/World;ILipsis/woot/util/WootMobName;Lipsis/woot/util/EnumEnchantKey;)V", shift = At.Shift.AFTER))
    private void ut$cleanupOnDeath(ITickTracker tickTracker, World world, BlockPos origin, IFarmSetup farmSetup, CallbackInfo ci)
    {
        ((ITartarusCleaner) Woot.tartarusManager).ut$clean(world, spawnId, false);
    }

    // Here for redundancy.
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lipsis/woot/loot/schools/TartarusManager;freeSpawnBoxId(I)I"))
    private void ut$cleanupOnFree(ITickTracker tickTracker, World world, BlockPos origin, IFarmSetup farmSetup, CallbackInfo ci)
    {
        ((ITartarusCleaner) Woot.tartarusManager).ut$clean(world, spawnId, true);
    }

    /**
     * @reason Force remove even if chunks stop ticking.
     */
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/item/EntityItem;setDead()V", remap = true))
    private void ut$cleanupOnLearn(EntityItem instance, ITickTracker tickTracker, World world)
    {
        WorldServer worldWoot = Woot.wootDimensionManager.getWorldServer(world);
        if (worldWoot != null) worldWoot.removeEntityDangerously(instance);
    }

}
