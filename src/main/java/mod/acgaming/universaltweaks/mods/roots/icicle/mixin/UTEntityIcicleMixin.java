package mod.acgaming.universaltweaks.mods.roots.icicle.mixin;

import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import epicsquid.roots.entity.spell.EntityIcicle;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// Courtesy of WaitingIdly
@Mixin(EntityIcicle.class)
public abstract class UTEntityIcicleMixin extends EntityFireball
{
    // This makes the icicle save the spell type to nbt.
    // Without it, it does switch(type) and implodes with a NPE due to the type being null.
    @Shadow(remap = false)
    private EntityIcicle.SpellType type;

    private UTEntityIcicleMixin(World worldIn)
    {
        super(worldIn);
    }

    @Override
    public void writeEntityToNBT(@NotNull NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setString("SPELL_TYPE", type.name());
    }

    @Override
    public void readEntityFromNBT(@NotNull NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        type = EntityIcicle.SpellType.valueOf(compound.getString("SPELL_TYPE"));
    }
}
