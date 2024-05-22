package mod.acgaming.universaltweaks.tweaks.blocks.betterplacement.mixin;

import mod.acgaming.universaltweaks.tweaks.blocks.betterplacement.IInteractable;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Block.class)
public class UTInteractableBlockMixin implements IInteractable
{
    @Unique
    private boolean ut$interactable = true;

    @Override
    public boolean isInteractable()
    {
        return ut$interactable;
    }

    /**
     * @reason Block is assumed NOT interactable if the default/super implementation is called.
     */
    @Inject(method = "onBlockActivated", at = @At(value = "HEAD"))
    private void utSetInteractable(CallbackInfoReturnable<Boolean> cir)
    {
        ut$interactable = false;
    }
}
