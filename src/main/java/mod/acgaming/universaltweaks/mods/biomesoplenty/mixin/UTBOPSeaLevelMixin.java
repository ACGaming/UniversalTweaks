package mod.acgaming.universaltweaks.mods.biomesoplenty.mixin;

import biomesoplenty.api.config.IConfigObj;
import biomesoplenty.common.world.BOPWorldSettings;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BOPWorldSettings.class, remap = false)
public class UTBOPSeaLevelMixin
{
    @Shadow
    public int seaLevel;

    @Inject(method = "setDefault", at = @At(value = "TAIL"))
    public void utBOPSeaLevel(CallbackInfo ci)
    {
        seaLevel = UTConfigTweaks.WORLD.utSeaLevel;
    }

    @Inject(method = "fromConfigObj", at = @At(value = "TAIL"))
    public void utBOPSeaLevelWorldConfig(IConfigObj worldConfig, CallbackInfo ci)
    {
        this.seaLevel = worldConfig.getInt("seaLevel", this.seaLevel);
    }
}