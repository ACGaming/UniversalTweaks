package mod.acgaming.universaltweaks.tweaks.items.itementities.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.mods.vanilla.mixin.UTEntityItemAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EntityItem.class, priority = 1002)
public abstract class UTEntityItemMixin extends Entity
{
    @Unique
    public boolean playerInteraction;
    @Shadow
    public int pickupDelay;

    protected UTEntityItemMixin(World worldIn)
    {
        super(worldIn);
    }

    @Unique
    public boolean isCollectionTool(Item item)
    {
        String registryName = item.getRegistryName().toString();
        for (String collectionTool : UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIECollectionTools)
        {
            if (registryName.equals(collectionTool)) return true;
        }
        return false;
    }

    @Shadow
    public abstract ItemStack getItem();

    @Override
    public AxisAlignedBB getCollisionBoundingBox()
    {
        if (UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIEPhysicsToggle && this.ticksExisted > 10) return this.getEntityBoundingBox();
        return super.getCollisionBoundingBox();
    }

    @Override
    public void applyEntityCollision(Entity entity)
    {
        if (UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIEPhysicsToggle && this.ticksExisted > 10)
        {
            if (entity instanceof EntityItem)
            {
                if (entity.getEntityBoundingBox().minY < this.getEntityBoundingBox().maxY) super.applyEntityCollision(entity);
            }
            else if (entity.getEntityBoundingBox().minY <= this.getEntityBoundingBox().minY) super.applyEntityCollision(entity);
        }
        else super.applyEntityCollision(entity);
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIEPhysicsToggle ? this.ticksExisted > 10 : super.canBeCollidedWith();
    }

    @Override
    public boolean canBePushed()
    {
        return UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIEPhysicsToggle ? this.ticksExisted > 10 : super.canBePushed();
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
    {
        if (!UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIEAutomaticPickupToggle)
        {
            if (!UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIESneakingPickupToggle || player.isSneaking())
            {
                playerInteraction = true;
                this.onCollideWithPlayer(player);
                playerInteraction = false;
                return true;
            }
        }
        playerInteraction = false;
        return super.processInitialInteract(player, hand);
    }

    @Override
    public AxisAlignedBB getCollisionBox(Entity entity)
    {
        if (UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIEPhysicsToggle && (this.ticksExisted > 10 || entity.canBePushed())) return entity.getEntityBoundingBox();
        return super.getCollisionBox(entity);
    }

    @Inject(method = "onCollideWithPlayer", at = @At("HEAD"), cancellable = true)
    public void utIEOnCollideWithPlayer(EntityPlayer entityIn, CallbackInfo ci)
    {
        Item heldItemMainhand = entityIn.getHeldItemMainhand().getItem();
        Item heldItemOffhand = entityIn.getHeldItemOffhand().getItem();
        if (!UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIEAutomaticPickupToggle && !playerInteraction && !isCollectionTool(heldItemMainhand) && !isCollectionTool(heldItemOffhand) || UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIESneakingPickupToggle && !entityIn.isSneaking())
        {
            ci.cancel();
        }
    }

    @Inject(method = "setPickupDelay", at = @At("TAIL"))
    public void utIESetPickupDelay(int ticks, CallbackInfo ci)
    {
        if (UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIEPickupDelay > -1) this.pickupDelay = UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIEPickupDelay;
    }

    @Inject(method = "setDefaultPickupDelay", at = @At(value = "TAIL"))
    public void utIESetDefaultPickupDelay(CallbackInfo ci)
    {
        if (UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIEPickupDelay > -1) this.pickupDelay = UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIEPickupDelay;
    }

    // Courtesy of Darkhax, bl4ckscor3
    @Inject(method = "searchForOtherItemsNearby", at = @At("HEAD"), cancellable = true)
    public void utIECombinationSearch(CallbackInfo ci)
    {
        if (UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIENoCombinationToggle) ci.cancel();
        if (UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIESmartCombinationToggle)
        {
            // Check maximum stack size
            final ItemStack stack = this.getItem();
            if (stack.getCount() >= stack.getMaxStackSize()) ci.cancel();
            // Check configurable radius
            EntityItem entityItem = (EntityItem) (Object) this;
            if (entityItem.ticksExisted < 20) return;
            double radius = UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIESmartCombinationRadius;
            boolean checkY = UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIESmartCombinationYAxis;
            for (EntityItem i : entityItem.world.getEntitiesWithinAABB(EntityItem.class, entityItem.getEntityBoundingBox().grow(radius, checkY ? radius : 0, radius))) ((UTEntityItemAccessor) entityItem).callCombineItems(i);
            ci.cancel();
        }
    }

    // Courtesy of Darkhax
    @Inject(method = "combineItems", at = @At("HEAD"), cancellable = true)
    public void utIECombinationCombine(EntityItem other, CallbackInfoReturnable<Boolean> cir)
    {
        if (UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIENoCombinationToggle) cir.setReturnValue(false);
        if (UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIESmartCombinationToggle)
        {
            // Check maximum stack size
            final ItemStack stack = this.getItem();
            if (stack.getCount() >= stack.getMaxStackSize()) cir.setReturnValue(false);
        }
    }

    // Courtesy of fonnymunkey
    @WrapWithCondition(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/item/EntityItem;move(Lnet/minecraft/entity/MoverType;DDD)V"))
    public boolean utIEOnUpdate(EntityItem instance, MoverType moverType, double dx, double dy, double dz)
    {
        if (!UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIEUpdateToggle) return true;
        // Run on odd ticks to not skip the '% 25' check
        return instance.ticksExisted % 2 != 0;
    }
}