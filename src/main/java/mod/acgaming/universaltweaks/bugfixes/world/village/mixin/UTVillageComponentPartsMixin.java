package mod.acgaming.universaltweaks.bugfixes.world.village.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.gen.structure.StructureVillagePieces;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin({StructureVillagePieces.Church.class, StructureVillagePieces.House2.class})
public abstract class UTVillageComponentPartsMixin extends StructureVillagePieces.Village
{
    @WrapOperation(method = "addComponentParts", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getDefaultState()Lnet/minecraft/block/state/IBlockState;", ordinal = 0))
    private IBlockState utVillageComponentParts(Block block, Operation<IBlockState> original)
    {
        return getBiomeSpecificBlockState(original.call(block));
    }
}