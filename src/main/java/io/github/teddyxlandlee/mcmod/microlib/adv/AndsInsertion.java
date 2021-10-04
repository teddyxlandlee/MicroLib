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

import java.util.Map;
import java.util.function.Function;

public class AndsInsertion<I, P> implements CommonAdvancementHelper<I> {
    public static final AndsInsertion<Item, ItemPredicate> BALANCED_DIET;


    /** @see it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap#put(Object, Object)  */
    private final Map<String, P> predicateMap = new Object2ObjectArrayMap<>();

    //private final Function<P, CriterionConditions> predicate2conditions;
    private final Function<I, P> single2predicate;
    private final Function<Tag<I>, P> tag2predicate;
    private final Function<I, Identifier> item2id;

    AndsInsertion(@NotNull Identifier targetAdv,
                  Function<P, CriterionConditions> predicate2conditions,
                  Function<I, P> single2predicate,
                  Function<Tag<I>, P> tag2predicate,
                  Function<I, Identifier> item2id) {
        //this.predicate2conditions = predicate2conditions;
        this.single2predicate = single2predicate;
        this.tag2predicate = tag2predicate;
        this.item2id = item2id;

        AdvancementLoadingCallback.EVENT.register((id, json, task, acc) -> {
            if (!id.equals(targetAdv)) return;
            int predicateMapSize = predicateMap.size();
            String[][] oldReq = acc.getRequirements();
            int oldLen = oldReq.length;
            String[][] newReq = new String[oldLen + predicateMapSize][];
            System.arraycopy(oldReq, 0, newReq, predicateMapSize, oldLen);

            int i = 0;
            for (Map.Entry<String, P> entry : predicateMap.entrySet()) {
                String reqId = "microlib:req~$" + targetAdv + '%' + entry.getKey();
                task.criterion(reqId, predicate2conditions.apply(entry.getValue()));
                newReq[i++] = new String[] { reqId };
            } acc.setRequirements(newReq);
        });
    }

    @Override
    public void registerOne(String id, I item) {
        predicateMap.put('@' + id, single2predicate.apply(item));
    }

    @Override
    public void registerTag(Identifier id, Tag<I> items) {
        predicateMap.put("tag@" + id.toString(), tag2predicate.apply(items));
    }

    @Override
    public Identifier idFromItem(I item) {
        return item2id.apply(item);
    }

    static {
        BALANCED_DIET = new AndsInsertion<>(
                new Identifier("husbandry/balanced_diet"),
                itemPredicate -> new ConsumeItemCriterion.Conditions(EntityPredicate.Extended.EMPTY, itemPredicate),
                SimpleItemPredicates::of, SimpleItemPredicates::of,
                Registry.ITEM::getId
        );
    }
}
