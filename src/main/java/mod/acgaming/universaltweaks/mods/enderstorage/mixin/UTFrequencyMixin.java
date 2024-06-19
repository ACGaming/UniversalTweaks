package mod.acgaming.universaltweaks.mods.enderstorage.mixin;

import java.util.Objects;
import codechicken.enderstorage.api.Frequency;
import codechicken.lib.colour.EnumColour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

// Courtesy of jchung01
@Mixin(value = Frequency.class, remap = false)
public class UTFrequencyMixin
{
    @Shadow
    public EnumColour left;
    @Shadow
    public EnumColour middle;
    @Shadow
    public EnumColour right;
    @Shadow
    public String owner;

    /**
     * Add equals() override to fulfill Object equality contract.
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Frequency that = (Frequency) o;
        return left == that.left && middle == that.middle && right == that.right && Objects.equals(owner, that.owner);
    }

    /**
     * @author jchung01
     * @reason Use a more proper hashCode method.
     */
    @Override
    @Overwrite
    public int hashCode()
    {
        return Objects.hash(left, middle, right, owner);
    }
}
