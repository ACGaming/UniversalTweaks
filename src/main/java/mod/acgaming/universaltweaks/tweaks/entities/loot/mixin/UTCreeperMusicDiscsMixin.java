package mod.acgaming.universaltweaks.tweaks.entities.loot.mixin;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.item.Item;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import zone.rong.mixinextras.injector.WrapWithCondition;

@Mixin(EntityCreeper.class)
public class UTCreeperMusicDiscsMixin
{
    @WrapWithCondition(method = "onDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/monster/EntityCreeper;dropItem(Lnet/minecraft/item/Item;I)Lnet/minecraft/entity/item/EntityItem;"))
    public boolean utCreeperMusicDiscs(EntityCreeper instance, Item item, int i)
    {
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTCreeperMusicDiscs ::: On creeper death");
        return !UTConfig.TWEAKS_ENTITIES.utCreeperMusicDiscsToggle;
    }
}