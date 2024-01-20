package mod.acgaming.universaltweaks.bugfixes.entities.minecart.mixin;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

// MC-64836
// https://bugs.mojang.com/browse/MC-64836
// Courtesy of mrgrim
@Mixin(EntityMinecart.class)
public abstract class UTMinecartAIMixin
{
    // TODO: Investigate AI code (?)
    @Redirect(method = "moveAlongTrack", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityLivingBase;moveForward:F", opcode = Opcodes.GETFIELD, ordinal = 0),
            slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/entity/item/EntityMinecart;getPassengers()Ljava/util/List;", ordinal = 1),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/entity/item/EntityMinecart;shouldDoRailFunctions()Z", remap = false)))
    private float disableMobControl(EntityLivingBase entityIn)
    {
        if (UTConfigBugfixes.ENTITIES.utMinecartAIToggle && !(entityIn instanceof EntityPlayer))
        {
            return 0.0F;
        }

        return entityIn.moveForward;
    }
}