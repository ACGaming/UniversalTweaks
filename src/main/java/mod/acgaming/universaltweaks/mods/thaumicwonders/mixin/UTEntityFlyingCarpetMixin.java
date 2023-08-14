package mod.acgaming.universaltweaks.mods.thaumicwonders.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import com.verdantartifice.thaumicwonders.common.entities.EntityFlyingCarpet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of Focamacho
@Mixin(value = EntityFlyingCarpet.class, remap = false)
public abstract class UTEntityFlyingCarpetMixin extends Entity
{
    public UTEntityFlyingCarpetMixin(World worldIn)
    {
        super(worldIn);
    }

    @Inject(method = "func_184230_a", at = @At("HEAD"), cancellable = true)
    private void processInitialInteract(EntityPlayer player, EnumHand hand, CallbackInfoReturnable<Boolean> info)
    {
        if (this.isDead) info.setReturnValue(true);
    }
}