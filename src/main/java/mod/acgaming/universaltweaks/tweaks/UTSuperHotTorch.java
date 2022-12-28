package mod.acgaming.universaltweaks.tweaks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTSuperHotTorch
{
    @SubscribeEvent
    public static void utSuperHotTorch(AttackEntityEvent event)
    {
        if (!UTConfig.tweaks.utSuperHotTorchToggle) return;
        if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTSuperHotTorch ::: Attack entity event");
        Entity target = event.getTarget();
        EntityPlayer player = event.getEntityPlayer();
        ItemStack stackMainhand = player.getHeldItemMainhand();
        if (target.isBurning() || !(Block.getBlockFromItem(stackMainhand.getItem()) instanceof BlockTorch)) return;
        if (!player.isCreative()) player.inventory.decrStackSize(player.inventory.currentItem, 1);
        target.setFire(5);
        World world = player.getEntityWorld();
        world.playSound(player, target.getPosition(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1, world.rand.nextFloat() * 0.4F + 0.8F);
        for (int i = 0; i < 3; i++)
        {
            double x = target.posX + world.rand.nextDouble();
            double y = target.posY + world.rand.nextDouble();
            double z = target.posZ + world.rand.nextDouble();
            world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, x, y, z, 0, 0, 0);
        }
    }
}