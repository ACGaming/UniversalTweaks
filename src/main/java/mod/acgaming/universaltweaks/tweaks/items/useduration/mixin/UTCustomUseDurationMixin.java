package mod.acgaming.universaltweaks.tweaks.items.useduration.mixin;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import mod.acgaming.universaltweaks.tweaks.items.useduration.UTCustomUseDuration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public class UTCustomUseDurationMixin
{
    @Unique
    public int used = 0;

    @Inject(method = "onUsingTick", at = @At("HEAD"), cancellable = true, remap = false)
    public void utCustomUseDurationTick(ItemStack stack, EntityLivingBase entity, int count, CallbackInfo ci)
    {
        used++;
        if (used >= getCustomDuration(stack))
        {
            used = 0;
            entity.stopActiveHand();
            if (entity instanceof EntityPlayer) ((EntityPlayer) entity).getCooldownTracker().setCooldown(stack.getItem(), getCustomCooldown(stack));
            ci.cancel();
        }
    }

    @Unique
    public int getCustomDuration(ItemStack stack)
    {
        int meta = 0;
        try
        {
            if (stack.getHasSubtypes()) meta = stack.getMetadata();
        }
        catch (Exception ignored) {}
        return UTCustomUseDuration.itemUseDurationMap.get(stack.getItem().toString() + meta);
    }

    @Unique
    public int getCustomCooldown(ItemStack stack)
    {
        int meta = 0;
        try
        {
            if (stack.getHasSubtypes()) meta = stack.getMetadata();
        }
        catch (Exception ignored) {}
        return UTCustomUseDuration.itemUseCooldownMap.get(stack.getItem().toString() + meta);
    }
}