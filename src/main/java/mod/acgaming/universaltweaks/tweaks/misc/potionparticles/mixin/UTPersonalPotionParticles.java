package mod.acgaming.universaltweaks.tweaks.misc.potionparticles.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("ConstantValue")
@Mixin(value = EntityLivingBase.class)
public abstract class UTPersonalPotionParticles
{
    @ModifyExpressionValue(method = "updatePotionEffects", at = @At(value = "INVOKE", target = "Ljava/lang/Integer;intValue()I"))
    public int utDisablePersonalEffectParticles(int integer)
    {
        if (!UTConfigTweaks.MISC.utPoVEffectParticles) return integer;
        if (Minecraft.getMinecraft().player != null && (Object) this instanceof EntityPlayer && Minecraft.getMinecraft().player.getUniqueID().equals(((EntityPlayer) (Object) this).getUniqueID()))
        {
            return 0;
        }
        return integer;
    }

    @ModifyExpressionValue(method = "updatePotionEffects", at = @At(value = "INVOKE", target = "Ljava/lang/Boolean;booleanValue()Z"))
    public boolean utDisablePersonalEffectParticles(boolean bool)
    {
        if (!UTConfigTweaks.MISC.utPoVEffectParticles) return bool;
        if (Minecraft.getMinecraft().player != null && (Object) this instanceof EntityPlayer && Minecraft.getMinecraft().player.getUniqueID().equals(((EntityPlayer) (Object) this).getUniqueID()))
        {
            return true;
        }
        return bool;
    }
}