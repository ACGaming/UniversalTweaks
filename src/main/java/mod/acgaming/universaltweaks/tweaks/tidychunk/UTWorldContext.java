package mod.acgaming.universaltweaks.tweaks.tidychunk;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import mod.acgaming.universaltweaks.UniversalTweaks;

// Courtesy of OreCruncher
public class UTWorldContext
{
    public static boolean isTargetEntity(Entity entity)
    {
        return entity instanceof EntityItem;
    }

    public Object2LongOpenHashMap<ChunkPos> chunks = new Object2LongOpenHashMap<>();
    public int removeCount = 0;

    public void add(ChunkPos pos, World world)
    {
        this.chunks.put(pos, world.getTotalWorldTime());
    }

    public void searchAndDestroy(World world)
    {
        if (this.chunks.size() > 0)
        {
            world.getEntities(EntityItem.class, t -> isTargetEntity(t) && isContained(t)).forEach(this::removeEntity);
            if (this.removeCount > 0)
            {
                UniversalTweaks.LOGGER.debug("Entities wiped: " + this.removeCount);
                this.removeCount = 0;
            }
        }
    }

    public void removeOldContext(World world)
    {
        this.chunks.entrySet().removeIf(ctx -> (world.getTotalWorldTime() - ctx.getValue()) > 15);
    }

    public void removeEntity(Entity entity)
    {
        entity.setDead();
        this.removeCount++;
    }

    public boolean isContained(Entity entity)
    {
        return entity.isEntityAlive() && this.chunks.containsKey(new ChunkPos(entity.getPosition()));
    }
}