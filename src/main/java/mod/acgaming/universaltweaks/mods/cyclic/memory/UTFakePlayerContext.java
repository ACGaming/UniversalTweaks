package mod.acgaming.universaltweaks.mods.cyclic.memory;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import javax.annotation.Nonnull;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;

import com.google.common.base.Preconditions;

import org.jetbrains.annotations.Nullable;

public class UTFakePlayerContext extends WeakReference<FakePlayer>
{
    @Nullable
    private final WeakReference<WorldServer> worldRef;
    @Nonnull
    private final BlockPos pos;

    private UTFakePlayerContext(FakePlayer referent)
    {
        super(referent);
        worldRef = null;
        pos = BlockPos.ORIGIN;
    }

    private UTFakePlayerContext(FakePlayer referent, ReferenceQueue<? super FakePlayer> q)
    {
        super(referent, q);
        worldRef = null;
        pos = BlockPos.ORIGIN;
    }

    private UTFakePlayerContext(FakePlayer referent, @Nonnull WorldServer world, @Nonnull BlockPos pos)
    {
        super(referent);
        this.worldRef = new WeakReference<>(world);
        this.pos = pos;
    }

    /**
     * Wraps a weak reference to a fake player with world context.
     *
     * @param fakePlayer the weak reference to the fake player
     * @param world      the world
     * @param pos        position in the world
     * @return A weak reference to the fake player that sets the world context when calling {@link #get()}.
     */
    public static UTFakePlayerContext wrap(WeakReference<FakePlayer> fakePlayer, World world, BlockPos pos)
    {
        Preconditions.checkArgument(world instanceof WorldServer);

        return new UTFakePlayerContext(fakePlayer.get(), (WorldServer) world, pos);
    }

    @Nullable
    @Override
    public FakePlayer get()
    {
        FakePlayer fakePlayer = super.get();
        if (fakePlayer != null && worldRef != null)
        {
            WorldServer world = worldRef.get();
            if (world != null)
            {
                setContext(fakePlayer, world, pos);
            }
        }
        return fakePlayer;
    }

    /**
     * Set the world context for a fake player.
     * Always call this before using the fake player.
     *
     * @param world the world
     * @param pos   position in the world
     */
    public static void setContext(FakePlayer fakePlayer, WorldServer world, BlockPos pos)
    {
        fakePlayer.setWorld(world);
        fakePlayer.interactionManager.setWorld(world);
        fakePlayer.posX = pos.getX();
        fakePlayer.posY = pos.getY();
        fakePlayer.posZ = pos.getZ();
    }
}
