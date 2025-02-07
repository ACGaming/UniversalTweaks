package mod.acgaming.universaltweaks.mods.storagedrawers.mixin;


import com.jaquadro.minecraft.storagedrawers.block.tile.tiledata.FractionalDrawerGroup;
import mod.acgaming.universaltweaks.mods.storagedrawers.api.IAuxData;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = FractionalDrawerGroup.class, remap = false)
public abstract class UTFractionalGroupMixin {

    @Mixin(targets = "com.jaquadro.minecraft.storagedrawers.block.tile.tiledata.FractionalDrawerGroup$FractionalDrawer",
        remap = false)
    public static abstract class FractionalDataMixin implements IAuxData {

        @Shadow
        public abstract void setExtendedData(String key, Object data);

        @Shadow
        public abstract Object getExtendedData(String key);

        @Override
        public void UT$setData(String key, Object value) {
            setExtendedData(key, value);
        }

        @Override
        public @Nullable Object UT$getData(String key) {
            return getExtendedData(key);
        }
    }
}
