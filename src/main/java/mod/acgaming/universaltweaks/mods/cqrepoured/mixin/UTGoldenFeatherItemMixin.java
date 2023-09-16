package mod.acgaming.universaltweaks.mods.cqrepoured.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.cqr.cqrepoured.item.ItemGoldenFeather;
import team.cqr.cqrepoured.item.ItemLore;

@Mixin(ItemGoldenFeather.class)
public class UTGoldenFeatherItemMixin extends ItemLore
{
    @Inject(method = "onUpdate", at = @At("HEAD"), cancellable = true)
    public void utGoldenFeatherItem(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected, CallbackInfo ci)
    {
        if (!UTConfig.MOD_INTEGRATION.CHOCOLATE_QUEST.utCQRGoldenFeatherToggle) return;
        if (entity.fallDistance >= 3) world.spawnParticle(EnumParticleTypes.CLOUD, entity.posX, entity.posY, entity.posZ, (itemRand.nextFloat() - 0.5) / 2, -0.5, (itemRand.nextFloat() - 0.5) / 2);
        super.onUpdate(stack, world, entity, itemSlot, isSelected);
        ci.cancel();
    }
}