package mod.acgaming.universaltweaks.mods.minejurassic.mixin;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

import java.util.List;
import java.util.Random;
import net.mcreator.minejurassic.ElementsMineJurassic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = ElementsMineJurassic.class, remap = false)
public class UTMineJurassicFossilsMixin
{
    @Shadow
    @Final
    public List<ElementsMineJurassic.ModElement> elements;

    /**
     * @author ACGaming
     * @reason MCreator jank
     */
    @Overwrite
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator cg, IChunkProvider cp)
    {
        this.elements.stream()
            .filter(element -> !element.getClass().getSimpleName().toLowerCase().contains("fossil"))
            .forEach(element -> element.generateWorld(random, chunkX * 16, chunkZ * 16, world, world.provider.getDimension(), cg, cp));
    }
}
