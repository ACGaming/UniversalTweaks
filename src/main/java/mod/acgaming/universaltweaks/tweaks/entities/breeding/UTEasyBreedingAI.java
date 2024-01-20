package mod.acgaming.universaltweaks.tweaks.entities.breeding;

import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of Gigabit101
public class UTEasyBreedingAI extends EntityAIBase
{
    public final EntityAnimal animal;
    public final World world;

    public UTEasyBreedingAI(EntityAnimal animal)
    {
        this.animal = animal;
        this.world = animal.world;
    }

    @Nullable
    public EntityItem checkFood()
    {
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTEasyBreedingAI ::: Check food");
        List<EntityItem> items = getItems();
        for (EntityItem item : items) return item;
        return null;
    }

    public boolean shouldExecute()
    {
        EntityItem nearbyFood = checkFood();
        if (nearbyFood != null && this.animal.isBreedingItem(nearbyFood.getItem()) && !this.animal.isChild() && this.animal.getGrowingAge() == 0 && !this.animal.isInLove()) execute(this.animal, nearbyFood);
        return false;
    }

    public void execute(EntityAnimal animal, EntityItem item)
    {
        if (animal.getNavigator().tryMoveToXYZ(item.posX, item.posY, item.posZ, 1.25F) && animal.getDistance(item) < 1.1F)
        {
            consumeFood(item);
            animal.setInLove(null);
        }
    }

    public void consumeFood(EntityItem item)
    {
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTEasyBreedingAI ::: Consume food");
        ItemStack stack = item.getItem();
        stack.setCount(stack.getCount() - 1);
        if (stack.getCount() == 0) item.setDead();
        this.world.playSound(null, item.getPosition(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.AMBIENT, 1.0F, 1.0F);
    }

    List<EntityItem> getItems()
    {
        double distance = UTConfigTweaks.ENTITIES.EASY_BREEDING.utEasyBreedingDistance;
        return world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(animal.posX - distance, animal.posY - distance, animal.posZ - distance, animal.posX + distance, animal.posY + distance, animal.posZ + distance));
    }
}