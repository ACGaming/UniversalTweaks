package mod.acgaming.universaltweaks.tweaks.entities.villagerharvest.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;

import mod.acgaming.universaltweaks.tweaks.entities.villagerharvest.UTVillagerHarvestUtils;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIHarvestFarmland;
import net.minecraftforge.common.EnumPlantType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityAIHarvestFarmland.class)
public class UTEntityAIVillagerHarvestMixin
{
    @ModifyExpressionValue(
        method = "updateTask",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraftforge/common/IPlantable;getPlantType(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraftforge/common/EnumPlantType;",
            remap = false
        )
    )
    private EnumPlantType utBlacklistPlantHarvest(EnumPlantType original, @Local Block block) {
        if(original == EnumPlantType.Crop && UTVillagerHarvestUtils.isBlacklistedCrop(block)) {
            return null;
        }
        return original;
    }
}
