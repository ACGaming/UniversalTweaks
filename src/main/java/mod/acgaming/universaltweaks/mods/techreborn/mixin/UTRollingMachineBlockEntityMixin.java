package mod.acgaming.universaltweaks.mods.techreborn.mixin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import reborncore.api.IToolDrop;
import reborncore.api.tile.IInventoryProvider;
import reborncore.client.containerBuilder.IContainerProvider;
import reborncore.common.powerSystem.TilePowerAcceptor;
import reborncore.common.util.Inventory;
import reborncore.common.util.ItemUtils;
import techreborn.api.RollingMachineRecipe;
import techreborn.tiles.tier1.TileRollingMachine;

// Courtesy of aria1th (ported by jchung01)
@Mixin(value = TileRollingMachine.class, remap = false)
public abstract class UTRollingMachineBlockEntityMixin extends TilePowerAcceptor implements IToolDrop, IInventoryProvider, IContainerProvider
{
    @Shadow
    public static int energyPerTick;
    @Shadow
    public static int runTime;
    @Shadow
    public Inventory inventory;
    @Shadow
    public int tickTime;
    @Shadow
    public ItemStack currentRecipeOutput;
    @Shadow
    public IRecipe currentRecipe;
    @Shadow
    public boolean locked;
    @Shadow
    private int outputSlot;
    @Unique
    private List<Item> universalTweaks$cachedInventoryStructure = null;
    @Unique
    private IRecipe universalTweaks$lastRecipe = null;
    @Unique
    private boolean universalTweaks$forceRefresh = false;

    @Shadow
    public abstract void setIsActive(boolean active);

    @Shadow
    public abstract Optional<InventoryCrafting> balanceRecipe(InventoryCrafting craftCache);

    @Shadow
    public abstract boolean canMake(InventoryCrafting craftMatrix);

    @Shadow
    private InventoryCrafting getCraftingMatrix()
    {
        return null;
    }

    /**
     * Mimics getCraftingMatrix(boolean forceRefresh) in newer versions.
     * All calls to getCraftingMatrix() MUST set the field utForceRefresh before the call.
     */
    @ModifyExpressionValue(method = "getCraftingMatrix", at = @At(value = "FIELD", target = "Lreborncore/common/util/Inventory;hasChanged:Z", opcode = Opcodes.GETFIELD, ordinal = 0))
    private boolean utCheckForceRefresh(boolean original)
    {
        return UTConfigMods.TECH_REBORN.utOptimizeRollingMachineToggle ? universalTweaks$forceRefresh || original : original;
    }

    @Inject(method = "balanceRecipe", at = @At(value = "HEAD"), require = 1)
    private void utBalanceRecipe(CallbackInfoReturnable<Optional<InventoryCrafting>> cir)
    {
        if (!UTConfigMods.TECH_REBORN.utOptimizeRollingMachineToggle) return;
        universalTweaks$forceRefresh = false;
    }

    /**
     * Rewrittten version of update().
     * Based off of aria1th's 1.19 PR.
     * <p>
     * Remapping needed!
     */
    @Inject(method = "update", at = @At(value = "HEAD"), cancellable = true, remap = true)
    private void utOptimizedUpdate(CallbackInfo ci)
    {
        if (!UTConfigMods.TECH_REBORN.utOptimizeRollingMachineToggle) return;
        ci.cancel();

        super.update();
        if (world == null || world.isRemote)
        {
            return;
        }
        charge(10);

        // For utCheckForceRefresh
        universalTweaks$forceRefresh = true;
        InventoryCrafting craftMatrix = getCraftingMatrix();
        currentRecipe = utFindMatchingRecipe(craftMatrix, world);
        if (currentRecipe != null)
        {
            if (world.getTotalWorldTime() % 2 == 0)
            {
                balanceRecipe(craftMatrix);
            }
            currentRecipeOutput = currentRecipe.getCraftingResult(craftMatrix);
        }
        else
        {
            currentRecipeOutput = ItemStack.EMPTY;
        }
        // For utCheckForceRefresh
        universalTweaks$forceRefresh = false;
        craftMatrix = getCraftingMatrix();
        if (currentRecipeOutput.isEmpty() || !utCheckNotEmpty(craftMatrix))
        {
            // Can't make anyway, reject.
            tickTime = 0;
            setIsActive(false);
            return;
        }
        boolean overTicked = tickTime >= Math.max((int) (runTime * (1.0 - getSpeedMultiplier())), 1);
        // Now we ensured we can make something. Check energy state.
        if (canUseEnergy(getEuPerTick(energyPerTick)) && canMake(craftMatrix) && !overTicked)
        {
            setIsActive(true);
            useEnergy(getEuPerTick(energyPerTick));
            tickTime++;
        }
        else if (!overTicked)
        {
            setIsActive(false);
            return;
        }
        // Cached recipe or valid recipe exists.
        // check if we can make at least one.
        if (tickTime >= Math.max((int) (runTime * (1.0 - getSpeedMultiplier())), 1))
        {
            // Craft one
            if (inventory.getStackInSlot(outputSlot).isEmpty())
            {
                inventory.setInventorySlotContents(outputSlot, currentRecipeOutput.copy());
            }
            else
            {
                // We checked stack can fit in output slot in canMake()
                inventory.getStackInSlot(outputSlot).grow(currentRecipeOutput.getCount());
            }
            tickTime = 0;
            for (int i = 0; i < craftMatrix.getSizeInventory(); i++)
            {
                inventory.decrStackSize(i, 1);
            }
            if (!locked)
            {
                currentRecipeOutput = ItemStack.EMPTY;
                currentRecipe = null;
            }
        }
    }

