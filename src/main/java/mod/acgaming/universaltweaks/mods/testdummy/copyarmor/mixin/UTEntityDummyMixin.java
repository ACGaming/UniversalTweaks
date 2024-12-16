package mod.acgaming.universaltweaks.mods.testdummy.copyarmor.mixin;

import net.minecraft.item.ItemStack;

import boni.dummy.EntityDummy;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import mod.acgaming.universaltweaks.config.UTConfigMods;

// Courtesy of WaitingIdly
@Mixin(value = EntityDummy.class, remap = false)
public abstract class UTEntityDummyMixin
{
    /**
     * @author WaitingIdly
     */
    @WrapWithCondition(method = "equipArmor", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;shrink(I)V", remap = true))
    private boolean utDisableShrink(ItemStack instance, int quantity)
    {
        return !UTConfigMods.TEST_DUMMY.utCopyArmor;
    }

    /**
     * @author WaitingIdly
     */
    @WrapWithCondition(method = {"equipArmor", "removeArmor"}, at = @At(value = "INVOKE", target = "Lboni/dummy/EntityDummy;entityDropItem(Lnet/minecraft/item/ItemStack;F)Lnet/minecraft/entity/item/EntityItem;", remap = true))
    private boolean utDisableDrop(EntityDummy instance, ItemStack itemStack, float v)
    {
        return !UTConfigMods.TEST_DUMMY.utCopyArmor;
    }

    /**
     * @author WaitingIdly
     */
    @WrapWithCondition(method = {"dismantle"}, at = @At(value = "INVOKE", target = "Lboni/dummy/EntityDummy;dropEquipment(ZI)V", remap = true))
    private boolean utDisableDismantleEquipmentDrop(EntityDummy instance, boolean b, int i)
    {
        return !UTConfigMods.TEST_DUMMY.utCopyArmor;
    }

}
