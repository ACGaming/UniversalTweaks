package mod.acgaming.universaltweaks.mods.divinerpg.waterspawning.mixin;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

import divinerpg.objects.entities.entity.EntityDivineWaterMob;
import divinerpg.objects.entities.entity.EntityPeacefulUntilAttacked;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

// Courtesy of WaitingIdly
@Mixin(value = EntityDivineWaterMob.class, remap = false)
public abstract class UTEntityDivineWaterMobMixin extends EntityPeacefulUntilAttacked
{
    public UTEntityDivineWaterMobMixin(World worldIn)
    {
        super(worldIn);
    }

    /**
     * Changes the effective creature type to water creature because it replaces squid spawns.
     * Without this, if water mob spawning is blocked via {@link LivingSpawnEvent.CheckSpawn}
     * then infinite divinerpg water mobs can spawn, which also takes up the hostile mob cap.
     *
     * @author WaitingIdly
     * @see divinerpg.registry.EntitySpawnRegistry#onLivingSpawn(LivingSpawnEvent.CheckSpawn) EntitySpawnRegistry.onLivingSpawn(LivingSpawnEvent.CheckSpawn)
     */
    @Override
    public boolean isCreatureType(@NotNull EnumCreatureType type, boolean forSpawnCount)
    {
        if (forSpawnCount && this.isNoDespawnRequired()) return false;
        return type == EnumCreatureType.WATER_CREATURE;
    }
}
