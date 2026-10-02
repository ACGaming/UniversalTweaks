package mod.acgaming.universaltweaks.mods.biomesoplenty.biometweaker.mixin;

import java.util.Map;

import me.superckl.api.biometweaker.property.BiomePropertyManager;
import me.superckl.api.biometweaker.property.Property;
import mod.acgaming.universaltweaks.mods.biomesoplenty.biometweaker.UTBTBOPTerrainProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BiomePropertyManager.class, remap = false)
public abstract class UTBTBiomePropertyManagerMixin
{
    @Shadow
    @Final
    private static Map<String, Property<?>> propertyMap;

    @Inject(method = "populatePropertyMap", at = @At("TAIL"))
    private static void utPopulatePropertyMap(CallbackInfo ci)
    {
        propertyMap.put("averageheight", new UTBTBOPTerrainProperty("avgHeight"));
        propertyMap.put("variationabove", new UTBTBOPTerrainProperty("variationAbove"));
        propertyMap.put("variationbelow", new UTBTBOPTerrainProperty("variationBelow"));
    }
}
