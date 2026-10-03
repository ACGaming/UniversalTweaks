package mod.acgaming.universaltweaks.mods.enderutilities.client;

import java.awt.Rectangle;
import java.util.Collections;
import java.util.List;

import fi.dy.masa.enderutilities.gui.client.GuiHandyBag;
import mezz.jei.api.gui.IAdvancedGuiHandler;

public class UTGuiHandyBagJEIHandler implements IAdvancedGuiHandler<GuiHandyBag>
{
    @Override
    public Class<GuiHandyBag> getGuiContainerClass()
    {
        return GuiHandyBag.class;
    }

    @Override
    public List<Rectangle> getGuiExtraAreas(GuiHandyBag gui)
    {
        Rectangle area = ((UTGuiHandyBagSidebarButtonAreaProvider) gui).ut$getSidebarButtonArea();

        if (area == null)
        {
            return Collections.emptyList();
        }

        return Collections.singletonList(area);
    }
}
