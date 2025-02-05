package mod.acgaming.universaltweaks.mods.storagedrawers.api;

import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IAuxData
{
    String KEY = "insertion_data";

    void UT$setData(String key, Object value);

    @Nullable
    Object UT$getData(String key);

    @NotNull
    default Int2IntMap getOrCreateData() {
        Int2IntMap v = (Int2IntMap) UT$getData(KEY);
        if (v == null) {
            v = new Int2IntArrayMap();
            UT$setData(KEY, v);
        }
        return v;
    }
}
