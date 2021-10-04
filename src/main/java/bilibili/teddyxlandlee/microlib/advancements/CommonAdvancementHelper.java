package bilibili.teddyxlandlee.microlib.advancements;

import io.github.teddyxlandlee.mcmod.microlib.adv.AndsInsertion;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import org.apiguardian.api.API;

import java.util.Set;

/**
 * <p>This is a helper for common advancements insertions. With
 * this, you can simply register your item or item set</p>
 */
@API(status = API.Status.EXPERIMENTAL)
public interface CommonAdvancementHelper<I> {
    CommonAdvancementHelper<Item> BALANCED_DIET = AndsInsertion.BALANCED_DIET;

    void registerOne(String id, I item);

    default void registerOne(Identifier id, I item) {
        registerOne(id.toString(), item);
    }

    default void registerEach(Identifier id, I... items) {
        for (I i : items)
            registerOne(id + "^mul$$" + idFromItem(i), i);
    }

    default void registerEach(Identifier id, Set<I> items) {
        for (I i : items)
            registerOne(id + "^mul$$" + idFromItem(i), i);
    }

    void registerTag(Identifier id, Tag<I> items);


    Identifier idFromItem(I item);

}
