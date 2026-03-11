package mod.acgaming.universaltweaks.bugfixes.world.difficulty.mixin;

import java.io.File;
import java.net.Proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.EnumDifficulty;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of Blue-Beaker
@Mixin(IntegratedServer.class)
public abstract class UTDifficultyConsistencyMixin extends MinecraftServer
{
    @Final
    @Shadow
    private Minecraft mc;

    public UTDifficultyConsistencyMixin(File anvilFile, Proxy proxy, DataFixer dataFixer, YggdrasilAuthenticationService authService, MinecraftSessionService sessionService, GameProfileRepository profileRepo, PlayerProfileCache profileCache)
    {
        super(anvilFile, proxy, dataFixer, authService, sessionService, profileRepo, profileCache);
    }

    @Inject(method = "getDifficulty", at = @At(value = "HEAD"), cancellable = true)
    public void utGetDifficulty(CallbackInfoReturnable<EnumDifficulty> cir)
    {
        if (this.mc.world == null && this.worlds.length > 0)
        {
            EnumDifficulty difficulty = this.worlds[0].getDifficulty();
            cir.setReturnValue(difficulty);
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTDifficultyConsistency ::: Got difficulty {} from world, client settings has {}", difficulty, this.mc.gameSettings.difficulty);
        }
    }
}
