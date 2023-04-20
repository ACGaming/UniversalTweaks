package mod.acgaming.universaltweaks.bugfixes.entities.ai.mixin;

import java.util.Set;

import com.google.common.collect.Sets;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.profiler.Profiler;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityAITasks.class)
public class UTEntityAITasksMixin
{
    @Mutable
    @Shadow
    @Final
    public Set<EntityAITasks.EntityAITaskEntry> taskEntries;

    @Mutable
    @Shadow
    @Final
    private Set<EntityAITasks.EntityAITaskEntry> executingTaskEntries;

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void utEntityAITasks(Profiler profilerIn, CallbackInfo ci)
    {
        if (!UTConfig.BUGFIXES_ENTITIES.utEntityAITasksToggle) return;
        this.taskEntries = Sets.newConcurrentHashSet();
        this.executingTaskEntries = Sets.newConcurrentHashSet();
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTEntityAITasks ::: Created concurrent hash sets");
    }
}