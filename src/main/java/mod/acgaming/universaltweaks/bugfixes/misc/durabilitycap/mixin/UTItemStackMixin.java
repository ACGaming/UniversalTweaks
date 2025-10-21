package mod.acgaming.universaltweaks.bugfixes.misc.durabilitycap.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;

@Mixin(ItemStack.class)
public abstract class UTItemStackMixin {

	@Shadow
	private int itemDamage;

	@Inject(method = "writeToNBT(Lnet/minecraft/nbt/NBTTagCompound;)Lnet/minecraft/nbt/NBTTagCompound;", at = @At("RETURN"))
	private void utWriteToNBT(NBTTagCompound compound, CallbackInfoReturnable<NBTTagCompound> ci) {
		if (itemDamage > Short.MAX_VALUE) //If storing with a short doesnt work we should store in an int
			compound.setInteger("Damage", itemDamage);
	}
	
	@Redirect(method = "<init>(Lnet/minecraft/nbt/NBTTagCompound;)V", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(II)I"))
	private int ut__init__(int zero, int shortDamage, @Local NBTTagCompound compound) {		
		if (compound.hasKey("Damage", NBT.TAG_INT)) //if we stored an int we should use an int
			return Math.max(zero, compound.getInteger("Damage"));
		return Math.max(zero, shortDamage); //otherwise just go with regular thingy
	}

	@ModifyConstant(method = "isEmpty()Z", constant = @Constant(intValue = 65535))
	private int utIsEmpty(int value) {		
		return Integer.MAX_VALUE - 1; //yeah. mc also has a 65535 durability check? its clearly not needed here.
	}
}
