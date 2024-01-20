package mod.acgaming.universaltweaks.tweaks.misc.pickupnotification;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of gigaherz
public class UTPickupNotificationOverlay extends GuiScreen
{
    public static void init()
    {
        MinecraftForge.EVENT_BUS.register(new UTPickupNotificationOverlay());
    }

    private static boolean areSameishItem(ItemStack a, ItemStack b)
    {
        return a == b || isStackEmpty(a) && isStackEmpty(b) || (ItemStack.areItemsEqual(a, b) && ItemStack.areItemStackTagsEqual(a, b));
    }

    private static boolean isStackEmpty(ItemStack stack)
    {
        return stack.getCount() <= 0;
    }

    private static ItemStack safeCopy(ItemStack stack)
    {
        return stack.copy();
    }

    private final List<ChangeInfo> changeEntries = Lists.newArrayList();
    private final RenderItem renderItem;
    private int hardLimit;
    private int dim;
    private int dimLoadTicks;
    private ItemStack[] previous;
    private int previousXP = -1;
    private EntityPlayer playerEntity;
    private ItemStack previousInCursor = ItemStack.EMPTY;

    private UTPickupNotificationOverlay()
    {
        renderItem = Minecraft.getMinecraft().getRenderItem();
    }

    @SubscribeEvent
    public void utPUNRenderOverlay(RenderGameOverlayEvent.Post event)
    {
        if (!UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNItemAdditions && !UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNItemRemovals) return;

        if (event.getType() != RenderGameOverlayEvent.ElementType.CHAT) return;

        ScaledResolution resolution = event.getResolution();
        int width = resolution.getScaledWidth();
        int height = resolution.getScaledHeight();

        width = (int) (width / UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNScaleName);
        height = (int) (height / UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNScaleName);

        FontRenderer font = Minecraft.getMinecraft().fontRenderer;

        int iconSize = (int) (16 * UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNScaleIcon);
        int rightMargin = UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNDisplayIcon ? (2 + iconSize) : 0;
        int topMargin1 = 2 + (UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNDisplayIcon ? Math.max(0, (iconSize - font.FONT_HEIGHT) / 2) : 0);
        int topMargin2 = 1 + Math.max(0, -(iconSize - font.FONT_HEIGHT) / 2);

        int lineHeight = font.FONT_HEIGHT;
        if (UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNDisplayIcon) lineHeight = Math.max(2 + iconSize, lineHeight);

        hardLimit = height / lineHeight;

        List<Triple<ChangeInfo, String[], Integer>> computedStrings = Lists.newArrayList();

        int rectWidth;
        int number;

        synchronized (changeEntries)
        {
            if (changeEntries.isEmpty()) return;

            rectWidth = computeStrings(computedStrings, font);

            number = computedStrings.size();
            if (number == 0) return;
        }

        GlStateManager.pushMatrix();
        GlStateManager.scale(UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNScaleName, UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNScaleName, 1);

        rectWidth += rightMargin;

        int rectHeight = lineHeight * number;

        int x;
        int y;
        int align;
        switch (UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNSnapPosition)
        {
            default:
            case BOTTOM_RIGHT:
                x = width - 2 - rectWidth - UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNOffsetHorizontal;
                y = height - 2 - rectHeight - UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNOffsetVertical;
                align = 1;
                break;
            case BOTTOM:
                x = (width - rectWidth) / 2 - 2 + UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNOffsetHorizontal;
                y = height - 2 - rectHeight - UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNOffsetVertical;
                align = 0;
                break;
            case BOTTOM_LEFT:
                x = 2 + UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNOffsetHorizontal;
                y = height - 2 - rectHeight - UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNOffsetVertical;
                align = -1;
                break;
            case LEFT:
                x = 2 + UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNOffsetHorizontal;
                y = (height - rectHeight) / 2 - 2 + UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNOffsetVertical;
                align = -1;
                break;
            case TOP_LEFT:
                x = 2 + UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNOffsetHorizontal;
                y = 2 + UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNOffsetVertical;
                align = -1;
                break;
            case TOP:
                x = (width - rectWidth) / 2 - 2 + UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNOffsetHorizontal;
                y = 2 + UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNOffsetVertical;
                align = 0;
                break;
            case TOP_RIGHT:
                x = width - 2 - rectWidth - UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNOffsetHorizontal;
                y = 2 + UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNOffsetVertical;
                align = 1;
                break;
            case RIGHT:
                x = width - 2 - rectWidth - UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNOffsetHorizontal;
                y = (height - rectHeight) / 2 - 2 + UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNOffsetVertical;
                align = 1;
                break;
            case CENTER:
                x = (width - rectWidth) / 2 - 2 + UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNOffsetHorizontal;
                y = (height - rectHeight) / 2 - 2 + UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNOffsetVertical;
                align = 0;
                break;
        }

        if (UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNDisplayBackground) drawRect(x - 2, y - 2, x + rectWidth + 4, y + rectHeight + 4, Integer.MIN_VALUE);

        for (Triple<ChangeInfo, String[], Integer> e : computedStrings)
        {
            ChangeInfo change = e.getLeft();
            String[] strings = e.getMiddle();
            int fade = e.getRight();

            int w = 0;
            int[] widths = new int[strings.length];
            for (int n = 0; n < strings.length; n++)
            {
                String str = strings[n];
                int wn = widths[n] = font.getStringWidth(str);
                w += wn;
            }

            int forcedFade = UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNFadeLimit > 0 ? (fade * 255 / (UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNFadeLimit + 2)) : 255;
            int ttlFade = change.ttl * 255 / UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNFadeDuration;
            int alpha = Math.min(255, Math.min(forcedFade, ttlFade));
            int color = alpha << 24 | (change.mode == ChangeMode.OBTAINED ? 0x7FFF7F : 0xFF5F5F);

            int leftMargin = 0;
            switch (align)
            {
                case -1:
                    leftMargin = 2;
                    break;
                case 0:
                    leftMargin = (rectWidth - w - rightMargin) / 2;
                    break;
                case 1:
                    leftMargin = rectWidth - w - rightMargin;
                    break;
            }

            GlStateManager.enableBlend();
            int wAcc = 0;
            for (int n = 0; n < strings.length; n++)
            {
                font.drawStringWithShadow(strings[n], x + leftMargin + wAcc, y + topMargin1, color);
                wAcc += widths[n];
            }

            if (UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNDisplayIcon)
            {
                GlStateManager.pushMatrix();
                GlStateManager.translate(x + 2 + w + leftMargin, y + topMargin2, 0);
                GlStateManager.scale(UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNScaleIcon, UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNScaleIcon, 1);
                RenderHelper.enableGUIStandardItemLighting();
                if (change.item.stack.getItem() == Items.EXPERIENCE_BOTTLE && change.item.stack.getMetadata() == 42)
                {
                    ItemStack xpTemp = new ItemStack(Items.EXPERIENCE_BOTTLE);
                    renderItem.renderItemAndEffectIntoGUI(xpTemp, 0, 0);
                    renderItem.renderItemOverlayIntoGUI(font, xpTemp, 0, 0, null);
                }
                else
                {
                    renderItem.renderItemAndEffectIntoGUI(change.item.stack, 0, 0);
                    renderItem.renderItemOverlayIntoGUI(font, change.item.stack, 0, 0, null);
                }
                RenderHelper.disableStandardItemLighting();
                GlStateManager.popMatrix();
            }

            y += lineHeight;
        }

        GlStateManager.popMatrix();
    }

