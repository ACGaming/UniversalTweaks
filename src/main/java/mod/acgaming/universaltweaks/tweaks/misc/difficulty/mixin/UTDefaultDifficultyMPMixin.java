package mod.acgaming.universaltweaks.tweaks.misc.difficulty.mixin;

import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.EnumDifficulty;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DedicatedServer.class)
public class UTDefaultDifficultyMPMixin
{
    @Redirect(method = "getDifficulty", at = @At(value = "FIELD", target = "Lnet/minecraft/world/EnumDifficulty;NORMAL:Lnet/minecraft/world/EnumDifficulty;"))
    public EnumDifficulty utDefaultDifficultyMP()
    {
        return UTConfig.TWEAKS_MISC.utDefaultDifficulty;
    }
}