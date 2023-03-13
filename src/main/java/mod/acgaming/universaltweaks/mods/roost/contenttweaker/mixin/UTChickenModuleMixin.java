package mod.acgaming.universaltweaks.mods.roost.contenttweaker.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.setycz.chickens.handler.SpawnType;
import com.setycz.chickens.registry.ChickensRegistry;
import com.setycz.chickens.registry.ChickensRegistryItem;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.contenttweaker.ContentTweaker;
import com.teamacronymcoders.contenttweaker.modules.chickens.ChickenFactory;
import com.teamacronymcoders.contenttweaker.modules.chickens.ChickenModule;
import com.teamacronymcoders.contenttweaker.modules.chickens.ChickenRepresentation;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.minecraft.CraftTweakerMC;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of jchung01
@Mixin(value = ChickenModule.class, remap = false)
public abstract class UTChickenModuleMixin extends ModuleBase
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        if (!UTConfig.MOD_INTEGRATION.ROOST.utRoostEarlyRegisterCTChickens) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTChickenModuleMixin ::: Register chickens (pre-init)");
        utRegisterChickens();
    }

    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lcom/teamacronymcoders/contenttweaker/modules/chickens/ChickenFactory;registerChickens()V"), cancellable = true)
    public void utCTChickenInit(FMLInitializationEvent e, CallbackInfo ci)
    {
        if (UTConfig.MOD_INTEGRATION.ROOST.utRoostEarlyRegisterCTChickens)
        {
            if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTChickenModuleMixin ::: Finish setting up chickens (init)");
            utInitChickens();
            // ignore ChickenFactory.registerChickens() and use instance methods instead
            ci.cancel();
        }
    }

    // register barebone chickens to registry
    private void utRegisterChickens()
    {
        for (ChickenRepresentation chickenRepresentation : ChickenFactory.CHICKEN_REPRESENTATIONS)
        {
            ChickensRegistryItem item = new ChickensRegistryItem(
                new ResourceLocation(ContentTweaker.MOD_ID, chickenRepresentation.name),
                chickenRepresentation.name,
                chickenRepresentation.textureLocation.getInternal(),
                ItemStack.EMPTY,
                chickenRepresentation.backgroundColor.getIntColor(),
                chickenRepresentation.foregroundColor.getIntColor(),
                null,
                null
            );
            ChickensRegistry.register(item);
        }
        if (UTConfig.DEBUG.utDebugToggle)
        {
            CraftTweakerAPI.logInfo("UTChickenModuleMixin ::: Dumping chicken registry!");
            for (ChickensRegistryItem chicken : ChickensRegistry.getItems())
            {
                CraftTweakerAPI.logInfo("UTChickenModuleMixin ::: " + chicken.getRegistryName().toString());
            }
        }
    }

    // finish setting up chickens in init, after registries/resources are loaded
    private void utInitChickens()
    {
        if (UTConfig.DEBUG.utDebugToggle)
        {
            CraftTweakerAPI.logInfo("UTChickenModuleMixin ::: Dumping chicken registry! (in init)");
            for (ChickensRegistryItem chicken : ChickensRegistry.getItems())
            {
                CraftTweakerAPI.logInfo("UTChickenModuleMixin ::: " + chicken.getRegistryName().toString());
            }
        }
        for (ChickenRepresentation chickenRepresentation : ChickenFactory.CHICKEN_REPRESENTATIONS)
        {
            ResourceLocation registryName = new ResourceLocation(ContentTweaker.MOD_ID, chickenRepresentation.name);
            ChickensRegistryItem item = ChickensRegistry.getByRegistryName(registryName.toString());
            if (item == null)
            {
                CraftTweakerAPI.logError("Failed to find chicken item " + registryName + " in registry");
                return;
            }

            ChickensRegistryItem parentOneItem = chickenRepresentation.parentOne != null ?
                ChickensRegistry.getByResourceLocation(chickenRepresentation.parentOne.getInternal()) : null;
            ChickensRegistryItem parentTwoItem = chickenRepresentation.parentTwo != null ?
                ChickensRegistry.getByResourceLocation(chickenRepresentation.parentTwo.getInternal()) : null;
            if (parentOneItem != null && parentTwoItem != null)
            {
                item.setParentsNew(parentOneItem, parentTwoItem);
            }
            else
            {
                item.setNoParents();
            }

            if (chickenRepresentation.layItem != null)
            {
                item.setLayItem(CraftTweakerMC.getItemStack(chickenRepresentation.layItem));
            }

            if (chickenRepresentation.dropItem != null)
            {
                item.setDropItem(CraftTweakerMC.getItemStack(chickenRepresentation.dropItem));
            }

            if (chickenRepresentation.spawnType != null)
            {
                SpawnType actualSpawnType = null;
                for (SpawnType spawnTypeEnum : SpawnType.values())
                {
                    if (spawnTypeEnum.toString().equalsIgnoreCase(chickenRepresentation.spawnType))
                    {
                        actualSpawnType = spawnTypeEnum;
                    }
                }
                if (actualSpawnType != null)
                {
                    item.setSpawnType(actualSpawnType);
                }
                else
                {
                    CraftTweakerAPI.logError("Failed to find SpawnType for String: " + chickenRepresentation.spawnType);
                }
            }

            item.setLayCoefficient(chickenRepresentation.layCoefficient);
        }
    }
}