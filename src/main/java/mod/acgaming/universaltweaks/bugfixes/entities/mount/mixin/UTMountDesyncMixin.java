package mod.acgaming.universaltweaks.bugfixes.entities.mount.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.SPacketEntityTeleport;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// MC-101247
// https://bugs.mojang.com/browse/MC-101247
@Mixin(EntityPlayerMP.class)
public abstract class UTMountDesyncMixin extends Entity
{
    @Shadow
    public NetHandlerPlayServer connection;

    public UTMountDesyncMixin(World worldIn)
    {
        super(worldIn);
    }

    @Inject(method = "dismountRidingEntity", at = @At(value = "HEAD"))
    public void utMountDesync(CallbackInfo ci)
    {
        if (UTConfigBugfixes.ENTITIES.utMountDesyncToggle && this.connection != null && this.getRidingEntity() != null)
        {
            this.connection.sendPacket(new SPacketEntityTeleport(this.getRidingEntity()));
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTMountDesync ::: Sent packet for {} at {}", this.getRidingEntity().getName(), this.getRidingEntity().getPosition());
        }
    }
}