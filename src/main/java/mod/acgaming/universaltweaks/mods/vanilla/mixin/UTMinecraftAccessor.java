package mod.acgaming.universaltweaks.mods.vanilla.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface UTMinecraftAccessor
{
    @Accessor("modelManager")
    ModelManager getModelManager();

    @Accessor("rightClickDelayTimer")
    int getRightClickDelayTimer();

    @Accessor("rightClickDelayTimer")
    void setRightClickDelayTimer(int value);
}
