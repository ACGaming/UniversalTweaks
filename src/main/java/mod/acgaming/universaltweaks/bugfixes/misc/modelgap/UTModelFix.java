package mod.acgaming.universaltweaks.bugfixes.misc.modelgap;

import java.util.List;
import java.util.Set;

import org.lwjgl.util.vector.Vector3f;
import net.minecraft.client.renderer.block.model.BlockPart;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.util.EnumFacing;

import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of MehVahdJukaar
public class UTModelFix
{
    public static boolean isHorizontal(ItemModelGenerator.SpanFacing spanFacing)
    {
        return spanFacing == ItemModelGenerator.SpanFacing.DOWN || spanFacing == ItemModelGenerator.SpanFacing.UP;
    }

    public static void createOrExpandSpan(List<ItemModelGenerator.Span> listSpans, ItemModelGenerator.SpanFacing spanFacing, int pixelX, int pixelY)
    {
        int length;
        ItemModelGenerator.Span existingSpan = null;
        for (ItemModelGenerator.Span span2 : listSpans)
        {
            if (span2.getFacing() == spanFacing)
            {
                int i = isHorizontal(spanFacing) ? pixelY : pixelX;
                if (span2.getAnchor() != i) continue;
                // Skips faces with transparent pixels, so we can enlarge safely
                if (UTConfigBugfixes.MISC.MODEL_GAP.utModelGapExpansion != 0 && span2.getMax() != (!isHorizontal(spanFacing) ? pixelY : pixelX) - 1) continue;
                existingSpan = span2;
                break;
            }
        }
        length = isHorizontal(spanFacing) ? pixelX : pixelY;
        if (existingSpan == null)
        {
            int newStart = isHorizontal(spanFacing) ? pixelY : pixelX;
            listSpans.add(new ItemModelGenerator.Span(spanFacing, length, newStart));
        }
        else existingSpan.expand(length);
    }

    public static void enlargeFaces(CallbackInfoReturnable<List<BlockPart>> cir)
    {
        float inc = (float) UTConfigBugfixes.MISC.MODEL_GAP.utModelGapRecess;
        float inc2 = (float) UTConfigBugfixes.MISC.MODEL_GAP.utModelGapExpansion;
        for (BlockPart e : cir.getReturnValue())
        {
            Vector3f from = e.positionFrom;
            Vector3f to = e.positionTo;
            Set<EnumFacing> set = e.mapFaces.keySet();
            if (set.size() == 1)
            {
                EnumFacing dir = set.stream().findAny().get();
                switch (dir)
                {
                    case UP:
                    {
                        from.set(from.getX() - inc2, from.getY() - inc, from.getZ() - inc2);
                        to.set(to.getX() + inc2, to.getY() - inc, to.getZ() + inc2);
                    }
                    case DOWN:
                    {
                        from.set(from.getX() - inc2, from.getY() + inc, from.getZ() - inc2);
                        to.set(to.getX() + inc2, to.getY() + inc, to.getZ() + inc2);
                    }
                    case WEST:
                    {
                        from.set(from.getX() - inc, from.getY() + inc2, from.getZ() - inc2);
                        to.set(to.getX() - inc, to.getY() - inc2, to.getZ() + inc2);
                    }
                    case EAST:
                    {
                        from.set(from.getX() + inc, from.getY() + inc2, from.getZ() - inc2);
                        to.set(to.getX() + inc, to.getY() - inc2, to.getZ() + inc2);
                    }
                }
            }
        }
    }
}