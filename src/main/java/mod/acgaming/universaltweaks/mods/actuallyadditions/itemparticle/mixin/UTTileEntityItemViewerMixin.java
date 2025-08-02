package mod.acgaming.universaltweaks.mods.actuallyadditions.itemparticle.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of WaitingIdly
@Mixin(targets = "de.ellpeck.actuallyadditions.mod.network.PacketHandler$3", remap = false)
public abstract class UTTileEntityItemViewerMixin
{
    @Inject(method = "handleData", at = @At("HEAD"), cancellable = true)
    private void utDoItemParticle(NBTTagCompound compound, MessageContext context, CallbackInfo ci)
    {
        int value = UTConfigMods.ACTUALLY_ADDITIONS.utItemLaserParticlesGraphics;
        if (value > 3 || Minecraft.getMinecraft().gameSettings.particleSetting >= value)
        {
            ci.cancel();
        }
    }
}