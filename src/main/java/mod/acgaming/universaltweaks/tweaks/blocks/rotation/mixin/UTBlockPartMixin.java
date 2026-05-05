package mod.acgaming.universaltweaks.tweaks.blocks.rotation.mixin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.util.JsonUtils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(targets = "net.minecraft.client.renderer.block.model.BlockPart$Deserializer")
public abstract class UTBlockPartMixin
{
    /**
     * @author ACGaming
     * @reason Unlimited Block Model Rotations
     */
    @Overwrite
    private float parseAngle(JsonObject object)
    {
        float angle = JsonUtils.getFloat(object, "angle");
        if (angle < -360.0F || angle > 360.0F) throw new JsonParseException("Invalid rotation " + angle + " found, must be between -360 and 360");
        return angle;
    }
}
