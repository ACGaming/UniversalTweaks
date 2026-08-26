package mod.acgaming.universaltweaks.mods.cyclic.memory.mixin;

import java.lang.ref.WeakReference;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.FakePlayer;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.lothrazar.cyclicmagic.block.miner.TileEntityBlockMiner;
import mod.acgaming.universaltweaks.mods.cyclic.memory.UTFakePlayerContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = TileEntityBlockMiner.class)
public class UTTileEntityBlockMinerMixin extends TileEntity
{
    @ModifyExpressionValue(method = "update", at = @At(value = "INVOKE", target = "Lcom/lothrazar/cyclicmagic/util/UtilFakePlayer;initFakePlayer(Lnet/minecraft/world/WorldServer;Ljava/util/UUID;Ljava/lang/String;)Ljava/lang/ref/WeakReference;", remap = false))
    private WeakReference<FakePlayer> utSetFakePlayerContext(WeakReference<FakePlayer> original)
    {
        return UTFakePlayerContext.wrap(original, getWorld(), getPos());
    }
}
