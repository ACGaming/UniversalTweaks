package mod.acgaming.universaltweaks.mods.reccomplex.mixin;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import ivorius.reccomplex.utils.ByteArrays;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = ByteArrays.class, remap = false)
public class UTByteArraysMixin
{

    /**
     * @author MCTian_mi
     * @reason read more bytes each time
     */
    @Overwrite
    public static byte[] completeByteArray(InputStream inputStream)
    {
        try
        {
            ByteArrayOutputStream out = new ByteArrayOutputStream(8192);
            byte[] buffer = new byte[8192];
            int len;

            while ((len = inputStream.read(buffer)) != -1)
            {
                out.write(buffer, 0, len);
            }

            return out.toByteArray();
        }
        catch (IOException ignored)
        {
            return null;
        }
    }
}