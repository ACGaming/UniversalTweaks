package mod.acgaming.universaltweaks.bugfixes.entities.entityid.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// MC-111480
// https://bugs.mojang.com/browse/MC-111480
// MC-181129
// https://bugs.mojang.com/browse/MC-181129
@Mixin(Entity.class)
public abstract class UTEntityIDMixin
{
    @Shadow
    private int entityId;

    @Shadow
    private static int nextEntityID;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void utEntityID(World worldIn, CallbackInfo ci)
    {
        if (UTConfig.BUGFIXES_ENTITIES.utEntityIDToggle && this.entityId == 0)
        {
            this.entityId = nextEntityID++;
            if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTEntityID ::: Corrected entity ID from 0 to {}", this.entityId);
        }
    }
}