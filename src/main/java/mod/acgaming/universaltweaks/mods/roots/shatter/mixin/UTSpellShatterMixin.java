package mod.acgaming.universaltweaks.mods.roots.shatter.mixin;

import java.util.List;
import java.util.function.Function;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import epicsquid.roots.modifiers.instance.staff.StaffModifierInstanceList;
import epicsquid.roots.spell.SpellNaturesScythe;
import epicsquid.roots.spell.SpellShatter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(value = SpellShatter.class, remap = false)
public abstract class UTSpellShatterMixin
{
    /**
     * @author WaitingIdly
     * @reason Ensure that the block being targeted is actually breakable.
     */
    @WrapWithCondition(method = "cast", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;destroyBlock(Lnet/minecraft/util/math/BlockPos;Z)Z", remap = true))
    private boolean utEnsureBreakable(World instance, BlockPos pos, boolean dropBlock)
    {
        return instance.getBlockState(pos).getBlockHardness(instance, pos) >= 0.0f;
    }

    /**
     * @author WaitingIdly
     * @reason Ensure that the block being targeted is actually breakable.
     */
    @WrapWithCondition(method = "cast", at = @At(value = "INVOKE", target = "Lepicsquid/roots/spell/SpellNaturesScythe;breakBlock(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lepicsquid/roots/modifiers/instance/staff/StaffModifierInstanceList;Lnet/minecraft/entity/player/EntityPlayer;Ljava/util/List;)V"))
    private boolean utEnsureBreakable(SpellNaturesScythe instance, World world, BlockPos pos, StaffModifierInstanceList info, EntityPlayer player, List<Function<ItemStack, ItemStack>> converters)
    {
        return world.getBlockState(pos).getBlockHardness(world, pos) >= 0.0f;
    }
}
