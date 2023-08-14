package mod.acgaming.universaltweaks.mods.thaumcraft.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import thaumcraft.common.entities.construct.EntityOwnedConstruct;
import thaumcraft.common.golems.EntityThaumcraftGolem;

// Courtesy of Focamacho
@Mixin(EntityThaumcraftGolem.class)
public class UTEntityThaumcraftGolemMixin extends EntityOwnedConstruct
{
    public UTEntityThaumcraftGolemMixin(World worldIn)
    {
        super(worldIn);
    }

    // Courtesy of LuckyValenok
    @Override
    protected void dropEquipment(boolean wasRecentlyHit, int lootingModifier) {}

    @Inject(method = "processInteract", at = @At("HEAD"), cancellable = true)
    private void processInteract(EntityPlayer player, EnumHand hand, CallbackInfoReturnable<Boolean> cir)
    {
        if (getHealth() <= 0.0F) cir.setReturnValue(false);
    }
}