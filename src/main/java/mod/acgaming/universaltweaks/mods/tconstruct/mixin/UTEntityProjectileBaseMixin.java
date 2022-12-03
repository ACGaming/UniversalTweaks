package mod.acgaming.universaltweaks.mods.tconstruct.mixin;

import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slimeknights.tconstruct.library.capability.projectile.TinkerProjectileHandler;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.tools.modifiers.ModReinforced;

@Mixin(EntityProjectileBase.class)
public abstract class UTEntityProjectileBaseMixin extends EntityArrow
{
    @Shadow(remap = false)
    public TinkerProjectileHandler tinkerProjectile;

    public UTEntityProjectileBaseMixin(World worldIn)
    {
        super(worldIn);
    }

    @Inject(method = "onUpdate", at = @At(value = "HEAD"))
    public void utTConProjectileLifetime(CallbackInfo ci)
    {
        if (!UTConfig.mods.utTConProjectileToggle) return;
        if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTEntityProjectileBase ::: On update");
        if (!this.getEntityWorld().isRemote
            && this.inGround
            && this.arrowShake <= 0
            && TagUtil.getTagSafe(this.tinkerProjectile.getItemStack()).getBoolean(ModReinforced.TAG_UNBREAKABLE)
            && this.ticksExisted >= 100)
        {
            this.setDead();
        }
    }
}