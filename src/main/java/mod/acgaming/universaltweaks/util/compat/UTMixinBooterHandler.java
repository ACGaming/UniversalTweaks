package mod.acgaming.universaltweaks.util.compat;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.text.TextComponentTranslation;

public class UTMixinBooterHandler
{
    public static boolean showMixinBooter = false;

    static
    {
        try
        {
            Class.forName("com.llamalad7.mixinextras.MixinExtrasBootstrap");
        }
        catch (ClassNotFoundException ignored)
        {
            showMixinBooter = true;
        }
    }

    public static List<String> mixinBooterMessage()
    {
        List<String> messages = new ArrayList<>();
        messages.add(new TextComponentTranslation("msg.universaltweaks.mixinbooter.warning1").getFormattedText());
        messages.add(new TextComponentTranslation("msg.universaltweaks.mixinbooter.warning2").getFormattedText());
        messages.add("");
        messages.add(new TextComponentTranslation("msg.universaltweaks.mixinbooter.warning3").getFormattedText());
        return messages;
    }
}