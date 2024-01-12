package mod.acgaming.universaltweaks.tweaks.blocks.overhaulbeacon.mixin;

import java.util.Arrays;
import java.util.Objects;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.math.AxisAlignedBB;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(TileEntityBeacon.class)
public class UTOverhaulBeaconMixin
{
    @Unique
    private Block[] utOverhaulBeacon$blockLevels;
    @Unique
    private Object2ObjectMap<Block, Int2IntMap> utOverhaulBeacon$blockCounts;
    @Unique
    private Object2IntMap<Block> utOverhaulBeacon$blockHolder;

    @WrapOperation(
        method = "addEffectsToPlayers",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/util/math/AxisAlignedBB;grow(D)Lnet/minecraft/util/math/AxisAlignedBB;"
        )
    )
    private AxisAlignedBB utOverhaulBeacon$addEffectsToPlayers$grow(AxisAlignedBB instance, double value, Operation<AxisAlignedBB> original)
    {
        return original.call(instance, getBeaconRange(utOverhaulBeacon$blockCounts, value));
    }

    @Inject(
        method = "updateSegmentColors",
        at = @At(
            value = "HEAD"
        )
    )
    private void utOverhaulBeacon$clearBlocksMap(CallbackInfo ci)
    {
        if (UTConfigTweaks.BLOCKS.OVERHAUL_BEACON.utOverhaulBeaconMode.isEnforce)
        {
            if (this.utOverhaulBeacon$blockLevels == null)
            {
                this.utOverhaulBeacon$blockLevels = new Block[4];
            }
            else
            {
                Arrays.fill(this.utOverhaulBeacon$blockLevels, null);
            }
        }
        if (UTConfigTweaks.BLOCKS.OVERHAUL_BEACON.utOverhaulBeaconMode.isModifier)
        {
            if (this.utOverhaulBeacon$blockCounts == null)
            {
                this.utOverhaulBeacon$blockCounts = new Object2ObjectArrayMap<>();
                this.utOverhaulBeacon$blockHolder = new Object2IntArrayMap<>();

            }
            else
            {
                this.utOverhaulBeacon$blockCounts.clear();
            }
        }
    }

    @ModifyExpressionValue(
        method = "updateSegmentColors",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/Block;isBeaconBase(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Z"
        )
    )
    private boolean utOverhaulBeacon$collectBlocksOnLevel(boolean original, @Local Block block, @Local(index = 8) int level)
    {
        if (!original) return false;

        boolean isAllowedBlock = true;

        if (UTConfigTweaks.BLOCKS.OVERHAUL_BEACON.utOverhaulBeaconMode.isModifier)
        {
            this.utOverhaulBeacon$blockHolder.compute(block, (k, v) -> (v == null) ? 1 : v + 1);
        }

        if (UTConfigTweaks.BLOCKS.OVERHAUL_BEACON.utOverhaulBeaconMode.isEnforce)
        {
            if (this.utOverhaulBeacon$blockLevels[level - 1] == null)
            {
                this.utOverhaulBeacon$blockLevels[level - 1] = block;
            }
            isAllowedBlock = Objects.equals(this.utOverhaulBeacon$blockLevels[level - 1], block);
        }

        return isAllowedBlock;
    }

    @Inject(
        method = "updateSegmentColors",
        at = @At(
            value = "JUMP",
            opcode = Opcodes.IFNE,
            shift = At.Shift.BEFORE,
            ordinal = 2
        ),
        locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void utOverhaulBeacon$shouldAddLevel(CallbackInfo ci, @Local(ordinal = 1) boolean isLevelValid, @Local(index = 8) int level)
    {
        if (UTConfigTweaks.BLOCKS.OVERHAUL_BEACON.utOverhaulBeaconMode.isModifier)
        {
            if (isLevelValid)
            {
                this.utOverhaulBeacon$blockHolder.forEach((block, count) ->
                    this.utOverhaulBeacon$blockCounts.compute(block, (blockK, map) ->
                    {
                        if (map == null)
                        {
                            map = new Int2IntArrayMap();
                        }
                        map.compute(level - 1, (levelK, countK) -> (countK == null) ? count : countK + count);
                        return map;
                    }));
            }
            this.utOverhaulBeacon$blockHolder.clear();
        }
    }

    @Unique
    private double getBeaconRange(Object2ObjectMap<Block, Int2IntMap> map, double defaultRange)
    {
        return !UTConfigTweaks.BLOCKS.OVERHAUL_BEACON.utOverhaulBeaconMode.isModifier ? defaultRange :
            map.object2ObjectEntrySet().stream()
                .mapToDouble(
                    blockEntry -> UTConfigTweaks.BLOCKS.OVERHAUL_BEACON.utOverhaulBeaconBlocksModifier
                        .getOrDefault(Block.REGISTRY.getNameForObject(blockEntry.getKey()).toString(), UTConfigTweaks.BLOCKS.OVERHAUL_BEACON.utOverhaulBeaconGlobalModifier)
                        * blockEntry.getValue().entrySet().stream()
                        .mapToDouble(countEntry -> UTConfigTweaks.BLOCKS.OVERHAUL_BEACON.utOverhaulBeaconLevelScale[countEntry.getKey()]
                            * countEntry.getValue()).sum())
                .sum();
    }
}