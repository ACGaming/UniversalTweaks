package mod.acgaming.universaltweaks.tweaks.stronghold.worldgen;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

/**
 * Courtesy of yungnickyoung
 * This class is a direct copy of {@link net.minecraft.world.gen.structure.StructureStrongholdPieces}
 * but with {@link SafeStructureComponent} instead of {@link StructureComponent}, where necessary.
 * Streamlined some vanilla code ~ACGaming
 */
public class StructureSafeStrongholdPieces
{
    private static final StructureSafeStrongholdPieces.PieceWeight[] PIECE_WEIGHTS = new StructureSafeStrongholdPieces.PieceWeight[] {new StructureSafeStrongholdPieces.PieceWeight(StructureSafeStrongholdPieces.Straight.class, 40, 0), new StructureSafeStrongholdPieces.PieceWeight(StructureSafeStrongholdPieces.Prison.class, 5, 5), new StructureSafeStrongholdPieces.PieceWeight(StructureSafeStrongholdPieces.LeftTurn.class, 20, 0), new StructureSafeStrongholdPieces.PieceWeight(StructureSafeStrongholdPieces.RightTurn.class, 20, 0), new StructureSafeStrongholdPieces.PieceWeight(StructureSafeStrongholdPieces.RoomCrossing.class, 10, 6), new StructureSafeStrongholdPieces.PieceWeight(StructureSafeStrongholdPieces.StairsStraight.class, 5, 5), new StructureSafeStrongholdPieces.PieceWeight(StructureSafeStrongholdPieces.Stairs.class, 5, 5), new StructureSafeStrongholdPieces.PieceWeight(StructureSafeStrongholdPieces.Crossing.class, 5, 4), new StructureSafeStrongholdPieces.PieceWeight(StructureSafeStrongholdPieces.ChestCorridor.class, 5, 4), new StructureSafeStrongholdPieces.PieceWeight(StructureSafeStrongholdPieces.Library.class, 10, 2)
    {
        public boolean canSpawnMoreStructuresOfType(int p_75189_1_)
        {
            return super.canSpawnMoreStructuresOfType(p_75189_1_) && p_75189_1_ > 4;
        }
    }, new StructureSafeStrongholdPieces.PieceWeight(StructureSafeStrongholdPieces.PortalRoom.class, 20, 1)
    {
        public boolean canSpawnMoreStructuresOfType(int p_75189_1_)
        {
            return super.canSpawnMoreStructuresOfType(p_75189_1_) && p_75189_1_ > 5;
        }
    }};
    private static final StructureSafeStrongholdPieces.Stones STRONGHOLD_STONES = new StructureSafeStrongholdPieces.Stones();
    static int totalWeight;
    private static List<StructureSafeStrongholdPieces.PieceWeight> structurePieceList;
    private static Class<? extends StructureSafeStrongholdPieces.Stronghold> strongComponentType;

    public static void registerStrongholdPieces()
    {
        MapGenStructureIO.registerStructureComponent(StructureSafeStrongholdPieces.ChestCorridor.class, "SHCC");
        MapGenStructureIO.registerStructureComponent(StructureSafeStrongholdPieces.Corridor.class, "SHFC");
        MapGenStructureIO.registerStructureComponent(StructureSafeStrongholdPieces.Crossing.class, "SH5C");
        MapGenStructureIO.registerStructureComponent(StructureSafeStrongholdPieces.LeftTurn.class, "SHLT");
        MapGenStructureIO.registerStructureComponent(StructureSafeStrongholdPieces.Library.class, "SHLi");
        MapGenStructureIO.registerStructureComponent(StructureSafeStrongholdPieces.PortalRoom.class, "SHPR");
        MapGenStructureIO.registerStructureComponent(StructureSafeStrongholdPieces.Prison.class, "SHPH");
        MapGenStructureIO.registerStructureComponent(StructureSafeStrongholdPieces.RightTurn.class, "SHRT");
        MapGenStructureIO.registerStructureComponent(StructureSafeStrongholdPieces.RoomCrossing.class, "SHRC");
        MapGenStructureIO.registerStructureComponent(StructureSafeStrongholdPieces.Stairs.class, "SHSD");
        MapGenStructureIO.registerStructureComponent(StructureSafeStrongholdPieces.Stairs2.class, "SHStart");
        MapGenStructureIO.registerStructureComponent(StructureSafeStrongholdPieces.Straight.class, "SHS");
        MapGenStructureIO.registerStructureComponent(StructureSafeStrongholdPieces.StairsStraight.class, "SHSSD");
    }

    /**
     * sets up Arrays with the Structure pieces and their weights
     */
    public static void prepareStructurePieces()
    {
        structurePieceList = Lists.newArrayList();

        for (StructureSafeStrongholdPieces.PieceWeight StructureSafeStrongholdPieces$pieceweight : PIECE_WEIGHTS)
        {
            StructureSafeStrongholdPieces$pieceweight.instancesSpawned = 0;
            structurePieceList.add(StructureSafeStrongholdPieces$pieceweight);
        }

        strongComponentType = null;
    }

    private static boolean canAddStructurePieces()
    {
        boolean flag = false;
        totalWeight = 0;

        for (StructureSafeStrongholdPieces.PieceWeight StructureSafeStrongholdPieces$pieceweight : structurePieceList)
        {
            if (StructureSafeStrongholdPieces$pieceweight.instancesLimit > 0 && StructureSafeStrongholdPieces$pieceweight.instancesSpawned < StructureSafeStrongholdPieces$pieceweight.instancesLimit)
            {
                flag = true;
            }
            totalWeight += StructureSafeStrongholdPieces$pieceweight.pieceWeight;
        }
        return flag;
    }

