package bilibili.teddyxlandlee.microlib.predicate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.util.registry.Registry;
import org.apiguardian.api.API;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

@API(status = API.Status.EXPERIMENTAL)
public class ItemSetPredicate extends ItemPredicate {
    private final @Nullable Set<Item> items;

    public ItemSetPredicate(@Nullable Set<Item> items) {
        this.items = items;
    }

    public ItemSetPredicate(@Nullable Set<Item> items,
                            NumberRange.IntRange count,
                            NumberRange.IntRange durability,
                            EnchantmentPredicate[] enchantments,
                            EnchantmentPredicate[] storedEnchantments,
                            @Nullable Potion potion,
                            NbtPredicate nbt) {
        super(null, null, count, durability, enchantments, storedEnchantments, potion, nbt);
        this.items = items;
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

    @Override
    public boolean test(ItemStack stack) {
        if (items != null && !items.contains(stack.getItem())) return false;
        return super.test(stack);
    }

    @Override
    public JsonElement toJson() {
        JsonElement jsonElement = super.toJson();
        if (items == null || !jsonElement.isJsonObject()) return jsonElement;
        JsonObject obj = jsonElement.getAsJsonObject();
        JsonArray arr = new JsonArray();
        for (Item item : items)
            arr.add(Registry.ITEM.getId(item).toString());
        obj.add("items", arr);
        return obj;
    }
}
