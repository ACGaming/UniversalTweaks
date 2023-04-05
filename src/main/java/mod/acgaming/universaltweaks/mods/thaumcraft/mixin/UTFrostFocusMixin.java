package mod.acgaming.universaltweaks.mods.thaumcraft.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import thaumcraft.api.casters.FocusEffect;
import thaumcraft.common.items.casters.foci.FocusEffectFrost;
import thaumcraft.common.lib.SoundsTC;

// Courtesy of Turkey9002
@Mixin(FocusEffectFrost.class)
public abstract class UTFrostFocusMixin extends FocusEffect
{
	@Override
	public void onCast(final Entity caster)
	{
        if (UTConfig.MOD_INTEGRATION.THAUMCRAFT.utTCFrostFocusSoundRevampToggle) caster.world.playSound((EntityPlayer)null, caster.getPosition().up(), SoundsTC.ice, SoundCategory.PLAYERS, 0.6F, 1.0F + 
		(float)(caster.world.rand.nextGaussian() * 0.05F));
        else caster.world.playSound((EntityPlayer)null, caster.getPosition().up(), SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, SoundCategory.PLAYERS, 0.2F, 1.0F + 
		(float)(caster.world.rand.nextGaussian() * 0.05F));
    }
}
