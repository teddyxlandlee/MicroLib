package bilibili.teddyxlandlee.microlib.predicate;

import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import org.apiguardian.api.API;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

@API(status = API.Status.DEPRECATED)
public class ItemSetPredicate extends ItemPredicate {
    //private final @Nullable Set<Item> items;

    public ItemSetPredicate(@Nullable Set<Item> items) {
        this(items, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, EnchantmentPredicate.ARRAY_OF_ANY,
                EnchantmentPredicate.ARRAY_OF_ANY, null, NbtPredicate.ANY);
    }

    public ItemSetPredicate(@Nullable Set<Item> items,
                            NumberRange.IntRange count,
                            NumberRange.IntRange durability,
                            EnchantmentPredicate[] enchantments,
                            EnchantmentPredicate[] storedEnchantments,
                            @Nullable Potion potion,
                            NbtPredicate nbt) {
        super(null, items, count, durability, enchantments, storedEnchantments, potion, nbt);
        //this.items = items;
    }

    public static ItemSetPredicate of(@Nullable Set<Item> items) {
        return new ItemSetPredicate(items);
    }

    public static ItemSetPredicate of(@Nullable Set<Item> items,
                                      NumberRange.IntRange count,
                                      NumberRange.IntRange durability,
                                      EnchantmentPredicate[] enchantments,
                                      EnchantmentPredicate[] storedEnchantments,
                                      @Nullable Potion potion,
                                      NbtPredicate nbt) {
        return new ItemSetPredicate(items, count, durability, enchantments, storedEnchantments, potion, nbt);
    }
}
