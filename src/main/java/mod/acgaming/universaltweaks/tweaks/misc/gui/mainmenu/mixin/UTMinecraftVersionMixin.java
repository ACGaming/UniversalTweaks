package mod.acgaming.universaltweaks.tweaks.misc.gui.mainmenu.mixin;

import java.util.Random;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = FMLCommonHandler.class, remap = false)
public abstract class UTMinecraftVersionMixin
{
    @Unique
    private final Random ut$rand = new Random();

    @Redirect(method = "computeBranding", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/common/Loader;getMCVersionString()Ljava/lang/String;"))
    public String utMinecraftVersion(Loader instance)
    {
        return "Minecraft 1." + ut$rand.nextInt(22) + "." + ut$rand.nextInt(12);
    }
}