    /**
     * Rewritten version of canMake(), accounting for utCheckNotEmpty() doing most of the check instead.
     * Based off of aria1th's 1.19 PR.
     * <p>
     * To implement with better mixin compatibility,
     * can use ModifyExpressionValue & ModifyReturnValue instead, but requires an extra inject for LocalCapture of 'stack'
     */
    @Inject(method = "canMake", at = @At(value = "HEAD"), cancellable = true)
    private void utCanMakeIgnoreLock(InventoryCrafting craftMatrix, CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfigMods.TECH_REBORN.utOptimizeRollingMachineToggle) return;

        ItemStack stack = utFindMatchingRecipeOutput(craftMatrix, world);
        if (stack.isEmpty())
        {
            cir.setReturnValue(false);
            return;
        }
        ItemStack output = inventory.getStackInSlot(outputSlot);
        if (output.isEmpty())
        {
            cir.setReturnValue(true);
            return;
        }
        cir.setReturnValue(ItemUtils.isItemEqual(stack, output, true, true) && output.getCount() + stack.getCount() <= output.getMaxStackSize());
    }

    /**
     * Because targeting a lambda function for mixins is hard,
     * rewrites lambda function of onCraft() to redirect findMatchingRecipeOutput() call.
     */
    @ModifyArg(method = "createContainer", at = @At(value = "INVOKE", target = "Lreborncore/client/containerBuilder/builder/ContainerTileInventoryBuilder;onCraft(Ljava/util/function/Consumer;)Lreborncore/client/containerBuilder/builder/ContainerTileInventoryBuilder;"))
    private Consumer<InventoryCrafting> utModifyOnCraftCall(Consumer<InventoryCrafting> onCraft)
    {
        if (!UTConfigMods.TECH_REBORN.utOptimizeRollingMachineToggle) return onCraft;
        // For utCheckForceRefresh
        universalTweaks$forceRefresh = false;
        return (inv) -> this.inventory.setInventorySlotContents(1, utFindMatchingRecipeOutput(this.getCraftingMatrix(), this.world));
    }

    @Unique
    private ItemStack utFindMatchingRecipeOutput(InventoryCrafting inv, World world)
    {
        // Don't need to call RollingMachineRecipe.instance.findMatchingRecipeOutput(), just use utFindMatchingRecipe()
        IRecipe recipe = utFindMatchingRecipe(inv, world);
        if (recipe == null) return ItemStack.EMPTY;
        return recipe.getCraftingResult(inv);
    }

    /**
     * Wrapper for RollingMachineRecipe.instance.findMatchingRecipe() to use caching
     */
    @Unique
    private IRecipe utFindMatchingRecipe(InventoryCrafting inv, World world)
    {
        if (utIsCorrectCachedInventory())
        {
            return universalTweaks$lastRecipe;
        }
        universalTweaks$cachedInventoryStructure = utFastInvLayout();
        IRecipe foundRecipe = RollingMachineRecipe.instance.findMatchingRecipe(inv, world);
        if (foundRecipe != null)
        {
            universalTweaks$lastRecipe = foundRecipe;
            return foundRecipe;
        }
        universalTweaks$lastRecipe = null;
        return null;
    }

    /**
     * @return true if the inventory matches the cached inventory;
     * false otherwise.
     */
    @Unique
    private boolean utIsCorrectCachedInventory()
    {
        if (universalTweaks$cachedInventoryStructure == null)
        {
            return false;
        }
        List<Item> current = utFastInvLayout();
        if (current == null || current.size() != this.universalTweaks$cachedInventoryStructure.size())
        {
            return false;
        }
        for (int i = 0; i < current.size(); i++)
        {
            if (current.get(i) != this.universalTweaks$cachedInventoryStructure.get(i))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * @return List of the tile inventory's Items
     */
    @Unique
    private List<Item> utFastInvLayout()
    {
        if (this.inventory == null) return null;
        ArrayList<Item> arrayList = new ArrayList<>(9);
        for (int i = 0; i < 9; i++)
        {
            arrayList.add(this.inventory.getStackInSlot(i).getItem());
        }
        return arrayList;
    }

    /**
     * @param craftMatrix the inventory's crafting matrix
     * @return true if the inventory is NOT empty or considered quasi-empty;
     * false otherwise.
     */
    @Unique
    private boolean utCheckNotEmpty(InventoryCrafting craftMatrix)
    {
        // Checks if inventory is empty or considered quasi-empty.
        if (locked)
        {
            boolean returnValue = false;
            // For locked condition, we need to check if inventory contains item and all slots are empty or has more than one item.
            for (int i = 0; i < craftMatrix.getSizeInventory(); i++)
            {
                ItemStack stack1 = craftMatrix.getStackInSlot(i);
                if (stack1.getCount() == 1)
                {
                    return false;
                }
                if (stack1.getCount() > 1)
                {
                    returnValue = true;
                }
            }
            return returnValue;
        }
        else
        {
            for (int i = 0; i < craftMatrix.getSizeInventory(); i++)
            {
                ItemStack stack1 = craftMatrix.getStackInSlot(i);
                if (!stack1.isEmpty())
                {
                    return true;
                }
            }
        }
        return false;
    }
}