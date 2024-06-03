package mod.acgaming.universaltweaks.bugfixes.entities.dimensionchange.mixin;

import com.mojang.authlib.GameProfile;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketPlayerAbilities;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// MC-124177
// https://bugs.mojang.com/browse/MC-124177
// Courtesy of FxMorin
@Mixin(EntityPlayerMP.class)
public abstract class UTDimensionChangeMixin extends EntityPlayer
{
    @Shadow public NetHandlerPlayServer connection;

    @Shadow private int lastExperience;

    @Shadow private float lastHealth;

    @Shadow private int lastFoodLevel;

    protected UTDimensionChangeMixin(World worldIn, GameProfile gameProfileIn)
    {
        super(worldIn, gameProfileIn);
    }

    @Inject(method = "changeDimension", at = @At(value = "TAIL"), remap = false)
    public void utChangeDimension(int dimensionIn, ITeleporter teleporter, CallbackInfoReturnable<Entity> cir)
    {
        if (!UTConfigBugfixes.ENTITIES.utDimensionChangeToggle) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTDimensionChange ::: Change dimension");
        this.connection.sendPacket(new SPacketPlayerAbilities(this.capabilities));
        for (PotionEffect effect : this.getActivePotionEffects()) this.connection.sendPacket(new SPacketEntityEffect(this.getEntityId(), effect));
        this.lastExperience = -1;
        this.lastHealth = -1.0F;
        this.lastFoodLevel = -1;
    }
}