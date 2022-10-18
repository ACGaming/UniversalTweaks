package mod.acgaming.hkntweaks.tweaks.stronghold.worldgen;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.*;

/**
 * Courtesy of yungnickyoung
 * Replacement for normal strongholds.
 * Block placement does not accomodate for air, resulting in safe strongholds not being affected by caves.
 * Actual stronghold generation is deferred to later on in {@link SafeStrongholdWorldGenerator} to encourage
 * strongholds to overwrite other modded structures that may intersect.
 */
public class MapGenSafeStronghold extends MapGenStronghold
{
    // Store static copies of necessary vars for the SafeStrongholdWorldGenerator ...
    // ... to use later
    public static World sWorld = null;
    public static Random sRand = null;
    public static Start sStart = null;
    public static List<StructureComponent> sComponents = Lists.newLinkedList();
    public static boolean createdStronghold = false;
    public static int foundStrongholdX = 0;
    public static int foundStrongholdZ = 0;

    /**
     * This method is essentially the same thing as {@link MapGenStructure#generateStructure(World, Random, ChunkPos)},
     * but kept distinct for future extensibility and bug testing.
     */
    public synchronized void generateSafeStructure(World worldIn, Random randomIn, ChunkPos chunkCoord)
    {
        this.initializeStructureData(worldIn);
        int i = (chunkCoord.x << 4) + 8;
        int j = (chunkCoord.z << 4) + 8;
        for (StructureStart structureStart : this.structureMap.values())
        {
            Start structurestart = (Start) structureStart;
            if (structurestart.isSizeableStructure() && structurestart.isValidForPostProcess(chunkCoord) && structurestart.getBoundingBox().intersectsWith(i, j, i + 15, j + 15))
            {
                structurestart.generateSafeStructure(worldIn, randomIn, new StructureBoundingBox(i, j, i + 15, j + 15));
                structurestart.notifyPostProcessAt(chunkCoord);
            }
        }
    }

    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
        sWorld = this.world;
        sRand = this.rand;
        MapGenStructureIO.registerStructure(Start.class, "Stronghold");
        StructureSafeStrongholdPieces.registerStrongholdPieces();
        updateCurrStronghold(chunkX, chunkZ);
        return sStart;
    }

    private void updateCurrStronghold(int chunkX, int chunkZ)
    {
        foundStrongholdX = chunkX;
        foundStrongholdZ = chunkZ;
        // Create StructureStart. This will generate the list of components (stronghold pieces) ...
        // ... but the stronghold will not be generated until accessed by SafeStrongholdWorldGenerator.
        Start start;
        for (start = new Start(this.world, this.rand, chunkX, chunkZ); start.getComponents().isEmpty() || ((StructureSafeStrongholdPieces.Stairs2) start.getComponents().get(0)).strongholdPortalRoom == null; start = new Start(this.world, this.rand, chunkX, chunkZ))
        {
        }
        sStart = start;
        createdStronghold = true;
    }

    public static class Start extends StructureStart
    {
        public List<StructureComponent> list;
        public World world;
        public Random rand;
        public StructureSafeStrongholdPieces.Stairs2 structurestrongholdpieces$stairs2;
        public StructureBoundingBox _boundingBox;

        /**
         * Constructs the list of components belonging to this stronghold Start.
         * No blocks are actually placed until the {@link Start#generateSafeStructure} method is called.
         */
        public Start(World worldIn, Random random, int chunkX, int chunkZ)
        {
            super(chunkX, chunkZ);
            this.world = worldIn;
            this.rand = random;
            StructureSafeStrongholdPieces.prepareStructurePieces();
            structurestrongholdpieces$stairs2 = new StructureSafeStrongholdPieces.Stairs2(rand, (chunkX << 4) + 2, (chunkZ << 4) + 2);
            this.components.add(structurestrongholdpieces$stairs2);
            structurestrongholdpieces$stairs2.buildComponent(structurestrongholdpieces$stairs2, this.components, rand);
            populateComponents();
        }

        public void populateComponents()
        {
            list = structurestrongholdpieces$stairs2.pendingChildren;
            while (!list.isEmpty())
            {
                int i = rand.nextInt(list.size());
                StructureComponent structurecomponent = list.remove(i);
                structurecomponent.buildComponent(structurestrongholdpieces$stairs2, this.components, rand);
            }
            sComponents = this.components;
            this.updateBoundingBox();
            this._boundingBox = boundingBox;
            this.markAvailableHeight(world, rand, 10);
        }

        /**
         * This method is essentially the same thing as {@link StructureStart#generateStructure(World, Random, StructureBoundingBox)},
         * but kept distinct for future extensibility and bug testing.
         */
        public void generateSafeStructure(World worldIn, Random rand, StructureBoundingBox structurebb)
        {
            Iterator iterator = this.components.iterator();
            while (iterator.hasNext())
            {
                SafeStructureComponent structurecomponent = (SafeStructureComponent) iterator.next();
                if (structurecomponent.getBoundingBox().intersectsWith(structurebb) && !structurecomponent.addComponentParts(worldIn, rand, structurebb))
                {
                    iterator.remove();
                }
            }
        }
    }
}