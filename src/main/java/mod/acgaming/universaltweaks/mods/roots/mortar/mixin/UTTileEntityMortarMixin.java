package mod.acgaming.universaltweaks.mods.roots.mortar.mixin;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import epicsquid.roots.tileentity.TileEntityMortar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(value = TileEntityMortar.class, remap = false)
public abstract class UTTileEntityMortarMixin
{
    /**
     * @author WaitingIdly
     * @reason The spell dust itemstack isn't copied before being spawned in-world,
     * which means that some item collectors can break the recipe.
     * Copy it to ensure that cannot happen.
     */
    @WrapOperation(method = "handleCraft", at = @At(value = "INVOKE", target = "Lepicsquid/mysticallib/util/ItemUtil;spawnItem(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/entity/item/EntityItem;", ordinal = 0))
    private EntityItem utFixDustSpawn(World world, BlockPos pos, ItemStack stack, Operation<EntityItem> original)
    {
        return original.call(world, pos, stack.copy());
    }
}
