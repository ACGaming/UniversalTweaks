package mod.acgaming.universaltweaks.bugfixes.blocks.miningglitch.mixin;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;
import mod.acgaming.universaltweaks.bugfixes.blocks.miningglitch.IEntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// MC-118710
// https://bugs.mojang.com/browse/MC-118710
// Courtesy of mrgrim
@Mixin(EntityPlayerSP.class)
public abstract class UTPlayerWalkingMixin extends AbstractClientPlayer implements IEntityPlayerSP
{
    public UTPlayerWalkingMixin(World worldIn, GameProfile playerProfile)
    {
        super(worldIn, playerProfile);
    }

    public void updateWalkingPlayer()
    {
        this.onUpdateWalkingPlayer();
    }

    @Shadow
    private void onUpdateWalkingPlayer() {}
}