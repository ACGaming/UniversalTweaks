package mod.acgaming.universaltweaks.mods.bewitchment.mixin;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

import com.bewitchment.common.block.tile.entity.TileEntityWitchesAltar;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = TileEntityWitchesAltar.class, remap = false)
public class UTTileEntityWitchesAltarMixin
{
    /**
     * @author WaitingIdly
     * @reason Ensure the property exists before attempting to set the blockstate with it,
     * as not all classes that extend the class.
     * Primarily for TerraFirmaCraft leaves, as they do not have the {@link net.minecraft.block.BlockLeaves#CHECK_DECAY BlockLeaves.CHECK_DECAY}
     * property, which causes the altar to crash when checking them.
     * See <a href="https://github.com/TerraFirmaCraft/TerraFirmaCraft/blob/1.12.x/src/main/java/net/dries007/tfc/objects/blocks/wood/BlockLeavesTFC.java#L59">BlockLeavesTFC.java</a>
     */
    @SuppressWarnings("MixinExtrasOperationParameters")
    @WrapOperation(method = "convert", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;withProperty(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;"))
    private <T extends Comparable<T>, V extends T> IBlockState utCheckPropertyExists(IBlockState instance, IProperty<T> property, V value, Operation<IBlockState> original)
    {
        if (instance.getPropertyKeys().contains(property)) return original.call(instance, property, value);
        return instance;
    }
}
