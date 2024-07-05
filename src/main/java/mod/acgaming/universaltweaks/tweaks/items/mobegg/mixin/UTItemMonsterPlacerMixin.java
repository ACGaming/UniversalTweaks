package mod.acgaming.universaltweaks.tweaks.items.mobegg.mixin;

import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.tileentity.TileEntityMobSpawner;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of WaitingIdly
@Mixin(ItemMonsterPlacer.class)
public abstract class UTItemMonsterPlacerMixin
{
//    @Inject(method = "onPlaceItemIntoWorld", target = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;onItemUse(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumHand;Lnet/minecraft/util/EnumFacing;FFF)Lnet/minecraft/util/EnumActionResult;"))
//    private boolean utPreventReplacingMobSpawnerEntity(Object instance, Operation<Boolean> original)
//    {
//        if (!UTConfigTweaks.ITEMS.utPreventMobEggsFromChangingSpawner) return original.call(instance);
//        return false;
//    }
    @WrapOperation(method = "onItemUse", constant = @Constant(classValue = TileEntityMobSpawner.class, ordinal = 0))
    private boolean utPreventReplacingMobSpawnerEntity(Object instance, Operation<Boolean> original)
    {
        if (!UTConfigTweaks.ITEMS.utPreventMobEggsFromChangingSpawner) return original.call(instance);
        return false;
    }
}
