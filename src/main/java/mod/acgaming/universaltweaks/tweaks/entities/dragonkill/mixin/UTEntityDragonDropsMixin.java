package mod.acgaming.universaltweaks.tweaks.entities.dragonkill.mixin;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import net.minecraft.entity.boss.EntityDragon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EntityDragon.class)
public class UTEntityDragonDropsMixin
{
    /**
     * @author Invadermonky
     * @reason Modifying the hardcoded experience drop when the Ender Dragon is killed
     *          for the second time.
     */
    @ModifyConstant(method = "onDeathUpdate", constant = @Constant(intValue = 500))
    private int utModifySecondKillXp(int original)
    {
        return UTConfigTweaks.ENTITIES.DRAGON_KILL.utSecondKillXp;
    }

    /**
     * @author Invadermonky
     * @reason Modifying the hardcoded experience drop when the Ender Dragon is killed
     *          for the first time.
     */
    @ModifyConstant(method = "onDeathUpdate", constant = @Constant(intValue = 12000))
    private int utModifyFirstKillXp(int original)
    {
        return UTConfigTweaks.ENTITIES.DRAGON_KILL.utFirstKillXp;
    }
}
