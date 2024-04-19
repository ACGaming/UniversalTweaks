package mod.acgaming.universaltweaks.tweaks.performance.entityradiuscheck;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.registries.RegistryManager;

import it.unimi.dsi.fastutil.objects.Reference2DoubleMap;
import it.unimi.dsi.fastutil.objects.Reference2DoubleMaps;
import it.unimi.dsi.fastutil.objects.Reference2DoubleOpenHashMap;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

public class UTEntityRadiusCheck
{
    public static Set<Class<? extends Entity>> searchTargets = Collections.emptySet();
    public static Reference2DoubleMap<Class<? extends Entity>> collisionTargets = Reference2DoubleMaps.emptyMap();

    public static void onLoadComplete()
    {
        if (UTConfigTweaks.PERFORMANCE.ENTITY_RADIUS_CHECK.utReduceSearchSizeToggle) initSearchTargets();
        if (UTConfigTweaks.PERFORMANCE.ENTITY_RADIUS_CHECK.utLessCollisionsToggle) initCollisionTargets();
    }

    public static void initSearchTargets()
    {
        final ResourceLocation playerId = new ResourceLocation("player");
        Set<Class<? extends Entity>> out = new ReferenceOpenHashSet<>();
        // Read config for classes
        for (String entityId : UTConfigTweaks.PERFORMANCE.ENTITY_RADIUS_CHECK.utReduceSearchSizeTargets)
        {
            Class<? extends Entity> entityClazz = getEntityType(entityId, playerId, false);
            if (entityClazz == null)
            {
                UniversalTweaks.LOGGER.warn("UTEntityRadiusCheck ::: Invalid entity id " + entityId + "in \"[3] Reduce Search Size Targets\"! Skipping this entry.");
                continue;
            }
            out.add(entityClazz);
        }
        if (!out.isEmpty()) searchTargets = out;
    }

    public static void initCollisionTargets()
    {
        final ResourceLocation ignored = new ResourceLocation("");
        // To avoid auto-unboxing later
        collisionTargets = new Reference2DoubleOpenHashMap<>();
        Set<?> vanillaEntities = (Set<?>) RegistryManager.VANILLA.getRegistry(new ResourceLocation("entities")).getValuesCollection();
        Predicate<Class<? extends Entity>> vanillaPredicate = buildVanillaPredicate();
        // Add vanilla entity types
        for (Object entry : vanillaEntities)
        {
            Class<? extends Entity> clazz = ((EntityEntry) entry).getEntityClass();
            if (vanillaPredicate.test(clazz))
            {
                collisionTargets.put(clazz, 2.0D);
            }
        }
        // Add config's entity types
        for (String entry : UTConfigTweaks.PERFORMANCE.ENTITY_RADIUS_CHECK.utLessCollisionsExtraTargets)
        {
            // Parsing
            int separator = entry.indexOf(";");
            if (separator == -1)
            {
                UniversalTweaks.LOGGER.warn("UTEntityRadiusCheck ::: Unexpected format " + entry + "in \"[5] Less Collisions Extra Targets\"! Skipping this entry.");
                continue;
            }
            String entityId = entry.substring(0, separator);
            double radius;
            try
            {
                radius = Double.parseDouble(entry.substring(separator + 1));
            }
            catch (NumberFormatException ex)
            {
                UniversalTweaks.LOGGER.warn("UTEntityRadiusCheck ::: Unexpected format " + entry + "in \"[5] Less Collisions Extra Targets\"! Skipping this entry.");
                continue;
            }
            // Verifying
            Class<? extends Entity> entityClazz = getEntityType(entityId, ignored, true);
            if (entityClazz == null)
            {
                UniversalTweaks.LOGGER.warn("UTEntityRadiusCheck ::: Invalid entity id " + entityId + "in \"[5] Less Collisions Extra Targets\"! Skipping this entry.");
                continue;
            }
            if (radius <= 0.0D)
            {
                UniversalTweaks.LOGGER.warn("UTEntityRadiusCheck ::: Invalid radius " + radius + "in \"[5] Less Collisions Extra Targets\"! Skipping this entry.");
                continue;
            }
            collisionTargets.put(entityClazz, radius);
        }
    }

    private static @Nullable Class<? extends Entity> getEntityType(String entityId, ResourceLocation playerId, boolean nonVanillaOnly)
    {
        ResourceLocation entityLoc = new ResourceLocation(entityId);
        if (nonVanillaOnly && entityLoc.getNamespace().equals("minecraft")) return null;
        // Special case of player
        if (entityId.equals(playerId.toString()))
        {
            return EntityPlayer.class;
        }
        return EntityList.getClass(entityLoc);
    }

    private static Predicate<Class<? extends Entity>> buildVanillaPredicate()
    {
        // Conditions taken from RLTweaker's JsonConfigLessCollisions.
        // (1) No combat allies or offensive tools
        // (2) No mountables, except for pigs.
        // (3) No projectiles of any kind
        // (4) Caution with entities that may become the owner of explosions
        Predicate<Class<? extends Entity>> livingPredicate = new Predicate<Class<? extends Entity>>()
        {
            @Override
            public boolean test(Class<? extends Entity> entityClazz)
            {
                return EntityLivingBase.class.isAssignableFrom(entityClazz) &&
                    !(entityClazz == EntityWolf.class ||                        // (1)
                        AbstractHorse.class.isAssignableFrom(entityClazz) ||    // (2)
                        entityClazz == EntityPlayer.class ||
                        entityClazz == EntityDragon.class ||                    // (4)
                        entityClazz == EntityWither.class                       // (4)
                    );
            }
        };
        Predicate<Class<? extends Entity>> miscPredicate = new Predicate<Class<? extends Entity>>()
        {
            @Override
            public boolean test(Class<? extends Entity> entityClazz)
            {
                Collection<Class<? extends Entity>> allowed = ImmutableSet.of(
                    EntityItem.class,
                    EntityItemFrame.class,
                    EntityPainting.class,
                    EntityXPOrb.class
                );
                return allowed.contains(entityClazz);
            }
        };
        return livingPredicate.or(miscPredicate);
    }
}
