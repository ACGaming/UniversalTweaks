package mod.acgaming.universaltweaks.mods.infernalmobs.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

import atomicstryker.infernalmobs.common.MobModifier;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MobModifier.class)
public class UTInfernalMobsMobModifierMixin
{
    @Redirect(method = "getEntityDisplayName", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityList;getEntityString(Lnet/minecraft/entity/Entity;)Ljava/lang/String;"))
    public String utInfernalMobsDisplayName(Entity target)
    {
        return UTConfigMods.INFERNAL_MOBS.utIMBetterEntityNamesToggle ? target.getDisplayName().getUnformattedText() : EntityList.getEntityString(target);
    }
}
