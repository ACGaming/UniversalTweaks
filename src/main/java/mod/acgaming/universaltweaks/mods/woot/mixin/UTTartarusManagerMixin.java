package mod.acgaming.universaltweaks.mods.woot.mixin;

import java.util.HashMap;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import ipsis.Woot;
import ipsis.woot.loot.schools.SpawnBox;
import ipsis.woot.loot.schools.TartarusManager;
import mod.acgaming.universaltweaks.mods.woot.ITartarusCleaner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// Courtesy of jchung01
@Mixin(value = TartarusManager.class, remap = false)
public class UTTartarusManagerMixin implements ITartarusCleaner
{
    @Shadow
    private HashMap<Integer, SpawnBox> spawnBoxMap;

    @Override
    public void ut$clean(World world, int boxId, boolean removeAll)
    {
        WorldServer worldWoot = Woot.wootDimensionManager.getWorldServer(world);
        if (worldWoot == null) return;
        // Some additionally spawned entities are not EntityLiving (like AoA heartstones), so search by exclusion
        Predicate<Entity> condition = entity ->
        {
            boolean ret = !(entity instanceof EntityPlayer);
            return removeAll ? ret : ret && !(entity instanceof EntityItem);
        };
        for (Entity entity : worldWoot.getEntitiesWithinAABB(Entity.class, this.spawnBoxMap.get(boxId).getAxisAlignedBB(), condition))
        {
            worldWoot.removeEntityDangerously(entity);
        }
    }

    @Override
    public void ut$freeBoxes()
    {
        for (SpawnBox box : spawnBoxMap.values())
        {
            box.clearUsed();
        }
    }

    @Override
    public boolean ut$areBoxesInUse()
    {
        for (SpawnBox box : spawnBoxMap.values())
        {
            if (box.isUsed()) return true;
        }
        return false;
    }
}
