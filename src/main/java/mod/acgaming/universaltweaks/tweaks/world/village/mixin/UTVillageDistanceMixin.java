package mod.acgaming.universaltweaks.tweaks.world.village.mixin;

import net.minecraft.world.gen.structure.MapGenVillage;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MapGenVillage.class)
public class UTVillageDistanceMixin
{
    @Shadow
    private int distance;

    @Inject(method = "<init>()V", at = @At("TAIL"))
    public void utVillageDistance(CallbackInfo ci)
    {
        this.distance = UTConfigTweaks.WORLD.utVillageDistance;
    }
}