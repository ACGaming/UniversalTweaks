package mod.acgaming.universaltweaks.tweaks.items.attackcooldown.mixin;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.text.translation.I18n;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class UTAttackCooldownTooltipMixin
{
    @Shadow
    public abstract Item getItem();

    @Inject(method = "getTooltip", at = @At("RETURN"))
    public void utItemStackTooltip(EntityPlayer playerIn, ITooltipFlag advanced, CallbackInfoReturnable<List<String>> cir)
    {
        if (!UTConfigTweaks.ITEMS.ATTACK_COOLDOWN.utAttackCooldownTooltips || (UTConfigTweaks.ITEMS.ATTACK_COOLDOWN.utAttackCooldownSwords && !(this.getItem() instanceof ItemSword))) return;
        cir.getReturnValue().removeIf(line -> line.contains(I18n.translateToLocal("attribute.name.generic.attackSpeed")));
    }
}