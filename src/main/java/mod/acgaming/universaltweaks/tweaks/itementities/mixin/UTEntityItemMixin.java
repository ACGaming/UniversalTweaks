package mod.acgaming.universaltweaks.tweaks.itementities.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.config.UTConfig;
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
    private int pickupDelay;

    public UTEntityItemMixin(World worldIn)
    {
        super(worldIn);
    }

    @Unique
    public boolean isCollectionTool(Item item)
    {
        String registryName = item.getRegistryName().toString();
        for (String collectionTool : UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utIECollectionTools)
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
        if (UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utIEPhysicsToggle && this.ticksExisted > 10) return this.getEntityBoundingBox();
        else return null;
    }

    @Override
    public void applyEntityCollision(Entity entity)
    {
        if (UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utIEPhysicsToggle && this.ticksExisted > 10)
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
        return UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utIEPhysicsToggle && this.ticksExisted > 10;
    }

    @Override
    public boolean canBePushed()
    {
        return UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utIEPhysicsToggle && this.ticksExisted > 10;
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
    {
        if (!UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utIEAutomaticPickupToggle)
        {
            if (!UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utIESneakingPickupToggle || player.isSneaking())
            {
                playerInteraction = true;
                this.onCollideWithPlayer(player);
                playerInteraction = false;
                return true;
            }
        }
        playerInteraction = false;
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBox(Entity entity)
    {
        return UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utIEPhysicsToggle && this.ticksExisted > 10 || entity.canBePushed() ? entity.getEntityBoundingBox() : null;
    }

    @Inject(method = "onCollideWithPlayer", at = @At("HEAD"), cancellable = true)
    public void utIEOnCollideWithPlayer(EntityPlayer entityIn, CallbackInfo ci)
    {
        Item heldItemMainhand = entityIn.getHeldItemMainhand().getItem();
        Item heldItemOffhand = entityIn.getHeldItemOffhand().getItem();
        if (!UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utIEAutomaticPickupToggle && !playerInteraction && !isCollectionTool(heldItemMainhand) && !isCollectionTool(heldItemOffhand) || UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utIESneakingPickupToggle && !entityIn.isSneaking())
        {
            ci.cancel();
        }
    }

    @Inject(method = "searchForOtherItemsNearby", at = @At("HEAD"), cancellable = true)
    public void utIENoCombinationSearch(CallbackInfo ci)
    {
        if (UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utIENoCombinationToggle) ci.cancel();
    }

    @Inject(method = "combineItems", at = @At("HEAD"), cancellable = true)
    public void utIENoCombinationCombine(EntityItem other, CallbackInfoReturnable<Boolean> cir)
    {
        if (UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utIENoCombinationToggle) cir.setReturnValue(false);
    }

    @Inject(method = "setPickupDelay", at = @At("TAIL"))
    public void utIESetPickupDelay(int ticks, CallbackInfo ci)
    {
        if (UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utIEPickupDelay > -1) this.pickupDelay = UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utIEPickupDelay;
    }

    @Inject(method = "setDefaultPickupDelay", at = @At(value = "TAIL"))
    public void utIESetDefaultPickupDelay(CallbackInfo ci)
    {
        if (UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utIEPickupDelay > -1) this.pickupDelay = UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utIEPickupDelay;
    }

    // Courtesy of Darkhax
    @Inject(method = "searchForOtherItemsNearby", at = @At("HEAD"), cancellable = true)
    public void utIESmartCombinationSearch(CallbackInfo ci)
    {
        if (!UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utIESmartCombinationToggle) return;
        final ItemStack stack = this.getItem();
        if (stack.getCount() >= stack.getMaxStackSize()) ci.cancel();
    }

    // Courtesy of Darkhax
    @Inject(method = "combineItems", at = @At("HEAD"), cancellable = true)
    public void utIESmartCombinationCombine(EntityItem other, CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utIESmartCombinationToggle) return;
        final ItemStack stack = this.getItem();
        if (stack.getCount() >= stack.getMaxStackSize()) cir.setReturnValue(false);
    }
}