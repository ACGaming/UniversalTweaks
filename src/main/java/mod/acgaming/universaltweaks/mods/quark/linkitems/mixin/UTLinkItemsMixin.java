package mod.acgaming.universaltweaks.mods.quark.linkitems.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.event.HoverEvent;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import vazkii.quark.management.feature.LinkItems;

@Mixin(value = LinkItems.class, remap = false)
public class UTLinkItemsMixin
{
    /**
     * This code is just a glommed together combination of two ItemStack methods, with the instances
     * where {@link TextComponentString} was called inside of {@link ItemStack#getDisplayName()} replaced
     * with {@link TextComponentTranslation}.
     * <p>
     * This cannot simply call {@link ItemStack#getDisplayName()} because one of the possible outputs is a raw String.
     *
     * @see ItemStack#getTextComponent()
     * @see ItemStack#getDisplayName()
     */
    @Unique
    private static ITextComponent universalTweaks$getTextComponent(ItemStack stack)
    {
        ITextComponent stackName = null;
        NBTTagCompound display = stack.getSubCompound("display");

        if (display != null)
        {
            if (display.hasKey("Name", 8))
            {
                stackName = new TextComponentString(display.getString("Name"));
            }

            if (display.hasKey("LocName", 8))
            {
                stackName = new TextComponentTranslation(display.getString("LocName"));
            }
        }
        if (stackName == null) stackName = new TextComponentTranslation(stack.getItem().getItemStackDisplayName(stack));

        if (stack.hasDisplayName())
        {
            stackName.getStyle().setItalic(Boolean.TRUE);
        }

        ITextComponent component = (new TextComponentString("[")).appendSibling(stackName).appendText("]");

        if (!stack.isEmpty())
        {
            NBTTagCompound tag = stack.writeToNBT(new NBTTagCompound());
            component.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new TextComponentString(tag.toString())));
            component.getStyle().setColor(stack.getItem().getForgeRarity(stack).getColor());
        }

        return component;
    }

    /**
     * @reason Since this can run on the server, if it is run on the server send a {@link TextComponentTranslation} for the name
     * instead of a {@link TextComponentString} of the untranslated name.
     * @author WaitingIdly
     */
    @WrapOperation(method = "linkItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getTextComponent()Lnet/minecraft/util/text/ITextComponent;"))
    private static ITextComponent useItemStackTranslation(ItemStack instance, Operation<ITextComponent> original, @Local(argsOnly = true) EntityPlayer player)
    {
        if (UTConfigMods.QUARK.utLinkItemsServer && player.isServerWorld()) return universalTweaks$getTextComponent(instance);
        return original.call(instance);
    }

}
