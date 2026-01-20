package mod.acgaming.universaltweaks.mods.bdsandm.mixin;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import funwayguy.bdsandm.blocks.IStorageBlock;
import funwayguy.bdsandm.network.PacketBdsm;

@Mixin(value = PacketBdsm.ServerHandler.class, remap = false, priority = Integer.MAX_VALUE)
public class UTServerHandlerMixin
{
    /**
     * @author bruberu
     * @reason Adds thread safety to the network handler, which is required for correctly spawning dropped items.
     * Without this, crashes occur on servers while using certain world generation/spawn control mods.
     */
    @WrapOperation(method = "onMessage(Lfunwayguy/bdsandm/network/PacketBdsm;Lnet/minecraftforge/fml/common/network/simpleimpl/MessageContext;)Lfunwayguy/bdsandm/network/PacketBdsm;",
        at = @At(target = "Lfunwayguy/bdsandm/blocks/IStorageBlock;onPlayerInteract(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/EnumFacing;Lnet/minecraft/entity/player/EntityPlayerMP;Lnet/minecraft/util/EnumHand;ZZI)V",
            value = "INVOKE"))
    public void threadSafeInteract(IStorageBlock instance, World world, BlockPos blockPos, IBlockState iBlockState,
                                   EnumFacing enumFacing, EntityPlayerMP entityPlayerMP, EnumHand enumHand,
                                   boolean isHit, boolean altMode, int delay, Operation<Void> original,
                                   PacketBdsm message, MessageContext ctx)
    {
        IThreadListener threadListener = FMLCommonHandler.instance().getWorldThread(ctx.netHandler);
        if (threadListener.isCallingFromMinecraftThread())
        {
            original.call(instance, world, blockPos, iBlockState, enumFacing, entityPlayerMP, enumHand, isHit, altMode,
                delay);
        }
        else
        {
            threadListener.addScheduledTask(() -> original.call(instance, world, blockPos, iBlockState, enumFacing,
                entityPlayerMP, enumHand, isHit, altMode, delay));
        }
    }
}