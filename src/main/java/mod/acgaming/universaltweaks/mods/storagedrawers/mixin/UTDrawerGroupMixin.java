package mod.acgaming.universaltweaks.mods.storagedrawers.mixin;


import com.jaquadro.minecraft.storagedrawers.block.tile.tiledata.StandardDrawerGroup;
import mod.acgaming.universaltweaks.mods.storagedrawers.api.IAuxData;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = StandardDrawerGroup.class, remap = false)
public abstract class UTDrawerGroupMixin
{

    @Mixin(value = StandardDrawerGroup.DrawerData.class, remap = false)
    public abstract static class DrawerDataMixin implements IAuxData
    {

        @Shadow
        public abstract void setExtendedData(String key, Object data);

        @Shadow
        public abstract Object getExtendedData(String key);

        @Override
        public void UT$setData(String key, Object value)
        {
            setExtendedData(key, value);
        }

        @Override
        public @Nullable Object UT$getData(String key)
        {
            return getExtendedData(key);
        }
    }
}