    @SubscribeEvent
    public void utPUNClientTick(TickEvent.ClientTickEvent event)
    {
        if (event.phase != TickEvent.Phase.END) return;

        if (!UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNItemAdditions && !UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNItemRemovals) return;

        EntityPlayer player = Minecraft.getMinecraft().player;

        if (player == null) return;

        if (player != playerEntity)
        {
            if (player.inventoryContainer != null)
            {
                player.inventoryContainer = new UTContainerWrapper((ContainerPlayer) player.inventoryContainer, player, () -> {
                    previous = null;
                    dimLoadTicks = 0;
                });
                playerEntity = player;
            }
            previous = null;
        }

        if (player.dimension != dim)
        {
            previous = null;
            dimLoadTicks = 200;
            dim = player.dimension;
        }

        if (dimLoadTicks > 0)
        {
            previous = null;
            dimLoadTicks--;
            return;
        }

        synchronized (changeEntries)
        {
            changeEntries.forEach(e -> e.ttl--);
            while (changeEntries.size() > hardLimit) changeEntries.remove(0);
            changeEntries.removeIf(e -> e.ttl <= 0 || e.count == 0);
        }

        if (previous == null || previous.length != player.inventory.getSizeInventory())
        {
            previous = new ItemStack[player.inventory.getSizeInventory()];
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) previous[i] = safeCopy(player.inventory.getStackInSlot(i));
            previousInCursor = player.inventory.getItemStack();
            return;
        }

