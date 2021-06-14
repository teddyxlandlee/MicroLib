package bilibili.teddyxlandlee.microlib.api.predicate;

import com.google.common.collect.ImmutableSet;
import net.minecraft.item.Item;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.tag.Tag;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * Although {@link ItemPredicate.Builder} is useful,
 * There is still some {@link ItemPredicate} that only
 * tests 1 item or an item set.
 */
public class SimpleItemPredicates {
    private SimpleItemPredicates() {}

    private static ItemPredicate of(@Nullable Tag<Item> tag, @Nullable Item item) {
        return new ItemPredicate(
                null, item,
                NumberRange.IntRange.ANY, NumberRange.IntRange.ANY,
                EnchantmentPredicate.ARRAY_OF_ANY,
                EnchantmentPredicate.ARRAY_OF_ANY,
                null, NbtPredicate.ANY
        );
    }

    public static ItemPredicate of(Item item) {
        return of(null, item);
    }

    public static ItemPredicate of(Set<Item> items) {
        return of(Tag.of(items), null);
    }

    public static ItemPredicate of(Item... items) {
        return of(ImmutableSet.copyOf(items));
    }

    public static ItemPredicate of(Tag<Item> tag) {
        return of(tag, null);
    }
}
