package mod.acgaming.hkntweaks.core;

import com.google.common.eventbus.EventBus;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;

public class HkNTweaksContainer extends DummyModContainer
{
    public HkNTweaksContainer()
    {
        super(new ModMetadata());
        ModMetadata meta = this.getMetadata();
        meta.modId = "hkntweakscore";
        meta.name = "HkN Tweaks Core";
        meta.description = "Core functionality of HkN Tweaks";
        meta.version = "1.12.2-1.0.0";
        meta.authorList.add("ACGaming");
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller)
    {
        bus.register(this);
        return true;
    }
}