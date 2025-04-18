package mod.acgaming.universaltweaks.tweaks.entities.trading.mixin;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import mod.acgaming.universaltweaks.tweaks.entities.trading.UTVillagerProfessionBlacklist;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityVillager.class)
public abstract class UTEntityVillagerMixin extends EntityAgeable
{
    @Shadow(remap = false)
    private VillagerRegistry.VillagerProfession prof;

    protected UTEntityVillagerMixin(World world)
    {
        super(world);
    }

    @Shadow
    public abstract void setProfession(int professionId);

    @Inject(method = "setProfession(Lnet/minecraftforge/fml/common/registry/VillagerRegistry$VillagerProfession;)V", at = @At(value = "HEAD"), cancellable = true, remap = false)
    private void utSetProfession(VillagerRegistry.VillagerProfession prof, CallbackInfo ci)
    {
        VillagerRegistry.VillagerProfession selectedProfession = ut$getValidProfession((EntityVillager) (Object) this, this.getRNG());
        this.prof = selectedProfession;
        this.setProfession(VillagerRegistry.getId(selectedProfession));
        ci.cancel();
    }

    /**
     * Given a villager and its random number generator, returns a valid {@link VillagerRegistry.VillagerProfession} for the villager's current biome.
     * "Valid" meaning the profession is not blacklisted in the current biome according to the configuration.
     *
     * @param villager the villager to choose a profession for
     * @param random   the villager's random number generator
     * @return a valid profession for the villager
     */
    @Unique
    private VillagerRegistry.VillagerProfession ut$getValidProfession(EntityLivingBase villager, Random random)
    {
        // Get the biome at the villager's position
        BlockPos pos = new BlockPos(villager);
        Biome biome = villager.world.getBiome(pos);
        String biomeRegName = biome.getRegistryName().toString();

        // Get all registered professions
        List<VillagerRegistry.VillagerProfession> professions = new ArrayList<>(ForgeRegistries.VILLAGER_PROFESSIONS.getValuesCollection());

        // Get blacklisted professions for the current biome from config, default to empty array if null
        List<String> blacklistedProfessions = Arrays.asList(UTVillagerProfessionBlacklist.VILLAGER_PROFESSION_BLACKLIST.getOrDefault(biomeRegName, new String[0]));

        // Remove blacklisted professions
        professions.removeIf(profession -> {
            ResourceLocation profName = profession.getRegistryName();
            if (profName == null) return false;
            return blacklistedProfessions.contains(profName.toString());
        });

        // If no professions remain, fall back to nitwit
        if (professions.isEmpty())
        {
            return ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:nitwit"));
        }

        // Select a random profession from the remaining pool
        return professions.get(random.nextInt(professions.size()));
    }
}
