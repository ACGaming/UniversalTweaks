package mod.acgaming.universaltweaks.tweaks.entities.minecart.mixin;

import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityMinecartFurnace;
import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.entity.item.EntityMinecartTNT;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of WaitingIdly
@Mixin(value = {EntityMinecartFurnace.class, EntityMinecartChest.class, EntityMinecartTNT.class, EntityMinecartHopper.class})
public abstract class UTEntityMinecartTypeMixin
{
    @ModifyExpressionValue(method = "killMinecart", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Ljava/lang/String;)Z"))
    private boolean utPreventDropMinecartType(boolean original)
    {
        return !UTConfigTweaks.ENTITIES.utMinecartDropsType && original;
    }
}