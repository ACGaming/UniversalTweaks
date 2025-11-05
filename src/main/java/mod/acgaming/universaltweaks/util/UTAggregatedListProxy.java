package mod.acgaming.universaltweaks.util;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.AbstractList;
import java.util.HashSet;
import java.util.Set;

public class UTAggregatedListProxy extends AbstractList<ItemStack> {
    private final Multiset<Key> internal;

    public UTAggregatedListProxy() {
        this.internal = HashMultiset.create();
    }

    // Represent an item key by item + meta only, ignoring NBT. This mirrors Botania's recipe checks.
    private static final class Key {
        final Item item;
        final int meta;

        Key(Item item, int meta) {
            this.item = item;
            this.meta = meta;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Key key = (Key) o;

            return meta == key.meta && item == key.item;
        }

        @Override
        public int hashCode() {
            int result = System.identityHashCode(item);
            result = 31 * result + meta;

            return result;
        }
    }

    private static Key keyOf(ItemStack stack) {
        return new Key(stack.getItem(), stack.getItemDamage());
    }

    private static ItemStack stackOf(Key key) {
        return new ItemStack(key.item, 1, key.meta);
    }

    public Set<Multiset.Entry<ItemStack>> getPairs() {
        Set<Multiset.Entry<ItemStack>> out = new HashSet<>();
        for (Multiset.Entry<Key> e : internal.entrySet()) {
            out.add(new SimpleEntry(stackOf(e.getElement()), e.getCount()));
        }
        return out;
    }

    public int getCount(ItemStack stack) {
        if (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
            int total = 0;
            Item item = stack.getItem();
            for (Multiset.Entry<Key> e : internal.entrySet()) {
                if (e.getElement().item == item) total += e.getCount();
            }

            return total;
        }

        return internal.count(keyOf(stack));
    }

    public void addCount(ItemStack stack, int count) {
        if (count <= 0) return;

        internal.add(keyOf(stack), count);
    }

    public int removeCount(ItemStack stack, int count) {
        if (count <= 0) return 0;

        if (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
            // Remove across all metas for this item
            int remaining = count;
            Item item = stack.getItem();

            // Iterate over a snapshot to avoid concurrent modification issues when removing
            for (Multiset.Entry<Key> e : new HashSet<>(internal.entrySet())) {
                if (e.getElement().item != item) continue;

                int toRemove = Math.min(e.getCount(), remaining);
                if (toRemove > 0) {
                    internal.remove(e.getElement(), toRemove);
                    remaining -= toRemove;

                    if (remaining == 0) break;
                }
            }

            return count - remaining;
        }

        Key key = keyOf(stack);
        int prev = internal.count(key);

        if (prev == 0) return 0;

        int toRemove = Math.min(count, prev);
        internal.remove(key, toRemove);

        return toRemove;
    }

    @Override
    public boolean add(ItemStack stack) {
        int c = stack.getCount();
        if (c <= 0) return false;

        internal.add(keyOf(stack), c);

        return true;
    }

    @Override
    public ItemStack get(int index) {
        if (index < 0) throw new IndexOutOfBoundsException("Index: " + index);

        int passed = 0;
        for (Multiset.Entry<Key> e : internal.entrySet()) {
            int cnt = e.getCount();
            if (index < passed + cnt) {
                ItemStack out = stackOf(e.getElement());
                out.setCount(1);

                return out;
            }

            passed += cnt;
        }

        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + passed);
    }

    @Override
    public int size() {
        return internal.size();
    }

    @Override
    public void clear() {
        internal.clear();
    }

    // Simple immutable entry implementation to expose ItemStack view of the underlying multiset
    private static final class SimpleEntry implements Multiset.Entry<ItemStack> {
        private final ItemStack element;
        private final int count;

        SimpleEntry(ItemStack element, int count) {
            this.element = element;
            this.count = count;
        }

        @Override
        public ItemStack getElement() {
            return element;
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SimpleEntry that = (SimpleEntry) o;

            return count == that.count && ItemStack.areItemsEqual(element, that.element) && ItemStack.areItemStackTagsEqual(element, that.element);
        }

        @Override
        public int hashCode() {
            int result = System.identityHashCode(element.getItem());
            result = 31 * result + element.getItemDamage();
            result = 31 * result + count;

            return result;
        }
    }
}