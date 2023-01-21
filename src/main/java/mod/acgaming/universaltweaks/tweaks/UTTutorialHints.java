package mod.acgaming.universaltweaks.tweaks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.tutorial.TutorialSteps;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

// Courtesy of HeckinChloe
public class UTTutorialHints
{
    public static void utTutorialHints()
    {
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTTutorialHints ::: Set tutorial step");
        Minecraft.getMinecraft().getTutorial().setStep(TutorialSteps.NONE);
    }
}