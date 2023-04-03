package mod.acgaming.universaltweaks.tweaks.misc.toastcontrol;

import net.minecraft.client.Minecraft;
import net.minecraft.client.tutorial.TutorialSteps;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

// Courtesy of HeckinChloe
public class UTTutorialToast
{
    public static void utTutorialToast()
    {
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTTutorialToast ::: Set tutorial step");
        Minecraft.getMinecraft().getTutorial().setStep(TutorialSteps.NONE);
    }
}