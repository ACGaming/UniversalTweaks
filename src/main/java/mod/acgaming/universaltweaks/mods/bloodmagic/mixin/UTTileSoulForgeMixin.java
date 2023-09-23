package mod.acgaming.universaltweaks.mods.bloodmagic.mixin;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;

import WayofTime.bloodmagic.api.impl.BloodMagicAPI;
import WayofTime.bloodmagic.api.impl.BloodMagicRecipeRegistrar;
import WayofTime.bloodmagic.api.impl.recipe.RecipeTartaricForge;
import WayofTime.bloodmagic.soul.IDemonWillConduit;
import WayofTime.bloodmagic.tile.TileInventory;
import WayofTime.bloodmagic.tile.TileSoulForge;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// Courtesy of jchung01
@Mixin(value = TileSoulForge.class, remap = false)
public abstract class UTTileSoulForgeMixin extends TileInventory implements ITickable, IDemonWillConduit
{
    private boolean isAdded = false;
    private RecipeTartaricForge utLastRecipe = null;

    // Dummy constructor
    public UTTileSoulForgeMixin(int size, String name)
    {
        super(size, name);
    }

    @Override
    public void markDirty()
    {
        super.markDirty();
        if (!UTConfigMods.BLOOD_MAGIC.utBMOptimizeSoulForgeToggle) return;
        utRefreshRecipe();
    }

    public void utRefreshRecipe()
    {
        List<ItemStack> inputList = new ArrayList<>();
        for (int i = 0; i < 4; i++)
        {
            if (!getStackInSlot(i).isEmpty())
            {
                inputList.add(getStackInSlot(i));
            }
        }
        utLastRecipe = BloodMagicAPI.INSTANCE.getRecipeRegistrar().getTartaricForge(inputList);
    }

    /**
     * Use cached recipe in update().
     * Uses Redirect because getTartaricForge() must not be called unless necessary.
     * <p>
     * Remapping needed!
     */
    @Redirect(method = "update", at = @At(value = "INVOKE", target = "LWayofTime/bloodmagic/api/impl/BloodMagicRecipeRegistrar;getTartaricForge(Ljava/util/List;)LWayofTime/bloodmagic/api/impl/recipe/RecipeTartaricForge;"), remap = true)
    private RecipeTartaricForge utUseCachedRecipe(BloodMagicRecipeRegistrar registrar, List<ItemStack> input)
    {
        if (!UTConfigMods.BLOOD_MAGIC.utBMOptimizeSoulForgeToggle) return registrar.getTartaricForge(input);
        if (!isAdded)
        {
            utLastRecipe = registrar.getTartaricForge(input);
            isAdded = true;
        }
        return utLastRecipe;
    }
}
