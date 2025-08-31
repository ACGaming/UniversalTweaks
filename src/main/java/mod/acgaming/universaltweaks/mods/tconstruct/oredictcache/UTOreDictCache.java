package mod.acgaming.universaltweaks.mods.tconstruct.oredictcache;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.Loader;

import mod.acgaming.universaltweaks.UniversalTweaks;

// Courtesy of youyihj
public class UTOreDictCache
{
    public static final File cacheFile = new File("config" + File.separator + "tconstruct_oredictcache.dat");
    public static NBTTagCompound cacheNBT = new NBTTagCompound();

    public static void preInit()
    {
        UTOreDictRecipesState.setCurrentState(cacheFile.exists() ? UTOreDictRecipesState.READ : UTOreDictRecipesState.SCAN);
        if (UTOreDictRecipesState.getCurrentState().isRead())
        {
            try
            {
                cacheNBT = Objects.requireNonNull(CompressedStreamTools.read(cacheFile));
            }
            catch (IOException e)
            {
                UniversalTweaks.LOGGER.error("UTOreDictCache ::: Failed to read cache file! Rescanning all recipes...", e);
                UTOreDictRecipesState.setCurrentState(UTOreDictRecipesState.SCAN);
            }
            final NBTTagList modsNBT = cacheNBT.getTagList("Mods", Constants.NBT.TAG_STRING);
            if (!hasSameElements(getInstalledModIds(), StreamSupport.stream(modsNBT.spliterator(), false).map(nbt -> ((NBTTagString) nbt).getString()).collect(Collectors.toList())))
            {
                UniversalTweaks.LOGGER.info("UTOreDictCache ::: Detected installed mods are different from the last launch. Rescanning all recipes...");
                cacheNBT = new NBTTagCompound();
                UTOreDictRecipesState.setCurrentState(UTOreDictRecipesState.SCAN);
            }
        }
        if (UTOreDictRecipesState.getCurrentState().isScan())
        {
            final NBTTagList modsNBT = new NBTTagList();
            getInstalledModIds().stream().map(NBTTagString::new).forEach(modsNBT::appendTag);
            cacheNBT.setTag("Mods", modsNBT);
        }
    }

    public static void onLoadComplete()
    {
        cacheNBT = null;
    }

    public static Collection<String> getInstalledModIds()
    {
        return Loader.instance().getIndexedModList().keySet();
    }

    public static <T> boolean hasSameElements(Collection<T> first, Collection<T> second)
    {
        if (first.size() != second.size()) return false;
        Set<T> collection = new HashSet<>(first);
        collection.addAll(second);
        return collection.size() == first.size();
    }
}