    private static StructureSafeStrongholdPieces.Stronghold findAndCreatePieceFactory(Class<? extends StructureSafeStrongholdPieces.Stronghold> clazz, List<StructureComponent> p_175954_1_, Random p_175954_2_, int p_175954_3_, int p_175954_4_, int p_175954_5_, @Nullable EnumFacing p_175954_6_, int p_175954_7_)
    {
        StructureSafeStrongholdPieces.Stronghold StructureSafeStrongholdPieces$stronghold = null;
        if (clazz == StructureSafeStrongholdPieces.Straight.class)
        {
            StructureSafeStrongholdPieces$stronghold = StructureSafeStrongholdPieces.Straight.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (clazz == StructureSafeStrongholdPieces.Prison.class)
        {
            StructureSafeStrongholdPieces$stronghold = StructureSafeStrongholdPieces.Prison.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (clazz == StructureSafeStrongholdPieces.LeftTurn.class)
        {
            StructureSafeStrongholdPieces$stronghold = StructureSafeStrongholdPieces.LeftTurn.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (clazz == StructureSafeStrongholdPieces.RightTurn.class)
        {
            StructureSafeStrongholdPieces$stronghold = StructureSafeStrongholdPieces.RightTurn.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (clazz == StructureSafeStrongholdPieces.RoomCrossing.class)
        {
            StructureSafeStrongholdPieces$stronghold = StructureSafeStrongholdPieces.RoomCrossing.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (clazz == StructureSafeStrongholdPieces.StairsStraight.class)
        {
            StructureSafeStrongholdPieces$stronghold = StructureSafeStrongholdPieces.StairsStraight.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (clazz == StructureSafeStrongholdPieces.Stairs.class)
        {
            StructureSafeStrongholdPieces$stronghold = StructureSafeStrongholdPieces.Stairs.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (clazz == StructureSafeStrongholdPieces.Crossing.class)
        {
            StructureSafeStrongholdPieces$stronghold = StructureSafeStrongholdPieces.Crossing.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (clazz == StructureSafeStrongholdPieces.ChestCorridor.class)
        {
            StructureSafeStrongholdPieces$stronghold = StructureSafeStrongholdPieces.ChestCorridor.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (clazz == StructureSafeStrongholdPieces.Library.class)
        {
            StructureSafeStrongholdPieces$stronghold = StructureSafeStrongholdPieces.Library.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (clazz == StructureSafeStrongholdPieces.PortalRoom.class)
        {
            StructureSafeStrongholdPieces$stronghold = StructureSafeStrongholdPieces.PortalRoom.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        return StructureSafeStrongholdPieces$stronghold;
    }

    private static StructureSafeStrongholdPieces.Stronghold generatePieceFromSmallDoor(StructureSafeStrongholdPieces.Stairs2 p_175955_0_, List<StructureComponent> p_175955_1_, Random p_175955_2_, int p_175955_3_, int p_175955_4_, int p_175955_5_, EnumFacing p_175955_6_, int p_175955_7_)
    {
        if (!canAddStructurePieces())
        {
            return null;
        }
        else
        {
            if (strongComponentType != null)
            {
                StructureSafeStrongholdPieces.Stronghold StructureSafeStrongholdPieces$stronghold = findAndCreatePieceFactory(strongComponentType, p_175955_1_, p_175955_2_, p_175955_3_, p_175955_4_, p_175955_5_, p_175955_6_, p_175955_7_);
                strongComponentType = null;

                if (StructureSafeStrongholdPieces$stronghold != null)
                {
                    return StructureSafeStrongholdPieces$stronghold;
                }
            }
            int j = 0;
            while (j < 5)
            {
                ++j;
                int i = p_175955_2_.nextInt(totalWeight);

                for (StructureSafeStrongholdPieces.PieceWeight StructureSafeStrongholdPieces$pieceweight : structurePieceList)
                {
                    i -= StructureSafeStrongholdPieces$pieceweight.pieceWeight;
                    if (i < 0)
                    {
                        if (!StructureSafeStrongholdPieces$pieceweight.canSpawnMoreStructuresOfType(p_175955_7_) || StructureSafeStrongholdPieces$pieceweight == p_175955_0_.lastPlaced)
                        {
                            break;
                        }
                        StructureSafeStrongholdPieces.Stronghold StructureSafeStrongholdPieces$stronghold1 = findAndCreatePieceFactory(StructureSafeStrongholdPieces$pieceweight.pieceClass, p_175955_1_, p_175955_2_, p_175955_3_, p_175955_4_, p_175955_5_, p_175955_6_, p_175955_7_);
                        if (StructureSafeStrongholdPieces$stronghold1 != null)
                        {
                            ++StructureSafeStrongholdPieces$pieceweight.instancesSpawned;
                            p_175955_0_.lastPlaced = StructureSafeStrongholdPieces$pieceweight;
                            if (!StructureSafeStrongholdPieces$pieceweight.canSpawnMoreStructures())
                            {
                                structurePieceList.remove(StructureSafeStrongholdPieces$pieceweight);
                            }
                            return StructureSafeStrongholdPieces$stronghold1;
                        }
                    }
                }
            }
            StructureBoundingBox structureboundingbox = StructureSafeStrongholdPieces.Corridor.findPieceBox(p_175955_1_, p_175955_3_, p_175955_4_, p_175955_5_, p_175955_6_);
            if (structureboundingbox != null && structureboundingbox.minY > 1)
            {
                return new StructureSafeStrongholdPieces.Corridor(p_175955_7_, structureboundingbox, p_175955_6_);
            }
            else
            {
                return null;
            }
        }
    }

    private static StructureComponent generateAndAddPiece(StructureSafeStrongholdPieces.Stairs2 p_175953_0_, List<StructureComponent> p_175953_1_, Random p_175953_2_, int p_175953_3_, int p_175953_4_, int p_175953_5_, @Nullable EnumFacing p_175953_6_, int p_175953_7_)
    {
        if (p_175953_7_ > 50)
        {
            return null;
        }
        else if (Math.abs(p_175953_3_ - p_175953_0_.getBoundingBox().minX) <= 112 && Math.abs(p_175953_5_ - p_175953_0_.getBoundingBox().minZ) <= 112)
        {
            StructureComponent structurecomponent = generatePieceFromSmallDoor(p_175953_0_, p_175953_1_, p_175953_2_, p_175953_3_, p_175953_4_, p_175953_5_, p_175953_6_, p_175953_7_ + 1);
            if (structurecomponent != null)
            {
                p_175953_1_.add(structurecomponent);
                p_175953_0_.pendingChildren.add(structurecomponent);
            }
            return structurecomponent;
        }
        else
        {
            return null;
        }
    }

    public static class ChestCorridor extends StructureSafeStrongholdPieces.Stronghold
    {
        public static StructureSafeStrongholdPieces.ChestCorridor createPiece(List<StructureComponent> p_175868_0_, Random p_175868_1_, int p_175868_2_, int p_175868_3_, int p_175868_4_, EnumFacing p_175868_5_, int p_175868_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175868_2_, p_175868_3_, p_175868_4_, -1, -1, 0, 5, 5, 7, p_175868_5_);
            return canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175868_0_, structureboundingbox) == null ? new StructureSafeStrongholdPieces.ChestCorridor(p_175868_6_, p_175868_1_, structureboundingbox, p_175868_5_) : null;
        }

        private boolean hasMadeChest;

        public ChestCorridor(int p_i45582_1_, Random p_i45582_2_, StructureBoundingBox p_i45582_3_, EnumFacing p_i45582_4_)
        {
            super(p_i45582_1_);
            this.setCoordBaseMode(p_i45582_4_);
            this.entryDoor = this.getRandomDoor(p_i45582_2_);
            this.boundingBox = p_i45582_3_;
        }

        /**
         * Initiates construction of the Structure Component picked, at the current Location of StructGen
         */
        public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand)
        {
            this.getNextComponentNormal((StructureSafeStrongholdPieces.Stairs2) componentIn, listIn, rand, 1, 1);
        }

        /**
         * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
         * Mineshafts at the end, it adds Fences...
         */
        public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
        {
            if (this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
            {
                return false;
            }
            else
            {
                this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 4, 6, true, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
                this.placeDoor(worldIn, structureBoundingBoxIn, this.entryDoor, 1, 1, 0);
                this.placeDoor(worldIn, structureBoundingBoxIn, StructureSafeStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 6);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 2, 3, 1, 4, Blocks.STONEBRICK.getDefaultState(), Blocks.STONEBRICK.getDefaultState(), false);
                this.setBlockState(worldIn, Blocks.STONE_SLAB.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 1, 1, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.STONE_SLAB.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 1, 5, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.STONE_SLAB.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 2, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.STONE_SLAB.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 2, 4, structureBoundingBoxIn);
                for (int i = 2; i <= 4; ++i)
                {
                    this.setBlockState(worldIn, Blocks.STONE_SLAB.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 2, 1, i, structureBoundingBoxIn);
                }
                if (!this.hasMadeChest && structureBoundingBoxIn.isVecInside(new BlockPos(this.getXWithOffset(3, 3), this.getYWithOffset(2), this.getZWithOffset(3, 3))))
                {
                    this.hasMadeChest = true;
                    this.generateChest(worldIn, structureBoundingBoxIn, randomIn, 3, 2, 3, LootTableList.CHESTS_STRONGHOLD_CORRIDOR);
                }
                return true;
            }
        }

        /**
         * (abstract) Helper method to write subclass data to NBT
         */
        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setBoolean("Chest", this.hasMadeChest);
        }

        /**
         * (abstract) Helper method to read subclass data from NBT
         */
        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            super.readStructureFromNBT(tagCompound, p_143011_2_);
            this.hasMadeChest = tagCompound.getBoolean("Chest");
        }
    }

    public static class Corridor extends StructureSafeStrongholdPieces.Stronghold
    {
        public static StructureBoundingBox findPieceBox(List<StructureComponent> p_175869_0_, int p_175869_2_, int p_175869_3_, int p_175869_4_, EnumFacing p_175869_5_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5, 5, 4, p_175869_5_);
            StructureComponent structurecomponent = StructureComponent.findIntersecting(p_175869_0_, structureboundingbox);

            if (structurecomponent.getBoundingBox().minY == structureboundingbox.minY)
            {
                for (int j = 3; j >= 1; --j)
                {
                    structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5, 5, j - 1, p_175869_5_);

                    if (!structurecomponent.getBoundingBox().intersectsWith(structureboundingbox))
                    {
                        return StructureBoundingBox.getComponentToAddBoundingBox(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5, 5, j, p_175869_5_);
                    }
                }
            }
            return null;
        }

        private int steps;

        public Corridor(int p_i45581_1_, StructureBoundingBox p_i45581_3_, EnumFacing p_i45581_4_)
        {
            super(p_i45581_1_);
            this.setCoordBaseMode(p_i45581_4_);
            this.boundingBox = p_i45581_3_;
            this.steps = p_i45581_4_ != EnumFacing.NORTH && p_i45581_4_ != EnumFacing.SOUTH ? p_i45581_3_.getXSize() : p_i45581_3_.getZSize();
        }

        /**
         * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
         * Mineshafts at the end, it adds Fences...
         */
        public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
        {
            if (this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
            {
                return false;
            }
            else
            {
                for (int i = 0; i < this.steps; ++i)
                {
                    this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 0, 0, i, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 1, 0, i, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 2, 0, i, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 3, 0, i, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 4, 0, i, structureBoundingBoxIn);

                    for (int j = 1; j <= 3; ++j)
                    {
                        this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 0, j, i, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, j, i, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, j, i, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 3, j, i, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 4, j, i, structureBoundingBoxIn);
                    }

                    this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 0, 4, i, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 1, 4, i, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 2, 4, i, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 3, 4, i, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 4, 4, i, structureBoundingBoxIn);
                }

                return true;
            }
        }

        /**
         * (abstract) Helper method to write subclass data to NBT
         */
        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setInteger("Steps", this.steps);
        }

        /**
         * (abstract) Helper method to read subclass data from NBT
         */
        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            super.readStructureFromNBT(tagCompound, p_143011_2_);
            this.steps = tagCompound.getInteger("Steps");
        }
    }

