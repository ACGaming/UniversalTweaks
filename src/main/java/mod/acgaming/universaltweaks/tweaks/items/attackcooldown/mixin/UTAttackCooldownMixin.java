package mod.acgaming.universaltweaks.tweaks.items.attackcooldown.mixin;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of WildBamaBoy
@Mixin(EntityPlayer.class)
public abstract class UTAttackCooldownMixin extends EntityLivingBase
{
    public UTAttackCooldownMixin(World worldIn)
    {
        super(worldIn);
    }

    @Inject(method = "resetCooldown", at = @At("TAIL"))
    public void utResetCooldown(CallbackInfo ci)
    {
        if (!UTConfigTweaks.ITEMS.ATTACK_COOLDOWN.utAttackCooldownToggle
            || (UTConfigTweaks.ITEMS.ATTACK_COOLDOWN.utAttackCooldownSwords && !(this.getHeldItemMainhand().getItem() instanceof ItemSword))
            || this.ticksSinceLastSwing >= 20) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTAttackCooldown ::: Reset cooldown");
        this.ticksSinceLastSwing = 20;
    }
}