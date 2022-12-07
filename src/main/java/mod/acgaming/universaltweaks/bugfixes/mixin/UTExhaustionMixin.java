package mod.acgaming.universaltweaks.bugfixes.mixin;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import zone.rong.mixinextras.injector.ModifyExpressionValue;

// MC-31819
// https://bugs.mojang.com/browse/MC-31819
// Courtesy of isXander
@Mixin(EntityPlayer.class)
public abstract class UTExhaustionMixin extends EntityLivingBase
{
    public UTExhaustionMixin(World worldIn)
    {
        super(worldIn);
    }

    @ModifyExpressionValue(method = "addExhaustion", at = @At(value = "FIELD", target = "Lnet/minecraft/world/World;isRemote:Z"))
    public boolean utShouldNotAddExhaustion(boolean isRemote)
    {
        if (!UTConfig.bugfixes.utExhaustionToggle) return isRemote;
        if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTExhaustion ::: Exhaustion check");
        return isRemote || this.world.getDifficulty() == EnumDifficulty.PEACEFUL;
    }
}