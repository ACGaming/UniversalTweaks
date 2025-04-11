package mod.acgaming.universaltweaks.tweaks.misc.gui.lanserverproperties.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.datafix.DataFixer;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.io.File;
import java.net.Proxy;
import java.util.concurrent.*;
import mod.acgaming.universaltweaks.UniversalTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// Courtesy of embeddedt
@Mixin(IntegratedServer.class)
public abstract class UTIntegratedServerMixin extends MinecraftServer
{
    protected UTIntegratedServerMixin(File anvilFile, Proxy proxy, DataFixer dataFixer, YggdrasilAuthenticationService authService, MinecraftSessionService sessionService, GameProfileRepository profileRepo, PlayerProfileCache profileCache)
    {
        super(anvilFile, proxy, dataFixer, authService, sessionService, profileRepo, profileCache);
    }

    /**
     * Sometimes the server thread is already gone at this point, try to prevent a freeze
     */
    @Redirect(method = "initiateShutdown", at = @At(value = "INVOKE", target = "Lcom/google/common/util/concurrent/Futures;getUnchecked(Ljava/util/concurrent/Future;)Ljava/lang/Object;", remap = false), require = 0)
    private <V> V utCheckThreadStatus(Future<V> future)
    {
        while (this.isServerRunning() && ((Thread) ObfuscationReflectionHelper.getPrivateValue(MinecraftServer.class, this, "field_175590_aa")).isAlive())
        {
            try
            {
                return future.get(500, TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException e)
            {
                break;
            }
            catch (ExecutionException | CancellationException e)
            {
                throw new RuntimeException(e);
            }
            catch (TimeoutException ignored) {}
        }
        UniversalTweaks.LOGGER.info("UTIntegratedServer ::: Server thread has already exited, skipping logout process");
        return null;
    }
}