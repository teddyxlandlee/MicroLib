package bilibili.teddyxlandlee.microlib.predicate;

import net.minecraft.item.Item;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.tag.Tag;
import org.jetbrains.annotations.Nullable;

/**
 * Although {@link ItemPredicate.Builder} is useful,
 * There is still some {@link ItemPredicate} that only
 * tests 1 item or an item tag.
 */
public class SimpleItemPredicates {
    private SimpleItemPredicates() {}

    private static ItemPredicate of(@Nullable Tag<Item> tag, @Nullable Item item) {
        return new ItemPredicate(
                tag, item,
                NumberRange.IntRange.ANY, NumberRange.IntRange.ANY,
                EnchantmentPredicate.ARRAY_OF_ANY,
                EnchantmentPredicate.ARRAY_OF_ANY,
                null, NbtPredicate.ANY
        );
    }

    public static ItemPredicate of(Item item) {
        return of(null, item);
    }

    public static ItemPredicate of(Tag<Item> tag) {
        return of(tag, null);
    }
}