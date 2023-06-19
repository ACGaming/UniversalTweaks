package mod.acgaming.universaltweaks.tweaks.misc.difficulty.mixin;

import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.storage.WorldInfo;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldInfo.class)
public class UTDefaultDifficultySPMixin
{
    @Redirect(method = "<init>(Lnet/minecraft/world/WorldSettings;Ljava/lang/String;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/world/storage/WorldInfo;DEFAULT_DIFFICULTY:Lnet/minecraft/world/EnumDifficulty;"))
    public EnumDifficulty utDefaultDifficultySP()
    {
        return EnumDifficulty.byId(UTConfig.TWEAKS_MISC.utDefaultDifficulty.ordinal());
    }
}