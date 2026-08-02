package mod.acgaming.universaltweaks.mods.immersivenegineering.crushersync.mixin;

import blusunrize.immersiveengineering.common.blocks.metal.TileEntityCrusher;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityMultiblockMetal;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.List;

@Mixin(
    value = TileEntityMultiblockMetal.class,
    remap = false
)
@SuppressWarnings("rawtypes")
public abstract class UTIECrusherSyncMixin
{
    /*
     Description-packet-only field.
     This is deliberately not written to world-save NBT. On load, the
     server recalculates it from the authoritative process state.
     */
    @Unique
    private static final String ut$RENDER_ACTIVE_NBT =
        "ut_ie_crusher_render_active";


    //Shared empty list used only while reading Crusher description packets.
    @Unique
    private static final NBTTagList ut$EMPTY_PROCESS_QUEUE =
        new NBTTagList();

    /*
     Conservative default for a not-yet-synchronized Crusher.
     Server: last synchronized active state.
     Client: active state received from the server.
     */
    @Unique
    private boolean ut$crusherRenderActive;

    @Unique
    private boolean ut$isCrusher()
    {return (Object) this instanceof TileEntityCrusher;}

    @Unique
    private boolean ut$isCrusherDescriptionPacket(boolean descPacket)
    {return descPacket && ut$isCrusher();}

     //Omit the Crusher process queue from description packets.
     //World-save NBT uses descPacket == false and remains unchanged.
    @ModifyExpressionValue(
        method =
            "writeCustomNBT" + "(Lnet/minecraft/nbt/NBTTagCompound;Z)V",
        at = @At(
            value = "FIELD",
            target = "Lblusunrize/immersiveengineering/common/blocks/"
                    + "metal/TileEntityMultiblockMetal;"
                    + "processQueue:Ljava/util/List;",
            opcode = Opcodes.GETFIELD,
            remap = false
        ),
        remap = false,
        require = 1
    )
    private List ut$omitCrusherQueueFromDescriptionPacket(
        List original,
        NBTTagCompound nbt,
        boolean descPacket
    )
    {
        if (ut$isCrusherDescriptionPacket(descPacket))
        {return Collections.emptyList();}
        return original;
    }

    //Prevent the client from reconstructing Crusher processes and recipes
    //from description-packet NBT.
    @ModifyExpressionValue(
        method =
            "readCustomNBT"
                + "(Lnet/minecraft/nbt/NBTTagCompound;Z)V",
        at = @At(
            value = "INVOKE",
            target =
                "Lnet/minecraft/nbt/NBTTagCompound;"
                    + "getTagList(Ljava/lang/String;I)"
                    + "Lnet/minecraft/nbt/NBTTagList;",
            remap = true
        ),
        remap = false,
        require = 1
    )
    private NBTTagList ut$ignoreCrusherQueueFromDescriptionPacket(
        NBTTagList original,
        NBTTagCompound nbt,
        boolean descPacket
    )
    {
        if (ut$isCrusherDescriptionPacket(descPacket))
        {return ut$EMPTY_PROCESS_QUEUE;}
        return original;
    }

    //Add the compact render-active state to Crusher description packets.
    @Inject(
        method =
            "writeCustomNBT"
                + "(Lnet/minecraft/nbt/NBTTagCompound;Z)V",
        at = @At("RETURN"),
        remap = false,
        require = 1
    )
    private void ut$writeCrusherRenderState(
        NBTTagCompound nbt,
        boolean descPacket,
        CallbackInfo ci
    )
    {
        if (ut$isCrusherDescriptionPacket(descPacket))
        {
            nbt.setBoolean(
                ut$RENDER_ACTIVE_NBT,
                ut$crusherRenderActive
            );
        }
    }


    //Receive the render-active state from Crusher description packets.
    @Inject(
        method =
            "readCustomNBT"
                + "(Lnet/minecraft/nbt/NBTTagCompound;Z)V",
        at = @At("RETURN"),
        remap = false,
        require = 1
    )
    private void ut$readCrusherRenderState(
        NBTTagCompound nbt,
        boolean descPacket,
        CallbackInfo ci
    )
    {
        if (ut$isCrusherDescriptionPacket(descPacket))
        {
            ut$crusherRenderActive =
                nbt.getBoolean(ut$RENDER_ACTIVE_NBT);
        }
    }

    //Synchronize changes in IE's original shouldRenderAsActive() result.
    @Inject(
        method = "update()V",
        at = @At("HEAD"),
        remap = true,
        require = 1
    )
    private void ut$syncCrusherRenderState(CallbackInfo ci)
    {
        if (!ut$isCrusher())
        {return;}

        TileEntityMultiblockMetal self =
            (TileEntityMultiblockMetal) (Object) this;

        World world = self.getWorld();
        if (world == null || world.isRemote || self.isDummy())
        {return;}

        boolean renderActive = self.shouldRenderAsActive();
        if (ut$crusherRenderActive == renderActive)
        {return;}
        ut$crusherRenderActive = renderActive;
        self.updateMasterBlock(null, true);
    }

    //The client queue is intentionally empty, so use the state synchronized
    //by the server instead of deriving rendering state from processQueue.
    @Inject(
        method = "shouldRenderAsActive()Z",
        at = @At("HEAD"),
        cancellable = true,
        remap = false,
        require = 1
    )
    private void ut$useSyncedCrusherRenderState(
        CallbackInfoReturnable<Boolean> cir
    )
    {
        if (!ut$isCrusher())
        {return;}
        TileEntityMultiblockMetal self =
            (TileEntityMultiblockMetal) (Object) this;

        World world = self.getWorld();
        if (world != null && world.isRemote)
        {cir.setReturnValue(ut$crusherRenderActive);}
    }
}