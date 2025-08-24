package mod.acgaming.universaltweaks.mods.jurassicreborn.mixin;

import java.util.Random;

import net.minecraft.world.gen.structure.StructureVillagePieces;

import mod.acgaming.universaltweaks.config.UTConfigMods;
import mod.reborn.server.world.structure.GeneticistVillagerHouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GeneticistVillagerHouse.CreationHandler.class)
public class UTJRGeneticistVillagerHouseMixin
{
    @Inject(method = "getVillagePieceWeight", at = @At("RETURN"), cancellable = true, remap = false)
    public void utGetVillagePieceWeight(Random random, int size, CallbackInfoReturnable<StructureVillagePieces.PieceWeight> cir)
    {
        if (!UTConfigMods.JURASSIC_REBORN.utGeneticistHouseGenToggle)
        {
            cir.setReturnValue(new StructureVillagePieces.PieceWeight(GeneticistVillagerHouse.class, 0, 0));
        }
    }
}
