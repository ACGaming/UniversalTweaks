package mod.acgaming.universaltweaks.mods.cyclic.mixin;

import java.util.ArrayList;

import com.lothrazar.cyclicmagic.data.BlockPosDim;
import com.lothrazar.cyclicmagic.item.enderbook.ButtonClose;
import com.lothrazar.cyclicmagic.item.enderbook.ButtonWaypointDelete;
import com.lothrazar.cyclicmagic.item.enderbook.ButtonWaypointNew;
import com.lothrazar.cyclicmagic.item.enderbook.ButtonWaypointTeleport;
import com.lothrazar.cyclicmagic.item.enderbook.GuiEnderBook;
import com.lothrazar.cyclicmagic.item.enderbook.ItemEnderBook;
import com.lothrazar.cyclicmagic.util.UtilChat;
import com.lothrazar.cyclicmagic.util.UtilWorld;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiEnderBook.class, remap = false)
public abstract class UTGuiEnderBookPaginationMixin extends GuiScreen
{
    @Unique private static final int ut$PAGE_PREV_ID = 91000;
    @Unique private static final int ut$PAGE_NEXT_ID = 91001;
    @Unique private static final int ut$MAX_NAME_LEN = 20;

    @Unique private int ut$page = 0;
    @Unique private int ut$pageCount = 1;

    @Shadow public static int buttonIdNew;
    @Shadow GuiButton buttonNew;
    @Shadow GuiTextField txtNew;
    @Shadow private ItemStack bookStack;
    @Shadow private ButtonWaypointTeleport btnBack;
    // func_73866_w_ - initGui
    @Inject(method = "func_73866_w_", at = @At("HEAD"), cancellable = true, remap = false)
    private void ut$initPaginatedGui(CallbackInfo ci)
    {
        ci.cancel();

        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();

        EntityPlayer player = this.mc.player;
        if (player == null)
        {
            return;
        }

        if (this.bookStack == null)
        {
            this.bookStack = ItemEnderBook.getPlayersBook(player);
        }

        if (this.bookStack != null && !this.bookStack.hasTagCompound())
        {
            this.bookStack.setTagCompound(new NBTTagCompound());
        }

        String previousName = this.txtNew == null ? null : this.txtNew.getText();

        int buttonID = 0;
        int w = 70;
        int h = 20;
        int yPad = 1;
        int deleteWidth = 20;
        int rowPad = 8;

        buttonIdNew = buttonID++;
        this.buttonNew = new ButtonWaypointNew(buttonIdNew, this.width / 2 - w, 20, w, h, buttonIdNew);
        this.addButton(this.buttonNew);

        ButtonClose buttonClose = new ButtonClose(9999, this.width / 2 - w - 50, 20);
        this.addButton(buttonClose);

        this.txtNew = new GuiTextField(buttonID++, this.fontRenderer, this.buttonNew.x + this.buttonNew.width + 10, this.buttonNew.y, w, h);
        this.txtNew.setMaxStringLength(ut$MAX_NAME_LEN);
        this.txtNew.setText(previousName == null ? player.getEntityWorld().getBiome(player.getPosition()).getBiomeName() : previousName);
        this.txtNew.setFocused(true);

        this.btnBack = new ButtonWaypointTeleport(
            GuiEnderBook.BACK_BTN_ID,
            this.txtNew.x + this.txtNew.width + 8,
            buttonClose.y,
            w / 2,
            h,
            UtilChat.lang("gui.enderbook.back"),
            GuiEnderBook.BACK_BTN_ID
        );
        this.addButton(this.btnBack);
        this.btnBack.enabled = ut$getBackLocation() != null;

        ArrayList<BlockPosDim> list = this.bookStack == null ? new ArrayList<>() : ItemEnderBook.getLocations(this.bookStack);

        if (this.bookStack != null && list.size() >= ItemEnderBook.maximumSaved)
        {
            this.buttonNew.enabled = false;
        }

        int waypointsPerPage = Math.max(1, UTConfigMods.CYCLIC.utEnderBookWaypointsPerPage);
        this.ut$pageCount = Math.max(1, (list.size() + waypointsPerPage - 1) / waypointsPerPage);

        if (this.ut$page >= this.ut$pageCount)
        {
            this.ut$page = this.ut$pageCount - 1;
        }
        if (this.ut$page < 0)
        {
            this.ut$page = 0;
        }

        int start = this.ut$page * waypointsPerPage;
        int end = Math.min(start + waypointsPerPage, list.size());

        int yStart = 45;
        int xStart = this.width / 10;
        int x = xStart;
        int y = yStart;

        for (int i = start; i < end; i++)
        {
            int pageIndex = i - start;
            BlockPosDim loc = list.get(i);
            String buttonText = loc.getDisplay() == null ? UtilChat.lang("gui.enderbook.go") : loc.getDisplay();

            if (pageIndex % ItemEnderBook.BTNS_PER_COLUMN == 0)
            {
                x += w + deleteWidth + rowPad;
                y = yStart;
            }
            else
            {
                y += h + yPad;
            }

            ButtonWaypointTeleport btn = new ButtonWaypointTeleport(buttonID++, x, y, w, h, buttonText, loc.id);
            BlockPos toPos = loc.toBlockPos();
            int distance = (int) UtilWorld.distanceBetweenHorizontal(toPos, player.getPosition());

            if (loc.getDimension() == player.dimension)
            {
                btn.enabled = true;
                btn.addTooltipLine(loc.coordsDisplay());

                if (distance > 0)
                {
                    btn.addTooltipLine(UtilChat.lang("button.waypoint.distance") + " " + distance);
                }

                int cost = ItemEnderBook.getExpCostPerTeleport(player, this.bookStack, loc.id);
                if (cost > 0)
                {
                    btn.addTooltipLine(UtilChat.lang("button.waypoint.cost") + " " + cost);
                }
            }
            else
            {
                btn.enabled = false;
                btn.addTooltipLine(UtilChat.lang("button.waypoint.dimension") + " " + loc.getDimension());
            }

            this.buttonList.add(btn);

            GuiButton del = new ButtonWaypointDelete(buttonID++, x - deleteWidth - 2, y, deleteWidth, h, "X", loc.id);
            this.buttonList.add(del);
        }

        if (this.ut$pageCount > 1)
        {
            GuiButton prev = new GuiButton(ut$PAGE_PREV_ID, this.width / 2 - 100, this.height - 26, 70, h, "<");
            GuiButton next = new GuiButton(ut$PAGE_NEXT_ID, this.width / 2 + 30, this.height - 26, 70, h, ">");

            prev.enabled = this.ut$page > 0;
            next.enabled = this.ut$page + 1 < this.ut$pageCount;

            this.addButton(prev);
            this.addButton(next);
        }
    }

