package mod.acgaming.universaltweaks.bugfixes.entities.destroypacket.mixin;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// MC-29375
// https://bugs.mojang.com/browse/MC-29375
@Mixin(EntityPlayerMP.class)
public abstract class UTDestroyPacketMixin extends EntityPlayer
{
    @Shadow
    public NetHandlerPlayServer connection;

    @Shadow
    @Final
    private List<Integer> entityRemoveQueue;

    protected UTDestroyPacketMixin(World worldIn, GameProfile gameProfileIn)
    {
        super(worldIn, gameProfileIn);
    }

    @Inject(method = "onUpdateEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;onUpdate()V", shift = At.Shift.AFTER))
    public void utSendDestroyPacket(CallbackInfo ci)
    {
        if (!UTConfigBugfixes.ENTITIES.utDestroyPacketToggle) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTDestroyPacket ::: Send SPacketDestroyEntities");
        if (!this.isEntityAlive())
        {
            while (!this.entityRemoveQueue.isEmpty())
            {
                int i = Math.min(this.entityRemoveQueue.size(), Integer.MAX_VALUE);
                int[] aint = new int[i];
                Iterator<Integer> iterator = this.entityRemoveQueue.iterator();
                int j = 0;
                while (iterator.hasNext() && j < i)
                {
                    aint[j++] = iterator.next();
                    iterator.remove();
                }
                this.connection.sendPacket(new SPacketDestroyEntities(aint));
            }
        }
    }
}