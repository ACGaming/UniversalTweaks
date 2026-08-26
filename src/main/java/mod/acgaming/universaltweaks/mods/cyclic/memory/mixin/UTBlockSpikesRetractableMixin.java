package mod.acgaming.universaltweaks.mods.cyclic.memory.mixin;

import com.google.common.base.Preconditions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.lothrazar.cyclicmagic.block.BlockSpikesRetractable;
import mod.acgaming.universaltweaks.mods.cyclic.memory.UTFakePlayerContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = BlockSpikesRetractable.class)
public class UTBlockSpikesRetractableMixin
{
    @Definition(id = "causePlayerDamage", method = "Lnet/minecraft/util/DamageSource;causePlayerDamage(Lnet/minecraft/entity/player/EntityPlayer;)Lnet/minecraft/util/DamageSource;")
    @Expression("causePlayerDamage(@(?))")
    @ModifyExpressionValue(method = "onEntityCollision", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    private EntityPlayer utSetFakePlayerContext(EntityPlayer original, World worldIn, BlockPos pos)
    {
        Preconditions.checkArgument(worldIn instanceof WorldServer);

        if (original instanceof FakePlayer)
        {
            // Set context directly because fake player is transient
            UTFakePlayerContext.setContext((FakePlayer) original, (WorldServer) worldIn, pos);
        }
        return original;
    }
}
