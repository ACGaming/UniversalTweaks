package mod.acgaming.universaltweaks.mods.avaritiaddons.mixin;

import net.minecraft.entity.player.EntityPlayer;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wanion.avaritiaddons.block.chest.ContainerAvaritiaddonsChest;
import wanion.lib.common.WContainer;
import wanion.lib.common.WTileEntity;

// Courtesy of WaitingIdly
@Mixin(value = ContainerAvaritiaddonsChest.class, remap = false)
public abstract class UTContainerAvaritiaddonsChestMixin extends WContainer<WTileEntity>
{
    private UTContainerAvaritiaddonsChestMixin(@NotNull WTileEntity wTileEntity)
    {
        super(wTileEntity);
    }

    /**
     * @author WaitingIdly
     * @reason Ensure the held itemstack is properly handled when the inventory is closed.
     */
    @Inject(method = "onContainerClosed", at = @At(value = "HEAD"), remap = true)
    private void utCallSuper(EntityPlayer playerIn, CallbackInfo ci)
    {
        super.onContainerClosed(playerIn);
    }
}