package mod.acgaming.universaltweaks.bugfixes.blocks.fallingblockdamage.mixin;

import java.util.ArrayList;

import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// https://bugs.mojang.com/browse/MC-572
// https://bugs.mojang.com/browse/MC-120158
@Mixin(EntityFallingBlock.class)
public abstract class UTFallingBlockDamageMixin extends Entity
{
    public UTFallingBlockDamageMixin(World worldIn)
    {
        super(worldIn);
    }

    @Redirect(method = "fall", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Lists;newArrayList(Ljava/lang/Iterable;)Ljava/util/ArrayList;", remap = false))
    public ArrayList<Entity> utFallingBlockDamage(Iterable<? extends Entity> elements)
    {
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTFallingBlockDamage ::: Block falling");
        return Lists.newArrayList(this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox()));
    }
}