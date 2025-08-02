package mod.acgaming.universaltweaks.mods.agricraft.mixin;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.infinityraider.agricraft.compat.enderio.EnderIOPlugin;
import crazypants.enderio.api.farm.IFarmerJoe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = EnderIOPlugin.class, remap = false)
public class UTEnderIOPluginMixin
{
    /**
     * @author Invadermonky
     * @reason Fixes crash when Ender IO's Farming Station attempts to harvest Agricraft crops.
     *
     * <p>
     * The {@link com.infinityraider.agricraft.compat.enderio.AgriFarmerJoe} for some reason populates and returns
     * list of EntityItems to the Farming Station on harvest. This causes a crash when the Farming Station code
     * uses the getItem() method, and assumes it is retrieving an {@link net.minecraft.item.Item} when it is actually
     * retrieving an {@link net.minecraft.item.ItemStack}.
     * </p>
     *
     * <p>
     * This is just a fix for the crash, the re-implementation of the Farming Station integration is handled in
     * <a href="https://www.curseforge.com/minecraft/mc-mods/magiculture-integrations">Magiculture Integrations</a>.
     * </p>
     */
    @SubscribeEvent
    @Overwrite
    public void registerFarmer(RegistryEvent.Register<IFarmerJoe> event)
    {

    }
}
