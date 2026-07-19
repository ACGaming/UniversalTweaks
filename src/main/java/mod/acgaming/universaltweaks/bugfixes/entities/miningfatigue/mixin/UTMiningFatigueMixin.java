package mod.acgaming.universaltweaks.bugfixes.entities.miningfatigue.mixin;

import net.minecraft.entity.player.EntityPlayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

// MC-279819
// https://bugs.mojang.com/browse/MC/issues/MC-279819
// Fixed in Minecraft 26.3
@Mixin(EntityPlayer.class)
public abstract class UTMiningFatigueMixin
{
    @ModifyConstant(method = "getDigSpeed", constant = @Constant(floatValue = 0.0027F))
    private float utMiningFatigueIII(float original)
    {
        return 0.027F;
    }
}