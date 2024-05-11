package mod.acgaming.universaltweaks.bugfixes.entities.entitylists.mixin;

import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// MC-121152
// https://bugs.mojang.com/browse/MC-121152
@Mixin(value = WorldClient.class)
public class UTWorldClientMixin
{
    @Inject(method = "addEntityToWorld",
        slice = @Slice(from = @At(value = "HEAD"),
            to = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;spawnEntity(Lnet/minecraft/entity/Entity;)Z")),
        at = @At(value = "INVOKE", target = "Ljava/util/Set;add(Ljava/lang/Object;)Z", ordinal = 0, remap = false))
    private void utSetIdBeforeAdd(int entityID, Entity entityToSpawn, CallbackInfo ci)
    {
        if (UTConfigBugfixes.ENTITIES.ENTITY_LISTS.utWorldAdditionsToggle)
        {
            entityToSpawn.setEntityId(entityID);
        }
    }
}
