package mod.acgaming.universaltweaks.tweaks.misc.gamewindow.mixin;

import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.Display;
import net.minecraft.client.Minecraft;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.Util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Minecraft.class)
public abstract class UTWindowIconMixin
{
    @Shadow
    protected abstract ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException;

    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;setWindowIcon()V"))
    private void utWindowIcon(Minecraft mc)
    {
        ut$setWindowIcon();
    }

    @Unique
    private void ut$setWindowIcon()
    {
        boolean isMac = Util.getOSType() == Util.EnumOS.OSX;
        InputStream icon16 = null;
        InputStream icon32 = null;
        InputStream icon256 = null;
        try
        {
            icon16 = Files.newInputStream(Paths.get(Launch.minecraftHome.getPath(), UTConfigTweaks.MISC.GAME_WINDOW.utGameWindowIcon16));
            icon32 = Files.newInputStream(Paths.get(Launch.minecraftHome.getPath(), UTConfigTweaks.MISC.GAME_WINDOW.utGameWindowIcon32));
            if (isMac)
            {
                icon256 = Files.newInputStream(Paths.get(Launch.minecraftHome.getPath(), UTConfigTweaks.MISC.GAME_WINDOW.utGameWindowIcon256));
                Display.setIcon(new ByteBuffer[] {this.readImageToBuffer(icon16), this.readImageToBuffer(icon32), this.readImageToBuffer(icon256)});
            }
            else
            {
                Display.setIcon(new ByteBuffer[] {this.readImageToBuffer(icon16), this.readImageToBuffer(icon32)});
            }
        }
        catch (IOException e)
        {
            UniversalTweaks.LOGGER.error("UTWindowIcon ::: Couldn't set icon", e);
        }
        finally
        {
            IOUtils.closeQuietly(icon16);
            IOUtils.closeQuietly(icon32);
            if (isMac)
            {
                IOUtils.closeQuietly(icon256);
            }
        }
    }
}