    public static class Crossing extends StructureSafeStrongholdPieces.Stronghold
    {
        public static StructureSafeStrongholdPieces.Crossing createPiece(List<StructureComponent> p_175866_0_, Random p_175866_1_, int p_175866_2_, int p_175866_3_, int p_175866_4_, EnumFacing p_175866_5_, int p_175866_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175866_2_, p_175866_3_, p_175866_4_, -4, -3, 0, 10, 9, 11, p_175866_5_);
            return canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175866_0_, structureboundingbox) == null ? new StructureSafeStrongholdPieces.Crossing(p_175866_6_, p_175866_1_, structureboundingbox, p_175866_5_) : null;
        }

        private boolean leftLow;
        private boolean leftHigh;
        private boolean rightLow;
        private boolean rightHigh;

        public Crossing(int p_i45580_1_, Random p_i45580_2_, StructureBoundingBox p_i45580_3_, EnumFacing p_i45580_4_)
        {
            super(p_i45580_1_);
            this.setCoordBaseMode(p_i45580_4_);
            this.entryDoor = this.getRandomDoor(p_i45580_2_);
            this.boundingBox = p_i45580_3_;
            this.leftLow = p_i45580_2_.nextBoolean();
            this.leftHigh = p_i45580_2_.nextBoolean();
            this.rightLow = p_i45580_2_.nextBoolean();
            this.rightHigh = p_i45580_2_.nextInt(3) > 0;
        }

        /**
         * Initiates construction of the Structure Component picked, at the current Location of StructGen
         */
        public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand)
        {
            int i = 3;
            int j = 5;
            EnumFacing enumfacing = this.getCoordBaseMode();
            if (enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.NORTH)
            {
                i = 8 - i;
                j = 8 - j;
            }
            this.getNextComponentNormal((StructureSafeStrongholdPieces.Stairs2) componentIn, listIn, rand, 5, 1);
            if (this.leftLow)
            {
                this.getNextComponentX((StructureSafeStrongholdPieces.Stairs2) componentIn, listIn, rand, i, 1);
            }
            if (this.leftHigh)
            {
                this.getNextComponentX((StructureSafeStrongholdPieces.Stairs2) componentIn, listIn, rand, j, 7);
            }
            if (this.rightLow)
            {
                this.getNextComponentZ((StructureSafeStrongholdPieces.Stairs2) componentIn, listIn, rand, i, 1);
            }
            if (this.rightHigh)
            {
                this.getNextComponentZ((StructureSafeStrongholdPieces.Stairs2) componentIn, listIn, rand, j, 7);
            }
        }

        /**
         * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
         * Mineshafts at the end, it adds Fences...
         */
        public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
        {
            if (this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
            {
                return false;
            }
            else
            {
                this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 9, 8, 10, true, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
                this.placeDoor(worldIn, structureBoundingBoxIn, this.entryDoor, 4, 3, 0);

                if (this.leftLow)
                {
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 1, 0, 5, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                }

                if (this.rightLow)
                {
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 3, 1, 9, 5, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                }

                if (this.leftHigh)
                {
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 7, 0, 7, 9, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                }

                if (this.rightHigh)
                {
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 5, 7, 9, 7, 9, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                }
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 10, 7, 3, 10, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 2, 1, 8, 2, 6, false, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
                this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 5, 4, 4, 9, false, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
                this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 8, 1, 5, 8, 4, 9, false, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
                this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 4, 7, 3, 4, 9, false, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
                this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 3, 5, 3, 3, 6, false, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 4, 3, 3, 4, Blocks.STONE_SLAB.getDefaultState(), Blocks.STONE_SLAB.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 6, 3, 4, 6, Blocks.STONE_SLAB.getDefaultState(), Blocks.STONE_SLAB.getDefaultState(), false);
                this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 5, 1, 7, 7, 1, 8, false, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 9, 7, 1, 9, Blocks.STONE_SLAB.getDefaultState(), Blocks.STONE_SLAB.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 7, 7, 2, 7, Blocks.STONE_SLAB.getDefaultState(), Blocks.STONE_SLAB.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 7, 4, 5, 9, Blocks.STONE_SLAB.getDefaultState(), Blocks.STONE_SLAB.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 5, 7, 8, 5, 9, Blocks.STONE_SLAB.getDefaultState(), Blocks.STONE_SLAB.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 5, 7, 7, 5, 9, Blocks.DOUBLE_STONE_SLAB.getDefaultState(), Blocks.DOUBLE_STONE_SLAB.getDefaultState(), false);
                this.setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.SOUTH), 6, 5, 6, structureBoundingBoxIn);
                return true;
            }
        }

        /**
         * (abstract) Helper method to write subclass data to NBT
         */
        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setBoolean("leftLow", this.leftLow);
            tagCompound.setBoolean("leftHigh", this.leftHigh);
            tagCompound.setBoolean("rightLow", this.rightLow);
            tagCompound.setBoolean("rightHigh", this.rightHigh);
        }

        /**
         * (abstract) Helper method to read subclass data from NBT
         */
        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            super.readStructureFromNBT(tagCompound, p_143011_2_);
            this.leftLow = tagCompound.getBoolean("leftLow");
            this.leftHigh = tagCompound.getBoolean("leftHigh");
            this.rightLow = tagCompound.getBoolean("rightLow");
            this.rightHigh = tagCompound.getBoolean("rightHigh");
        }
    }

    public static class LeftTurn extends StructureSafeStrongholdPieces.Stronghold
    {
        public static StructureSafeStrongholdPieces.LeftTurn createPiece(List<StructureComponent> p_175867_0_, Random p_175867_1_, int p_175867_2_, int p_175867_3_, int p_175867_4_, EnumFacing p_175867_5_, int p_175867_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175867_2_, p_175867_3_, p_175867_4_, -1, -1, 0, 5, 5, 5, p_175867_5_);
            return canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175867_0_, structureboundingbox) == null ? new StructureSafeStrongholdPieces.LeftTurn(p_175867_6_, p_175867_1_, structureboundingbox, p_175867_5_) : null;
        }

        public LeftTurn()
        {
        }

        public LeftTurn(int p_i45579_1_, Random p_i45579_2_, StructureBoundingBox p_i45579_3_, EnumFacing p_i45579_4_)
        {
            super(p_i45579_1_);
            this.setCoordBaseMode(p_i45579_4_);
            this.entryDoor = this.getRandomDoor(p_i45579_2_);
            this.boundingBox = p_i45579_3_;
        }

        /**
         * Initiates construction of the Structure Component picked, at the current Location of StructGen
         */
        public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand)
        {
            EnumFacing enumfacing = this.getCoordBaseMode();
            if (enumfacing != EnumFacing.NORTH && enumfacing != EnumFacing.EAST)
            {
                this.getNextComponentZ((StructureSafeStrongholdPieces.Stairs2) componentIn, listIn, rand, 1, 1);
            }
            else
            {
                this.getNextComponentX((StructureSafeStrongholdPieces.Stairs2) componentIn, listIn, rand, 1, 1);
            }
        }

        /**
         * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
         * Mineshafts at the end, it adds Fences...
         */
        public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
        {
            if (this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
            {
                return false;
            }
            else
            {
                this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 4, 4, true, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
                this.placeDoor(worldIn, structureBoundingBoxIn, this.entryDoor, 1, 1, 0);
                EnumFacing enumfacing = this.getCoordBaseMode();

                if (enumfacing != EnumFacing.NORTH && enumfacing != EnumFacing.EAST)
                {
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                }
                else
                {
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                }

                return true;
            }
        }
    }

    public static class Library extends StructureSafeStrongholdPieces.Stronghold
    {
        public static StructureSafeStrongholdPieces.Library createPiece(List<StructureComponent> p_175864_0_, Random p_175864_1_, int p_175864_2_, int p_175864_3_, int p_175864_4_, EnumFacing p_175864_5_, int p_175864_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175864_2_, p_175864_3_, p_175864_4_, -4, -1, 0, 14, 11, 15, p_175864_5_);
            if (!canStrongholdGoDeeper(structureboundingbox) || StructureComponent.findIntersecting(p_175864_0_, structureboundingbox) != null)
            {
                structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175864_2_, p_175864_3_, p_175864_4_, -4, -1, 0, 14, 6, 15, p_175864_5_);
                if (!canStrongholdGoDeeper(structureboundingbox) || StructureComponent.findIntersecting(p_175864_0_, structureboundingbox) != null)
                {
                    return null;
                }
            }
            return new StructureSafeStrongholdPieces.Library(p_175864_6_, p_175864_1_, structureboundingbox, p_175864_5_);
        }

        private boolean isLargeRoom;

        public Library(int p_i45578_1_, Random p_i45578_2_, StructureBoundingBox p_i45578_3_, EnumFacing p_i45578_4_)
        {
            super(p_i45578_1_);
            this.setCoordBaseMode(p_i45578_4_);
            this.entryDoor = this.getRandomDoor(p_i45578_2_);
            this.boundingBox = p_i45578_3_;
            this.isLargeRoom = p_i45578_3_.getYSize() > 6;
        }

        /**
         * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
         * Mineshafts at the end, it adds Fences...
         */
        public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
        {
            if (this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
            {
                return false;
            }
            else
            {
                int i = 11;
                if (!this.isLargeRoom)
                {
                    i = 6;
                }
                this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 13, i - 1, 14, true, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
                this.placeDoor(worldIn, structureBoundingBoxIn, this.entryDoor, 4, 1, 0);
                this.generateMaybeBox(worldIn, structureBoundingBoxIn, randomIn, 0.07F, 2, 1, 1, 11, 4, 13, Blocks.WEB.getDefaultState(), Blocks.WEB.getDefaultState(), false, 0);
                for (int l = 1; l <= 13; ++l)
                {
                    if ((l - 1) % 4 == 0)
                    {
                        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, l, 1, 4, l, Blocks.PLANKS.getDefaultState(), Blocks.PLANKS.getDefaultState(), false);
                        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 1, l, 12, 4, l, Blocks.PLANKS.getDefaultState(), Blocks.PLANKS.getDefaultState(), false);
                        this.setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.EAST), 2, 3, l, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.WEST), 11, 3, l, structureBoundingBoxIn);
                        if (this.isLargeRoom)
                        {
                            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 6, l, 1, 9, l, Blocks.PLANKS.getDefaultState(), Blocks.PLANKS.getDefaultState(), false);
                            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 6, l, 12, 9, l, Blocks.PLANKS.getDefaultState(), Blocks.PLANKS.getDefaultState(), false);
                        }
                    }
                    else
                    {
                        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, l, 1, 4, l, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
                        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 1, l, 12, 4, l, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);

                        if (this.isLargeRoom)
                        {
                            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 6, l, 1, 9, l, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
                            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 6, l, 12, 9, l, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
                        }
                    }
                }
                for (int k1 = 3; k1 < 12; k1 += 2)
                {
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, k1, 4, 3, k1, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, k1, 7, 3, k1, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 1, k1, 10, 3, k1, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
                }
                if (this.isLargeRoom)
                {
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 3, 5, 13, Blocks.PLANKS.getDefaultState(), Blocks.PLANKS.getDefaultState(), false);
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 5, 1, 12, 5, 13, Blocks.PLANKS.getDefaultState(), Blocks.PLANKS.getDefaultState(), false);
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 1, 9, 5, 2, Blocks.PLANKS.getDefaultState(), Blocks.PLANKS.getDefaultState(), false);
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 12, 9, 5, 13, Blocks.PLANKS.getDefaultState(), Blocks.PLANKS.getDefaultState(), false);
                    this.setBlockState(worldIn, Blocks.PLANKS.getDefaultState(), 9, 5, 11, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.PLANKS.getDefaultState(), 8, 5, 11, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.PLANKS.getDefaultState(), 9, 5, 10, structureBoundingBoxIn);
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 6, 2, 3, 6, 12, Blocks.OAK_FENCE.getDefaultState(), Blocks.OAK_FENCE.getDefaultState(), false);
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 6, 2, 10, 6, 10, Blocks.OAK_FENCE.getDefaultState(), Blocks.OAK_FENCE.getDefaultState(), false);
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 6, 2, 9, 6, 2, Blocks.OAK_FENCE.getDefaultState(), Blocks.OAK_FENCE.getDefaultState(), false);
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 6, 12, 8, 6, 12, Blocks.OAK_FENCE.getDefaultState(), Blocks.OAK_FENCE.getDefaultState(), false);
                    this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 9, 6, 11, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 8, 6, 11, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 9, 6, 10, structureBoundingBoxIn);
                    IBlockState iblockstate1 = Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.SOUTH);
                    this.setBlockState(worldIn, iblockstate1, 10, 1, 13, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate1, 10, 2, 13, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate1, 10, 3, 13, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate1, 10, 4, 13, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate1, 10, 5, 13, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate1, 10, 6, 13, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate1, 10, 7, 13, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 6, 9, 7, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 7, 9, 7, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 6, 8, 7, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 7, 8, 7, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 6, 7, 7, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 7, 7, 7, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 5, 7, 7, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 8, 7, 7, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 6, 7, 6, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 6, 7, 8, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 7, 7, 6, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 7, 7, 8, structureBoundingBoxIn);
                    IBlockState iblockstate = Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.UP);
                    this.setBlockState(worldIn, iblockstate, 5, 8, 7, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate, 8, 8, 7, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate, 6, 8, 6, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate, 6, 8, 8, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate, 7, 8, 6, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate, 7, 8, 8, structureBoundingBoxIn);
                }
                this.generateChest(worldIn, structureBoundingBoxIn, randomIn, 3, 3, 5, LootTableList.CHESTS_STRONGHOLD_LIBRARY);
                if (this.isLargeRoom)
                {
                    this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 12, 9, 1, structureBoundingBoxIn);
                    this.generateChest(worldIn, structureBoundingBoxIn, randomIn, 12, 8, 1, LootTableList.CHESTS_STRONGHOLD_LIBRARY);
                }
                return true;
            }
        }

        /**
         * (abstract) Helper method to write subclass data to NBT
         */
        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setBoolean("Tall", this.isLargeRoom);
        }

        /**
         * (abstract) Helper method to read subclass data from NBT
         */
        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            super.readStructureFromNBT(tagCompound, p_143011_2_);
            this.isLargeRoom = tagCompound.getBoolean("Tall");
        }
    }

    static class PieceWeight
    {
        /**
         * This basically keeps track of the 'epicness' of a structure. Epic structure components have a higher
         * 'weight', and Structures may only grow up to a certain 'weight' before generation is stopped
         */
        public final int pieceWeight;
        public Class<? extends StructureSafeStrongholdPieces.Stronghold> pieceClass;
        public int instancesSpawned;
        /**
         * How many Structure Pieces of this type may spawn in a structure
         */
        public int instancesLimit;

        public PieceWeight(Class<? extends StructureSafeStrongholdPieces.Stronghold> p_i2076_1_, int p_i2076_2_, int p_i2076_3_)
        {
            this.pieceClass = p_i2076_1_;
            this.pieceWeight = p_i2076_2_;
            this.instancesLimit = p_i2076_3_;
        }

        public boolean canSpawnMoreStructuresOfType(int p_75189_1_)
        {
            return this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit;
        }

        public boolean canSpawnMoreStructures()
        {
            return this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit;
        }
    }

    public static class PortalRoom extends StructureSafeStrongholdPieces.Stronghold
    {
        public static StructureSafeStrongholdPieces.PortalRoom createPiece(List<StructureComponent> p_175865_0_, Random p_175865_1_, int p_175865_2_, int p_175865_3_, int p_175865_4_, EnumFacing p_175865_5_, int p_175865_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175865_2_, p_175865_3_, p_175865_4_, -4, -1, 0, 11, 8, 16, p_175865_5_);
            return canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175865_0_, structureboundingbox) == null ? new StructureSafeStrongholdPieces.PortalRoom(p_175865_6_, p_175865_1_, structureboundingbox, p_175865_5_) : null;
        }

        private boolean hasSpawner;

        public PortalRoom(int p_i45577_1_, Random p_i45577_2_, StructureBoundingBox p_i45577_3_, EnumFacing p_i45577_4_)
        {
            super(p_i45577_1_);
            this.setCoordBaseMode(p_i45577_4_);
            this.boundingBox = p_i45577_3_;
        }

        /**
         * Initiates construction of the Structure Component picked, at the current Location of StructGen
         */
        public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand)
        {
            if (componentIn != null)
            {
                ((StructureSafeStrongholdPieces.Stairs2) componentIn).strongholdPortalRoom = this;
            }
        }

        /**
         * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
         * Mineshafts at the end, it adds Fences...
         */
        public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
        {
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 10, 7, 15, false, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
            this.placeDoor(worldIn, structureBoundingBoxIn, StructureSafeStrongholdPieces.Stronghold.Door.GRATES, 4, 1, 0);
            int i = 6;
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, i, 1, 1, i, 14, false, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 9, i, 1, 9, i, 14, false, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, i, 1, 8, i, 2, false, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, i, 14, 8, i, 14, false, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 2, 1, 4, false, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 8, 1, 1, 9, 1, 4, false, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 1, 1, 3, Blocks.FLOWING_LAVA.getDefaultState(), Blocks.FLOWING_LAVA.getDefaultState(), false);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 1, 1, 9, 1, 3, Blocks.FLOWING_LAVA.getDefaultState(), Blocks.FLOWING_LAVA.getDefaultState(), false);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 3, 1, 8, 7, 1, 12, false, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
            this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 9, 6, 1, 11, Blocks.FLOWING_LAVA.getDefaultState(), Blocks.FLOWING_LAVA.getDefaultState(), false);
            for (int j = 3; j < 14; j += 2)
            {
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, j, 0, 4, j, Blocks.IRON_BARS.getDefaultState(), Blocks.IRON_BARS.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 3, j, 10, 4, j, Blocks.IRON_BARS.getDefaultState(), Blocks.IRON_BARS.getDefaultState(), false);
            }
            for (int i1 = 2; i1 < 9; i1 += 2)
            {
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, i1, 3, 15, i1, 4, 15, Blocks.IRON_BARS.getDefaultState(), Blocks.IRON_BARS.getDefaultState(), false);
            }
            IBlockState iblockstate3 = Blocks.STONE_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 5, 6, 1, 7, false, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 2, 6, 6, 2, 7, false, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 3, 7, 6, 3, 7, false, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
            for (int k = 4; k <= 6; ++k)
            {
                this.setBlockState(worldIn, iblockstate3, k, 1, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate3, k, 2, 5, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate3, k, 3, 6, structureBoundingBoxIn);
            }
            IBlockState iblockstate4 = Blocks.END_PORTAL_FRAME.getDefaultState().withProperty(BlockEndPortalFrame.FACING, EnumFacing.NORTH);
            IBlockState iblockstate = Blocks.END_PORTAL_FRAME.getDefaultState().withProperty(BlockEndPortalFrame.FACING, EnumFacing.SOUTH);
            IBlockState iblockstate1 = Blocks.END_PORTAL_FRAME.getDefaultState().withProperty(BlockEndPortalFrame.FACING, EnumFacing.EAST);
            IBlockState iblockstate2 = Blocks.END_PORTAL_FRAME.getDefaultState().withProperty(BlockEndPortalFrame.FACING, EnumFacing.WEST);
            boolean flag = true;
            boolean[] aboolean = new boolean[12];
            for (int l = 0; l < aboolean.length; ++l)
            {
                aboolean[l] = randomIn.nextFloat() > 0.9F;
                flag &= aboolean[l];
            }
            this.setBlockState(worldIn, iblockstate4.withProperty(BlockEndPortalFrame.EYE, aboolean[0]), 4, 3, 8, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate4.withProperty(BlockEndPortalFrame.EYE, aboolean[1]), 5, 3, 8, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate4.withProperty(BlockEndPortalFrame.EYE, aboolean[2]), 6, 3, 8, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate.withProperty(BlockEndPortalFrame.EYE, aboolean[3]), 4, 3, 12, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate.withProperty(BlockEndPortalFrame.EYE, aboolean[4]), 5, 3, 12, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate.withProperty(BlockEndPortalFrame.EYE, aboolean[5]), 6, 3, 12, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate1.withProperty(BlockEndPortalFrame.EYE, aboolean[6]), 3, 3, 9, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate1.withProperty(BlockEndPortalFrame.EYE, aboolean[7]), 3, 3, 10, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate1.withProperty(BlockEndPortalFrame.EYE, aboolean[8]), 3, 3, 11, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate2.withProperty(BlockEndPortalFrame.EYE, aboolean[9]), 7, 3, 9, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate2.withProperty(BlockEndPortalFrame.EYE, aboolean[10]), 7, 3, 10, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate2.withProperty(BlockEndPortalFrame.EYE, aboolean[11]), 7, 3, 11, structureBoundingBoxIn);
            if (flag)
            {
                IBlockState iblockstate5 = Blocks.END_PORTAL.getDefaultState();
                this.setBlockState(worldIn, iblockstate5, 4, 3, 9, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate5, 5, 3, 9, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate5, 6, 3, 9, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate5, 4, 3, 10, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate5, 5, 3, 10, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate5, 6, 3, 10, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate5, 4, 3, 11, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate5, 5, 3, 11, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate5, 6, 3, 11, structureBoundingBoxIn);
            }
            if (!this.hasSpawner)
            {
                i = this.getYWithOffset(3);
                BlockPos blockpos = new BlockPos(this.getXWithOffset(5, 6), i, this.getZWithOffset(5, 6));
                if (structureBoundingBoxIn.isVecInside(blockpos))
                {
                    this.hasSpawner = true;
                    worldIn.setBlockState(blockpos, Blocks.MOB_SPAWNER.getDefaultState(), 2);
                    TileEntity tileentity = worldIn.getTileEntity(blockpos);
                    if (tileentity instanceof TileEntityMobSpawner)
                    {
                        ((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setEntityId(EntityList.getKey(EntitySilverfish.class));
                    }
                }
            }
            return true;
        }

        /**
         * (abstract) Helper method to write subclass data to NBT
         */
        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setBoolean("Mob", this.hasSpawner);
        }

        /**
         * (abstract) Helper method to read subclass data from NBT
         */
        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            super.readStructureFromNBT(tagCompound, p_143011_2_);
            this.hasSpawner = tagCompound.getBoolean("Mob");
        }
    }

    public static class Prison extends StructureSafeStrongholdPieces.Stronghold
    {
        public static StructureSafeStrongholdPieces.Prison createPiece(List<StructureComponent> p_175860_0_, Random p_175860_1_, int p_175860_2_, int p_175860_3_, int p_175860_4_, EnumFacing p_175860_5_, int p_175860_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175860_2_, p_175860_3_, p_175860_4_, -1, -1, 0, 9, 5, 11, p_175860_5_);
            return canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175860_0_, structureboundingbox) == null ? new StructureSafeStrongholdPieces.Prison(p_175860_6_, p_175860_1_, structureboundingbox, p_175860_5_) : null;
        }

        public Prison(int p_i45576_1_, Random p_i45576_2_, StructureBoundingBox p_i45576_3_, EnumFacing p_i45576_4_)
        {
            super(p_i45576_1_);
            this.setCoordBaseMode(p_i45576_4_);
            this.entryDoor = this.getRandomDoor(p_i45576_2_);
            this.boundingBox = p_i45576_3_;
        }

        /**
         * Initiates construction of the Structure Component picked, at the current Location of StructGen
         */
        public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand)
        {
            this.getNextComponentNormal((StructureSafeStrongholdPieces.Stairs2) componentIn, listIn, rand, 1, 1);
        }

        /**
         * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
         * Mineshafts at the end, it adds Fences...
         */
        public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
        {
            if (this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
            {
                return false;
            }
            else
            {
                this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 8, 4, 10, true, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
                this.placeDoor(worldIn, structureBoundingBoxIn, this.entryDoor, 1, 1, 0);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 10, 3, 3, 10, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 1, false, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
                this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 3, 4, 3, 3, false, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
                this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 7, 4, 3, 7, false, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
                this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 9, 4, 3, 9, false, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 4, 4, 3, 6, Blocks.IRON_BARS.getDefaultState(), Blocks.IRON_BARS.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 5, 7, 3, 5, Blocks.IRON_BARS.getDefaultState(), Blocks.IRON_BARS.getDefaultState(), false);
                this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), 4, 3, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), 4, 3, 8, structureBoundingBoxIn);
                IBlockState iblockstate = Blocks.IRON_DOOR.getDefaultState().withProperty(BlockDoor.FACING, EnumFacing.WEST);
                IBlockState iblockstate1 = Blocks.IRON_DOOR.getDefaultState().withProperty(BlockDoor.FACING, EnumFacing.WEST).withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER);
                this.setBlockState(worldIn, iblockstate, 4, 1, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1, 4, 2, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate, 4, 1, 8, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1, 4, 2, 8, structureBoundingBoxIn);
                return true;
            }
        }
    }

    public static class RightTurn extends StructureSafeStrongholdPieces.LeftTurn
    {
        /**
         * Initiates construction of the Structure Component picked, at the current Location of StructGen
         */
        public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand)
        {
            EnumFacing enumfacing = this.getCoordBaseMode();
            if (enumfacing != EnumFacing.NORTH && enumfacing != EnumFacing.EAST)
            {
                this.getNextComponentX((StructureSafeStrongholdPieces.Stairs2) componentIn, listIn, rand, 1, 1);
            }
            else
            {
                this.getNextComponentZ((StructureSafeStrongholdPieces.Stairs2) componentIn, listIn, rand, 1, 1);
            }
        }

        /**
         * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
         * Mineshafts at the end, it adds Fences...
         */
        public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
        {
            if (this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
            {
                return false;
            }
            else
            {
                this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 4, 4, true, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
                this.placeDoor(worldIn, structureBoundingBoxIn, this.entryDoor, 1, 1, 0);
                EnumFacing enumfacing = this.getCoordBaseMode();
                if (enumfacing != EnumFacing.NORTH && enumfacing != EnumFacing.EAST)
                {
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                }
                else
                {
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                }
                return true;
            }
        }
    }

    public static class RoomCrossing extends StructureSafeStrongholdPieces.Stronghold
    {
        public static StructureSafeStrongholdPieces.RoomCrossing createPiece(List<StructureComponent> p_175859_0_, Random p_175859_1_, int p_175859_2_, int p_175859_3_, int p_175859_4_, EnumFacing p_175859_5_, int p_175859_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175859_2_, p_175859_3_, p_175859_4_, -4, -1, 0, 11, 7, 11, p_175859_5_);
            return canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175859_0_, structureboundingbox) == null ? new StructureSafeStrongholdPieces.RoomCrossing(p_175859_6_, p_175859_1_, structureboundingbox, p_175859_5_) : null;
        }

        protected int roomType;

        public RoomCrossing(int p_i45575_1_, Random p_i45575_2_, StructureBoundingBox p_i45575_3_, EnumFacing p_i45575_4_)
        {
            super(p_i45575_1_);
            this.setCoordBaseMode(p_i45575_4_);
            this.entryDoor = this.getRandomDoor(p_i45575_2_);
            this.boundingBox = p_i45575_3_;
            this.roomType = p_i45575_2_.nextInt(5);
        }

        /**
         * Initiates construction of the Structure Component picked, at the current Location of StructGen
         */
        public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand)
        {
            this.getNextComponentNormal((StructureSafeStrongholdPieces.Stairs2) componentIn, listIn, rand, 4, 1);
            this.getNextComponentX((StructureSafeStrongholdPieces.Stairs2) componentIn, listIn, rand, 1, 4);
            this.getNextComponentZ((StructureSafeStrongholdPieces.Stairs2) componentIn, listIn, rand, 1, 4);
        }

        /**
         * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
         * Mineshafts at the end, it adds Fences...
         */
        public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
        {
            if (this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
            {
                return false;
            }
            else
            {
                this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 10, 6, 10, true, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
                this.placeDoor(worldIn, structureBoundingBoxIn, this.entryDoor, 4, 1, 0);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 10, 6, 3, 10, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 4, 0, 3, 6, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 1, 4, 10, 3, 6, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                switch (this.roomType)
                {
                    case 0:
                        this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 5, 1, 5, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 5, 2, 5, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 5, 3, 5, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.WEST), 4, 3, 5, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.EAST), 6, 3, 5, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.SOUTH), 5, 3, 4, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.NORTH), 5, 3, 6, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 4, 1, 4, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 4, 1, 5, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 4, 1, 6, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 6, 1, 4, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 6, 1, 5, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 6, 1, 6, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 5, 1, 4, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 5, 1, 6, structureBoundingBoxIn);
                        break;
                    case 1:
                        for (int i1 = 0; i1 < 5; ++i1)
                        {
                            this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 3, 1, 3 + i1, structureBoundingBoxIn);
                            this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 7, 1, 3 + i1, structureBoundingBoxIn);
                            this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 3 + i1, 1, 3, structureBoundingBoxIn);
                            this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 3 + i1, 1, 7, structureBoundingBoxIn);
                        }

                        this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 5, 1, 5, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 5, 2, 5, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 5, 3, 5, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.FLOWING_WATER.getDefaultState(), 5, 4, 5, structureBoundingBoxIn);
                        break;
                    case 2:
                        for (int i = 1; i <= 9; ++i)
                        {
                            this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 1, 3, i, structureBoundingBoxIn);
                            this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 9, 3, i, structureBoundingBoxIn);
                        }

                        for (int j = 1; j <= 9; ++j)
                        {
                            this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), j, 3, 1, structureBoundingBoxIn);
                            this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), j, 3, 9, structureBoundingBoxIn);
                        }

                        this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 5, 1, 4, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 5, 1, 6, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 5, 3, 4, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 5, 3, 6, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 4, 1, 5, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 6, 1, 5, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 4, 3, 5, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 6, 3, 5, structureBoundingBoxIn);
                        for (int k = 1; k <= 3; ++k)
                        {
                            this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 4, k, 4, structureBoundingBoxIn);
                            this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 6, k, 4, structureBoundingBoxIn);
                            this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 4, k, 6, structureBoundingBoxIn);
                            this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 6, k, 6, structureBoundingBoxIn);
                        }
                        this.setBlockState(worldIn, Blocks.TORCH.getDefaultState(), 5, 3, 5, structureBoundingBoxIn);
                        for (int l = 2; l <= 8; ++l)
                        {
                            this.setBlockState(worldIn, Blocks.PLANKS.getDefaultState(), 2, 3, l, structureBoundingBoxIn);
                            this.setBlockState(worldIn, Blocks.PLANKS.getDefaultState(), 3, 3, l, structureBoundingBoxIn);
                            if (l <= 3 || l >= 7)
                            {
                                this.setBlockState(worldIn, Blocks.PLANKS.getDefaultState(), 4, 3, l, structureBoundingBoxIn);
                                this.setBlockState(worldIn, Blocks.PLANKS.getDefaultState(), 5, 3, l, structureBoundingBoxIn);
                                this.setBlockState(worldIn, Blocks.PLANKS.getDefaultState(), 6, 3, l, structureBoundingBoxIn);
                            }
                            this.setBlockState(worldIn, Blocks.PLANKS.getDefaultState(), 7, 3, l, structureBoundingBoxIn);
                            this.setBlockState(worldIn, Blocks.PLANKS.getDefaultState(), 8, 3, l, structureBoundingBoxIn);
                        }
                        IBlockState iblockstate = Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.WEST);
                        this.setBlockState(worldIn, iblockstate, 9, 1, 3, structureBoundingBoxIn);
                        this.setBlockState(worldIn, iblockstate, 9, 2, 3, structureBoundingBoxIn);
                        this.setBlockState(worldIn, iblockstate, 9, 3, 3, structureBoundingBoxIn);
                        this.generateChest(worldIn, structureBoundingBoxIn, randomIn, 3, 4, 8, LootTableList.CHESTS_STRONGHOLD_CROSSING);
                }
                return true;
            }
        }

        /**
         * (abstract) Helper method to write subclass data to NBT
         */
        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setInteger("Type", this.roomType);
        }

        /**
         * (abstract) Helper method to read subclass data from NBT
         */
        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            super.readStructureFromNBT(tagCompound, p_143011_2_);
            this.roomType = tagCompound.getInteger("Type");
        }
    }

    public static class Stairs extends StructureSafeStrongholdPieces.Stronghold
    {
        public static StructureSafeStrongholdPieces.Stairs createPiece(List<StructureComponent> p_175863_0_, Random p_175863_1_, int p_175863_2_, int p_175863_3_, int p_175863_4_, EnumFacing p_175863_5_, int p_175863_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175863_2_, p_175863_3_, p_175863_4_, -1, -7, 0, 5, 11, 5, p_175863_5_);
            return canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175863_0_, structureboundingbox) == null ? new StructureSafeStrongholdPieces.Stairs(p_175863_6_, p_175863_1_, structureboundingbox, p_175863_5_) : null;
        }

        private boolean source;

        public Stairs(int p_i2081_1_, Random p_i2081_2_, int p_i2081_3_, int p_i2081_4_)
        {
            super(p_i2081_1_);
            this.source = true;
            this.setCoordBaseMode(EnumFacing.Plane.HORIZONTAL.random(p_i2081_2_));
            this.entryDoor = StructureSafeStrongholdPieces.Stronghold.Door.OPENING;
            this.boundingBox = new StructureBoundingBox(p_i2081_3_, 64, p_i2081_4_, p_i2081_3_ + 5 - 1, 74, p_i2081_4_ + 5 - 1);
        }

        public Stairs(int p_i45574_1_, Random p_i45574_2_, StructureBoundingBox p_i45574_3_, EnumFacing p_i45574_4_)
        {
            super(p_i45574_1_);
            this.source = false;
            this.setCoordBaseMode(p_i45574_4_);
            this.entryDoor = this.getRandomDoor(p_i45574_2_);
            this.boundingBox = p_i45574_3_;
        }

        /**
         * Initiates construction of the Structure Component picked, at the current Location of StructGen
         */
        public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand)
        {
            if (this.source)
            {
                StructureSafeStrongholdPieces.strongComponentType = StructureSafeStrongholdPieces.Crossing.class;
            }
            this.getNextComponentNormal((StructureSafeStrongholdPieces.Stairs2) componentIn, listIn, rand, 1, 1);
        }

        /**
         * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
         * Mineshafts at the end, it adds Fences...
         */
        public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
        {
            if (this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
            {
                return false;
            }
            else
            {
                this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 10, 4, true, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
                this.placeDoor(worldIn, structureBoundingBoxIn, this.entryDoor, 1, 7, 0);
                this.placeDoor(worldIn, structureBoundingBoxIn, StructureSafeStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 4);
                this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 2, 6, 1, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 1, 5, 1, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.STONE_SLAB.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 6, 1, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 1, 5, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 1, 4, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.STONE_SLAB.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 5, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 2, 4, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 3, 3, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.STONE_SLAB.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 3, 4, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 3, 3, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 3, 2, 1, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.STONE_SLAB.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 3, 3, 1, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 2, 2, 1, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 1, 1, 1, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.STONE_SLAB.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 2, 1, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 1, 1, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.STONE_SLAB.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 1, 3, structureBoundingBoxIn);
                return true;
            }
        }

        /**
         * (abstract) Helper method to write subclass data to NBT
         */
        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setBoolean("Source", this.source);
        }

        /**
         * (abstract) Helper method to read subclass data from NBT
         */
        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            super.readStructureFromNBT(tagCompound, p_143011_2_);
            this.source = tagCompound.getBoolean("Source");
        }
    }

    public static class Stairs2 extends StructureSafeStrongholdPieces.Stairs
    {
        public StructureSafeStrongholdPieces.PieceWeight lastPlaced;
        public StructureSafeStrongholdPieces.PortalRoom strongholdPortalRoom;
        public List<StructureComponent> pendingChildren = Lists.newArrayList();

        public Stairs2(Random p_i2083_2_, int p_i2083_3_, int p_i2083_4_)
        {
            super(0, p_i2083_2_, p_i2083_3_, p_i2083_4_);
        }
    }

    public static class StairsStraight extends StructureSafeStrongholdPieces.Stronghold
    {
        public static StructureSafeStrongholdPieces.StairsStraight createPiece(List<StructureComponent> p_175861_0_, Random p_175861_1_, int p_175861_2_, int p_175861_3_, int p_175861_4_, EnumFacing p_175861_5_, int p_175861_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175861_2_, p_175861_3_, p_175861_4_, -1, -7, 0, 5, 11, 8, p_175861_5_);
            return canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175861_0_, structureboundingbox) == null ? new StructureSafeStrongholdPieces.StairsStraight(p_175861_6_, p_175861_1_, structureboundingbox, p_175861_5_) : null;
        }

        public StairsStraight(int p_i45572_1_, Random p_i45572_2_, StructureBoundingBox p_i45572_3_, EnumFacing p_i45572_4_)
        {
            super(p_i45572_1_);
            this.setCoordBaseMode(p_i45572_4_);
            this.entryDoor = this.getRandomDoor(p_i45572_2_);
            this.boundingBox = p_i45572_3_;
        }

        /**
         * Initiates construction of the Structure Component picked, at the current Location of StructGen
         */
        public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand)
        {
            this.getNextComponentNormal((StructureSafeStrongholdPieces.Stairs2) componentIn, listIn, rand, 1, 1);
        }

        /**
         * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
         * Mineshafts at the end, it adds Fences...
         */
        public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
        {
            if (this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
            {
                return false;
            }
            else
            {
                this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 10, 7, true, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
                this.placeDoor(worldIn, structureBoundingBoxIn, this.entryDoor, 1, 7, 0);
                this.placeDoor(worldIn, structureBoundingBoxIn, StructureSafeStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 7);
                IBlockState iblockstate = Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH);
                for (int i = 0; i < 6; ++i)
                {
                    this.setBlockState(worldIn, iblockstate, 1, 6 - i, 1 + i, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate, 2, 6 - i, 1 + i, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate, 3, 6 - i, 1 + i, structureBoundingBoxIn);
                    if (i < 5)
                    {
                        this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 1, 5 - i, 1 + i, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 2, 5 - i, 1 + i, structureBoundingBoxIn);
                        this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 3, 5 - i, 1 + i, structureBoundingBoxIn);
                    }
                }
                return true;
            }
        }
    }

    static class Stones extends SafeStructureComponent.BlockSelector
    {
        private Stones()
        {
        }

        /**
         * picks Block Ids and Metadata (Silverfish)
         */
        public void selectBlocks(Random rand, int x, int y, int z, boolean wall)
        {
            if (wall)
            {
                float f = rand.nextFloat();
                if (f < 0.2F)
                {
                    this.blockstate = Blocks.STONEBRICK.getStateFromMeta(BlockStoneBrick.CRACKED_META);
                }
                else if (f < 0.5F)
                {
                    this.blockstate = Blocks.STONEBRICK.getStateFromMeta(BlockStoneBrick.MOSSY_META);
                }
                else if (f < 0.55F)
                {
                    this.blockstate = Blocks.MONSTER_EGG.getStateFromMeta(BlockSilverfish.EnumType.STONEBRICK.getMetadata());
                }
                else
                {
                    this.blockstate = Blocks.STONEBRICK.getDefaultState();
                }
            }
            else
            {
                this.blockstate = Blocks.AIR.getDefaultState();
            }
        }
    }

    public static class Straight extends StructureSafeStrongholdPieces.Stronghold
    {
        public static StructureSafeStrongholdPieces.Straight createPiece(List<StructureComponent> p_175862_0_, Random p_175862_1_, int p_175862_2_, int p_175862_3_, int p_175862_4_, EnumFacing p_175862_5_, int p_175862_6_)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175862_2_, p_175862_3_, p_175862_4_, -1, -1, 0, 5, 5, 7, p_175862_5_);
            return canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175862_0_, structureboundingbox) == null ? new StructureSafeStrongholdPieces.Straight(p_175862_6_, p_175862_1_, structureboundingbox, p_175862_5_) : null;
        }

        private boolean expandsX;
        private boolean expandsZ;

        public Straight(int p_i45573_1_, Random p_i45573_2_, StructureBoundingBox p_i45573_3_, EnumFacing p_i45573_4_)
        {
            super(p_i45573_1_);
            this.setCoordBaseMode(p_i45573_4_);
            this.entryDoor = this.getRandomDoor(p_i45573_2_);
            this.boundingBox = p_i45573_3_;
            this.expandsX = p_i45573_2_.nextInt(2) == 0;
            this.expandsZ = p_i45573_2_.nextInt(2) == 0;
        }

        /**
         * Initiates construction of the Structure Component picked, at the current Location of StructGen
         */
        public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand)
        {
            this.getNextComponentNormal((StructureSafeStrongholdPieces.Stairs2) componentIn, listIn, rand, 1, 1);
            if (this.expandsX)
            {
                this.getNextComponentX((StructureSafeStrongholdPieces.Stairs2) componentIn, listIn, rand, 1, 2);
            }
            if (this.expandsZ)
            {
                this.getNextComponentZ((StructureSafeStrongholdPieces.Stairs2) componentIn, listIn, rand, 1, 2);
            }
        }

        /**
         * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
         * Mineshafts at the end, it adds Fences...
         */
        public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
        {
            if (this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
            {
                return false;
            }
            else
            {
                this.fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 4, 6, true, randomIn, StructureSafeStrongholdPieces.STRONGHOLD_STONES);
                this.placeDoor(worldIn, structureBoundingBoxIn, this.entryDoor, 1, 1, 0);
                this.placeDoor(worldIn, structureBoundingBoxIn, StructureSafeStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 6);
                IBlockState iblockstate = Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.EAST);
                IBlockState iblockstate1 = Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.WEST);
                this.randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 1, 2, 1, iblockstate);
                this.randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 3, 2, 1, iblockstate1);
                this.randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 1, 2, 5, iblockstate);
                this.randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 3, 2, 5, iblockstate1);
                if (this.expandsX)
                {
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 2, 0, 3, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                }

                if (this.expandsZ)
                {
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 2, 4, 3, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                }
                return true;
            }
        }

        /**
         * (abstract) Helper method to write subclass data to NBT
         */
        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setBoolean("Left", this.expandsX);
            tagCompound.setBoolean("Right", this.expandsZ);
        }

        /**
         * (abstract) Helper method to read subclass data from NBT
         */
        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            super.readStructureFromNBT(tagCompound, p_143011_2_);
            this.expandsX = tagCompound.getBoolean("Left");
            this.expandsZ = tagCompound.getBoolean("Right");
        }
    }

    public abstract static class Stronghold extends SafeStructureComponent
    {
        /**
         * returns false if the Structure Bounding Box goes below 10
         */
        protected static boolean canStrongholdGoDeeper(StructureBoundingBox p_74991_0_)
        {
            return p_74991_0_ != null && p_74991_0_.minY > 10;
        }

        protected StructureSafeStrongholdPieces.Stronghold.Door entryDoor = StructureSafeStrongholdPieces.Stronghold.Door.OPENING;

        public Stronghold()
        {
        }

        protected Stronghold(int p_i2087_1_)
        {
            super(p_i2087_1_);
        }

        /**
         * (abstract) Helper method to write subclass data to NBT
         */
        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            tagCompound.setString("EntryDoor", this.entryDoor.name());
        }

        /**
         * (abstract) Helper method to read subclass data from NBT
         */
        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            this.entryDoor = StructureSafeStrongholdPieces.Stronghold.Door.valueOf(tagCompound.getString("EntryDoor"));
        }

        /**
         * builds a door of the enumerated types (empty opening is a door)
         */
        protected void placeDoor(World worldIn, StructureBoundingBox p_74990_3_, StructureSafeStrongholdPieces.Stronghold.Door p_74990_4_, int p_74990_5_, int p_74990_6_, int p_74990_7_)
        {
            switch (p_74990_4_)
            {
                case OPENING:
                    this.fillWithBlocks(worldIn, p_74990_3_, p_74990_5_, p_74990_6_, p_74990_7_, p_74990_5_ + 3 - 1, p_74990_6_ + 3 - 1, p_74990_7_, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                    break;
                case WOOD_DOOR:
                    this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.OAK_DOOR.getDefaultState(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.OAK_DOOR.getDefaultState().withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    break;
                case GRATES:
                    this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
                    break;
                case IRON_DOOR:
                    this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.IRON_DOOR.getDefaultState(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.IRON_DOOR.getDefaultState().withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONE_BUTTON.getDefaultState().withProperty(BlockButton.FACING, EnumFacing.NORTH), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_ + 1, p_74990_3_);
                    this.setBlockState(worldIn, Blocks.STONE_BUTTON.getDefaultState().withProperty(BlockButton.FACING, EnumFacing.SOUTH), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_ - 1, p_74990_3_);
            }
        }

        protected StructureSafeStrongholdPieces.Stronghold.Door getRandomDoor(Random p_74988_1_)
        {
            int i = p_74988_1_.nextInt(5);
            switch (i)
            {
                case 0:
                case 1:
                default:
                    return StructureSafeStrongholdPieces.Stronghold.Door.OPENING;
                case 2:
                    return StructureSafeStrongholdPieces.Stronghold.Door.WOOD_DOOR;
                case 3:
                    return StructureSafeStrongholdPieces.Stronghold.Door.GRATES;
                case 4:
                    return StructureSafeStrongholdPieces.Stronghold.Door.IRON_DOOR;
            }
        }

        /**
         * Gets the next component in any cardinal direction
         */
        @Nullable
        protected StructureComponent getNextComponentNormal(StructureSafeStrongholdPieces.Stairs2 p_74986_1_, List<StructureComponent> p_74986_2_, Random p_74986_3_, int p_74986_4_, int p_74986_5_)
        {
            EnumFacing enumfacing = this.getCoordBaseMode();
            if (enumfacing != null)
            {
                switch (enumfacing)
                {
                    case NORTH:
                        return StructureSafeStrongholdPieces.generateAndAddPiece(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.minX + p_74986_4_, this.boundingBox.minY + p_74986_5_, this.boundingBox.minZ - 1, enumfacing, this.getComponentType());
                    case SOUTH:
                        return StructureSafeStrongholdPieces.generateAndAddPiece(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.minX + p_74986_4_, this.boundingBox.minY + p_74986_5_, this.boundingBox.maxZ + 1, enumfacing, this.getComponentType());
                    case WEST:
                        return StructureSafeStrongholdPieces.generateAndAddPiece(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74986_5_, this.boundingBox.minZ + p_74986_4_, enumfacing, this.getComponentType());
                    case EAST:
                        return StructureSafeStrongholdPieces.generateAndAddPiece(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74986_5_, this.boundingBox.minZ + p_74986_4_, enumfacing, this.getComponentType());
                }
            }
            return null;
        }

        /**
         * Gets the next component in the +/- X direction
         */
        @Nullable
        protected StructureComponent getNextComponentX(StructureSafeStrongholdPieces.Stairs2 p_74989_1_, List<StructureComponent> p_74989_2_, Random p_74989_3_, int p_74989_4_, int p_74989_5_)
        {
            EnumFacing enumfacing = this.getCoordBaseMode();
            if (enumfacing != null)
            {
                switch (enumfacing)
                {
                    case NORTH:
                    case SOUTH:
                        return StructureSafeStrongholdPieces.generateAndAddPiece(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ + p_74989_5_, EnumFacing.WEST, this.getComponentType());
                    case WEST:
                    case EAST:
                        return StructureSafeStrongholdPieces.generateAndAddPiece(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX + p_74989_5_, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
                }
            }
            return null;
        }

        /**
         * Gets the next component in the +/- Z direction
         */
        @Nullable
        protected StructureComponent getNextComponentZ(StructureSafeStrongholdPieces.Stairs2 p_74987_1_, List<StructureComponent> p_74987_2_, Random p_74987_3_, int p_74987_4_, int p_74987_5_)
        {
            EnumFacing enumfacing = this.getCoordBaseMode();
            if (enumfacing != null)
            {
                switch (enumfacing)
                {
                    case NORTH:
                    case SOUTH:
                        return StructureSafeStrongholdPieces.generateAndAddPiece(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74987_4_, this.boundingBox.minZ + p_74987_5_, EnumFacing.EAST, this.getComponentType());
                    case WEST:
                    case EAST:
                        return StructureSafeStrongholdPieces.generateAndAddPiece(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.minX + p_74987_5_, this.boundingBox.minY + p_74987_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
                }
            }
            return null;
        }

        public enum Door
        {
            OPENING, WOOD_DOOR, GRATES, IRON_DOOR
        }
    }
}