        final List<Pair<ItemStack, ItemStack>> changes = Lists.newArrayList();
        for (int i = 0; i < player.inventory.getSizeInventory(); i++)
        {
            ItemStack stack = player.inventory.getStackInSlot(i);
            ItemStack old = previous[i];
            if (isChangeMeaningful(old, stack)) changes.add(Pair.of(old, stack));
            previous[i] = stack.copy();
        }

        ItemStack stackInCursor = player.inventory.getItemStack();
        if (isChangeMeaningful(stackInCursor, previousInCursor)) changes.add(Pair.of(previousInCursor, stackInCursor));
        previousInCursor = stackInCursor.copy();

        if (UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNExperience)
        {
            if (previousXP == -1) previousXP = player.experienceTotal;
            else if (previousXP != player.experienceTotal)
            {
                changes.add(Pair.of(new ItemStack(Items.EXPERIENCE_BOTTLE, previousXP, 42), new ItemStack(Items.EXPERIENCE_BOTTLE, player.experienceTotal, 42)));
                previousXP = player.experienceTotal;
            }
        }

        if (changes.isEmpty()) return;

        final List<ChangeInfo> changeList = Lists.newArrayList();
        changes.forEach(change -> {
            ItemStack left = change.getLeft();
            boolean leftEmpty = left.getCount() <= 0;

            ItemStack right = change.getRight();
            boolean rightEmpty = right.getCount() <= 0;

            if (areSameishItem(left, right))
            {
                if (!isBlacklisted(left))
                {
                    int difference = right.getCount() - left.getCount();
                    if (difference > 0) obtainedItem(changeList, left, difference);
                    else if (difference < 0) lostItem(changeList, left, -difference);
                }
            }
            else
            {
                if (!leftEmpty && !isBlacklisted(left))
                {
                    lostItem(changeList, left, left.getCount());
                }
                if (!rightEmpty && !isBlacklisted(right))
                {
                    obtainedItem(changeList, right, right.getCount());
                }
            }
        });

        changeList.removeIf(e -> e.count == 0);

