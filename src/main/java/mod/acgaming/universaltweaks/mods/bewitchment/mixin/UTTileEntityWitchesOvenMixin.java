package mod.acgaming.universaltweaks.mods.bewitchment.mixin;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import com.bewitchment.common.block.tile.entity.TileEntityWitchesOven;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TileEntityWitchesOven.class, remap = false)
public class UTTileEntityWitchesOvenMixin
{
    @Shadow
    @Final
    private ItemStackHandler inventory_up;

    /**
     * @author Invadermonky
     * @reason Fixes Bewitchment Witches' Oven consuming fuel container items.
     *
     * <p>
     * The {@link TileEntityWitchesOven#burnFuel(int, boolean)} method extracts 1 item from the inventory and
     * checks if it is a Lava Bucket. Any non-lava bucket items are destroyed.
     * </p>
     *
     * <p>
     * This mixin performs a check on the removed stack and re-inserts it into the fuel inventory if it is
     * a container item and is not a Lava Bucket.
     * </p>
     */
    @Inject(
        method = "burnFuel",
        at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraftforge/items/ItemStackHandler;extractItem(IIZ)Lnet/minecraft/item/ItemStack;")
    )
    private void burnFuelMixin(int time, boolean consume, CallbackInfo ci, @Local(name = "stack") ItemStack stack)
    {
        if (stack.getItem() != Items.LAVA_BUCKET && stack.getItem().hasContainerItem(stack))
        {
            this.inventory_up.insertItem(0, stack.getItem().getContainerItem(stack), false);
        }
    }
}
