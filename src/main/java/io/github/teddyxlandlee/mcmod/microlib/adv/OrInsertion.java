package io.github.teddyxlandlee.mcmod.microlib.adv;

import bilibili.teddyxlandlee.microlib.advancements.events.AdvancementLoadingCallback;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.advancement.criterion.PlacedBlockCriterion;
import net.minecraft.block.Block;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Function;

public class OrInsertion<I, P> extends AbstractAdvancementHelper<I, P> {
    public static final OrInsertion<Block, BlockPredicate> PLANT_SEED;

    public OrInsertion(@NotNull Identifier targetAdv,
                       Function<P, CriterionConditions> predicate2conditions,
                       Function<I, P> single2predicate,
                       @Nullable Function<Tag<I>, P> tag2predicate,
                       Function<I, Identifier> item2id) {
        super(single2predicate, tag2predicate, item2id);

        AdvancementLoadingCallback.EVENT.register((id, json, task, acc) -> {
            if (!id.equals(targetAdv)) return;
            int predicateMapSize = predicateMap.size();
            String[] oldReqList = acc.getRequirements()[0];
            int oldLen = oldReqList.length;
            String[] newReqList = new String[oldLen + predicateMapSize];
            System.arraycopy(oldReqList, 0, newReqList, predicateMapSize, oldLen);

            int i = 0;
            for (Map.Entry<String, P> entry : predicateMap.entrySet()) {
                String reqId = "microlib:req~$" + targetAdv + '%' + entry.getKey();
                task.criterion(reqId, predicate2conditions.apply(entry.getValue()));
                newReqList[i++] = reqId;
            } acc.getRequirements()[0] = newReqList;
        });
    }

    static {
        PLANT_SEED = new OrInsertion<>(
                new Identifier("husbandry/plant_seed"),
                blockPredicate -> new PlacedBlockCriterion.Conditions(
                        EntityPredicate.Extended.EMPTY,

                )
        )
    }
}
