package mod.acgaming.universaltweaks.tweaks.entities.loot.mixin;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.item.Item;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityCreeper.class)
public class UTCreeperMusicDiscsMixin
{
    @WrapWithCondition(method = "onDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/monster/EntityCreeper;dropItem(Lnet/minecraft/item/Item;I)Lnet/minecraft/entity/item/EntityItem;"))
    public boolean utCreeperMusicDiscs(EntityCreeper instance, Item item, int i)
    {
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTCreeperMusicDiscs ::: On creeper death");
        return !UTConfigTweaks.ENTITIES.utCreeperMusicDiscsToggle;
    }
}