    // func_146284_a - actionPerformed
    @Inject(method = "func_146284_a", at = @At("HEAD"), cancellable = true, remap = false)
    private void ut$handlePageButtons(GuiButton button, CallbackInfo ci)
    {
        if (button.id == ut$PAGE_PREV_ID)
        {
            if (this.ut$page > 0)
            {
                this.ut$page--;
                this.initGui();
            }
            ci.cancel();
        }
        else if (button.id == ut$PAGE_NEXT_ID)
        {
            if (this.ut$page + 1 < this.ut$pageCount)
            {
                this.ut$page++;
                this.initGui();
            }
            ci.cancel();
        }
    }

    // func_73863_a - drawScreen
    @Inject(method = "func_73863_a", at = @At("TAIL"), remap = false)
    private void ut$drawPageIndicator(int mouseX, int mouseY, float partialTicks, CallbackInfo ci)
    {
        if (this.ut$pageCount > 1)
        {
            this.drawCenteredString(
                this.fontRenderer,
                I18n.format("gui.universaltweaks.cyclic.ender_book.page", this.ut$page + 1, this.ut$pageCount),
                this.width / 2,
                this.height - 40,
                0xFFFFFF
            );
        }
    }

    @Unique
    private BlockPosDim ut$getBackLocation()
    {
        if (this.bookStack == null || !this.bookStack.hasTagCompound())
        {
            return null;
        }

        String csv = this.bookStack.getTagCompound().getString("location_back");
        if (csv == null || csv.isEmpty())
        {
            return null;
        }

        return new BlockPosDim(csv);
    }
}