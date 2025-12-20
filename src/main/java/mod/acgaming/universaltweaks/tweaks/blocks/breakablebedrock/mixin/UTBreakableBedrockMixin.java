package mod.acgaming.universaltweaks.tweaks.blocks.breakablebedrock.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Block.class)
public class UTBreakableBedrockMixin
{
    @WrapOperation(method = "registerBlocks", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;registerBlock(ILjava/lang/String;Lnet/minecraft/block/Block;)V", ordinal = 6))
    private static void utBreakableBedrock(int id, String textualID, Block block_, Operation<Void> original)
    {
        original.call(id, textualID, new Block(Material.ROCK).setHardness(200.0F).setResistance(6000000.0F).setTranslationKey(textualID).setCreativeTab(CreativeTabs.BUILDING_BLOCKS));
    }
}