        if (!changeList.isEmpty())
        {
            synchronized (changeEntries)
            {
                for (ChangeInfo info : changeList)
                {
                    if (info.count == 0) continue;
                    accumulate(changeEntries, info.item.stack, info.mode, info.count, false);
                }
            }
        }
    }

    private int computeStrings(List<Triple<ChangeInfo, String[], Integer>> computedStrings, FontRenderer font)
    {
        int rectWidth = 0;
        int itemsToShow = Math.min(Math.min(hardLimit, UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNSoftLimit + UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNFadeLimit), changeEntries.size());
        int offset = Math.max(0, changeEntries.size() - itemsToShow);
        int fadeOffset = changeEntries.size() - UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNSoftLimit - UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNFadeLimit;

        for (int i = offset; i < changeEntries.size(); i++)
        {
            ChangeInfo change = changeEntries.get(i);
            String[] parts = getChangeStrings(change);

            int w = Arrays.stream(parts).mapToInt(font::getStringWidth).sum();

            rectWidth = Math.max(rectWidth, w);

            computedStrings.add(Triple.of(change, parts, Math.min(UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNFadeLimit + 2, 1 + i - fadeOffset)));
        }
        return rectWidth;
    }

    private String[] getChangeStrings(ChangeInfo change)
    {
        String mode = change.mode == ChangeMode.OBTAINED ? "+" : "-";
        String s1 = String.format("%s%d", mode, change.count);
        if (UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNDisplayName)
        {
            String name = change.item.stack.getDisplayName();
            String italics = change.item.stack.hasDisplayName() ? String.valueOf(TextFormatting.ITALIC) : "";
            if (change.item.stack.getItem() == Items.EXPERIENCE_BOTTLE && change.item.stack.getMetadata() == 42)
            {
                name = I18n.format("msg.universaltweaks.tweaks.pickupnotification.xp");
                italics = "";
            }
            String s2 = String.format("%s%s", italics, name);
            return new String[] {s1, " ", s2};
        }
        else return new String[] {s1};
    }

    private boolean isBlacklisted(ItemStack left)
    {
        return Arrays.asList(UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNBlacklistItem).contains(left.getItem().getRegistryName().toString());
    }

    private boolean isChangeMeaningful(ItemStack a, ItemStack b)
    {
        if (a.getCount() != b.getCount()) return true;

        if (a == b || isStackEmpty(a) && isStackEmpty(b)) return false;

        if (a.getItem() == b.getItem() && Arrays.asList(UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNBlacklistSubitem).contains(a.getItem().getRegistryName().toString())) return false; // If we are ignoring subitem changes, consider them the same.

        return !ItemStack.areItemsEqualIgnoreDurability(a, b);
    }

    private void obtainedItem(List<ChangeInfo> changeList, ItemStack item, int added)
    {
        if (added <= 0 || !UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNItemAdditions) return;

        accumulate(changeList, item, ChangeMode.OBTAINED, added, true);
    }

    private void lostItem(List<ChangeInfo> changeList, ItemStack item, int removed)
    {
        if (removed <= 0 || !UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNItemRemovals) return;

        accumulate(changeList, item, ChangeMode.LOST, removed, true);
    }

    private void accumulate(List<ChangeInfo> changeList, ItemStack stack, ChangeMode mode, int count, boolean isLocal)
    {
        if (stack.getCount() <= 0) return;

        final ComparableItem name = new ComparableItem(stack);
        ChangeInfo info = isLocal ? changeList.stream().filter(e -> e.item.equals(name)).findFirst().orElse(null) : changeList.stream().filter(e -> e.item.equals(name) && e.mode == mode).findFirst().orElse(null);
        if (info == null)
        {
            info = new ChangeInfo(name, mode, count, UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNDisplayDuration);
            changeList.add(info);
            return;
        }

        if (info.mode != mode) count = -count;

        info.count += count;
        info.ttl = UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPUNDisplayDuration;

        if (info.count < 0)
        {
            info.count = -info.count;
            info.mode = info.mode == ChangeMode.LOST ? ChangeMode.OBTAINED : ChangeMode.LOST;
        }
    }

    private enum ChangeMode
    {
        OBTAINED, LOST
    }

    private static class ChangeInfo
    {
        final ComparableItem item;
        ChangeMode mode;
        int count;
        int ttl;

        ChangeInfo(ComparableItem item, ChangeMode mode, int count, int ttl)
        {
            this.item = item;
            this.mode = mode;
            this.count = count;
            this.ttl = ttl;
        }
    }

    private static class ComparableItem
    {
        ItemStack stack;

        ComparableItem(ItemStack stack)
        {
            this.stack = stack.copy();
            this.stack.setCount(1);
        }

        @Override
        public int hashCode()
        {
            return stack.getItem().hashCode() * 31 ^ stack.getMetadata();
        }

        @Override
        public boolean equals(Object obj)
        {
            if (!(obj instanceof ComparableItem)) return false;
            ItemStack stack = ((ComparableItem) obj).stack;
            return areSameishItem(stack, this.stack);
        }
    }
}