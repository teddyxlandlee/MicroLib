package io.github.teddyxlandlee.mcmod.microlib.adv;

import bilibili.teddyxlandlee.microlib.advancements.CommonAdvancementHelper;
import bilibili.teddyxlandlee.microlib.advancements.events.AdvancementLoadingCallback;
import bilibili.teddyxlandlee.microlib.predicate.SimpleItemPredicates;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.advancement.criterion.ConsumeItemCriterion;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.item.Item;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class CommonAdvancementHelperImpl<E extends CriterionConditions> implements CommonAdvancementHelper<E> {
    private final Map<ItemPredicate, String> itemPredicateMap = new Object2ObjectArrayMap<>();

    public static final CommonAdvancementHelperImpl<ConsumeItemCriterion.Conditions> BALANCED_DIET;

    public CommonAdvancementHelperImpl(@NotNull Identifier target, Function<ItemPredicate, E> function) {
        AdvancementLoadingCallback.EVENT.register((id, json, task, acc) -> {
            if (!id.equals(target)) return;
            int predicateMapSize = itemPredicateMap.size();
            String[][] oldReq = acc.getRequirements(); int l = oldReq.length;
            String[][] newReq = new String[l + predicateMapSize][];
            System.arraycopy(oldReq, 0, newReq, predicateMapSize, l);

            int i = 0;
            for (Iterator<Map.Entry<ItemPredicate, String>> iterator = itemPredicateMap.entrySet().iterator();
                 iterator.hasNext(); ++i) {
                Map.Entry<ItemPredicate, String> entry = iterator.next();
                String reqId = reqId(entry.getValue(), id);
                task.criterion(reqId, function.apply(entry.getKey()));
                newReq[i] = new String[] { reqId };
            } acc.setRequirements(newReq);
        });
    }

    @Override
    public void registerOne(Identifier id, Item item) {
        itemPredicateMap.put(SimpleItemPredicates.of(item), id.toString());
    }

    private static String multiId(Identifier origin, Identifier item) {
        return origin + "@mul$$" + item;
    }

    @Override
    public void registerEach(Identifier id, Item... items) {
        for (Item item : items)
            itemPredicateMap.put(SimpleItemPredicates.of(item),
                    multiId(id, Registry.ITEM.getId(item)));
    }

    @Override
    public void registerEach(Identifier id, Set<Item> items) {
        for (Item item : items)
            itemPredicateMap.put(SimpleItemPredicates.of(item),
                    multiId(id, Registry.ITEM.getId(item)));
    }

    @Override
    public void registerEach(Identifier id, Tag<Item> items) {
        for (Item item : items.values())
            itemPredicateMap.put(SimpleItemPredicates.of(item),
                    multiId(id, Registry.ITEM.getId(item)));
    }

    @Override
    public void registerTag(Identifier id, Item... items) {
        itemPredicateMap.put(SimpleItemPredicates.of(items), id.toString());
    }

    @Override
    public void registerTag(Identifier id, Set<Item> items) {
        itemPredicateMap.put(SimpleItemPredicates.of(items), id.toString());
    }

    @Override
    public void registerTag(Identifier id, Tag<Item> items) {
        itemPredicateMap.put(SimpleItemPredicates.of(items), id.toString());
    }

    static {
        BALANCED_DIET = new CommonAdvancementHelperImpl<>(
                new Identifier("husbandry/balanced_diet"),
                itemPredicate -> new ConsumeItemCriterion.Conditions(EntityPredicate.Extended.EMPTY, itemPredicate)
        );
    }

    private static String reqId(String userId, Identifier targetId) {
        return "microlib:req$$" + targetId + '$' + userId;
    }
}
