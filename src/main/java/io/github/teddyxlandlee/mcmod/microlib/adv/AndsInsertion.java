package io.github.teddyxlandlee.mcmod.microlib.adv;

import bilibili.teddyxlandlee.microlib.advancements.events.AdvancementLoadingCallback;
import bilibili.teddyxlandlee.microlib.predicate.SimpleItemPredicates;
import net.minecraft.advancement.criterion.ConsumeItemCriterion;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.advancement.criterion.LocationArrivalCriterion;
import net.minecraft.item.Item;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Function;

public class AndsInsertion<I, P> extends AbstractAdvancementHelper<I, P> {
    public static final AndsInsertion<Item, ItemPredicate> BALANCED_DIET;
    public static final AndsInsertion<RegistryKey<Biome>, LocationPredicate> ADVENTURING_TIME;

    AndsInsertion(@NotNull Identifier targetAdv,
                  Function<P, CriterionConditions> predicate2conditions,
                  Function<I, P> single2predicate,
                  @Nullable Function<Tag<I>, P> tag2predicate,
                  Function<I, Identifier> item2id) {
        super(single2predicate, tag2predicate, item2id);

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

    static {
        BALANCED_DIET = new AndsInsertion<>(
                new Identifier("husbandry/balanced_diet"),
                itemPredicate -> new ConsumeItemCriterion.Conditions(EntityPredicate.Extended.EMPTY, itemPredicate),
                SimpleItemPredicates::of, SimpleItemPredicates::of,
                Registry.ITEM::getId
        );
        ADVENTURING_TIME = new AndsInsertion<>(
                new Identifier("adventure/adventuring_time"),
                locationPredicate -> new LocationArrivalCriterion.Conditions(
                        new Identifier("location"), EntityPredicate.Extended.EMPTY, locationPredicate),
                LocationPredicate::biome, null,
                RegistryKey::getValue
        );
    }
}
