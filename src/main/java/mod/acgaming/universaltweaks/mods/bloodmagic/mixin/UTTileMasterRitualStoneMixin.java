package mod.acgaming.universaltweaks.mods.bloodmagic.mixin;

import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import WayofTime.bloodmagic.ritual.AreaDescriptor;
import WayofTime.bloodmagic.ritual.IMasterRitualStone;
import WayofTime.bloodmagic.ritual.Ritual;
import WayofTime.bloodmagic.tile.TileMasterRitualStone;
import WayofTime.bloodmagic.tile.base.TileTicking;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = TileMasterRitualStone.class, remap = false)
public abstract class UTTileMasterRitualStoneMixin extends TileTicking implements IMasterRitualStone
{
    @Shadow
    @Final
    protected Map<String, AreaDescriptor> modableRangeMap;
    @Shadow
    private Ritual currentRitual;

    @Shadow
    public abstract AreaDescriptor getBlockRange(String range);

    @Shadow
    public abstract void addBlockRange(String range, AreaDescriptor defaultRange);

    /**
     * @author Invadermonky
     * @reason Fixes ritual resetting on chunk/world unload.
     *
     * <p>This was caused by the MRS pulling the range map data from the Ritual itself, which only stores the default ritual
     * area information, resulting in an empty range map. The new method redirects the method to populate the MRS range map
     * from the MRS NBT.</p>
     */
    @Redirect(method = "deserialize", at = @At(value = "INVOKE", target = "LWayofTime/bloodmagic/ritual/Ritual;readFromNBT(Lnet/minecraft/nbt/NBTTagCompound;)V"))
    private void customRitualReadFromNBT(Ritual instance, NBTTagCompound ritualTag)
    {
        if (this.currentRitual != null)
        {
            this.currentRitual.readFromNBT(ritualTag);
        }
        NBTTagList tags = ritualTag.getTagList("areas", 10);
        if (!tags.isEmpty())
        {
            for (int i = 0; i < tags.tagCount(); i++)
            {
                NBTTagCompound newTag = tags.getCompoundTagAt(i);
                String rangeKey = newTag.getString("key");
                NBTTagCompound storedTag = newTag.getCompoundTag("area");
                AreaDescriptor desc = this.currentRitual.getBlockRange(rangeKey).copy();
                if (desc != null)
                {
                    desc.readFromNBT(storedTag);
                    this.addBlockRange(rangeKey, desc);
                }
            }
        }
    }

    /**
     * @author Invadermonky
     * @reason Fixes ritual resetting on chunk/world unload.
     *
     * <p>This was caused by the MRS pulling the range map data from the Ritual itself, which only stores the default ritual
     * area information, resulting in an empty range map. The new method redirects the method to populate the MRS range map
     * from the MRS NBT.</p>
     */
    @Redirect(method = "serialize", at = @At(value = "INVOKE", target = "LWayofTime/bloodmagic/ritual/Ritual;writeToNBT(Lnet/minecraft/nbt/NBTTagCompound;)V"))
    private void customRitualWriteToNBT(Ritual instance, NBTTagCompound ritualTag)
    {
        if (this.currentRitual != null)
        {
            this.currentRitual.writeToNBT(ritualTag);
        }
        NBTTagList tags = new NBTTagList();
        for (Map.Entry<String, AreaDescriptor> entry : this.modableRangeMap.entrySet())
        {
            NBTTagCompound newTag = new NBTTagCompound();
            newTag.setString("key", entry.getKey());
            NBTTagCompound storedTag = new NBTTagCompound();
            entry.getValue().writeToNBT(storedTag);
            newTag.setTag("area", storedTag);
            tags.appendTag(newTag);
        }
        ritualTag.setTag("areas", tags);
    }
}
