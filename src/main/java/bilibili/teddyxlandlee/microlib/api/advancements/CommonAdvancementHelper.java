package bilibili.teddyxlandlee.microlib.api.advancements;

import bilibili.teddyxlandlee.microlib.impl.advancements.CommonAdvancementHelperImpl;
import net.minecraft.advancement.criterion.ConsumeItemCriterion;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

import java.util.Set;

// TODO finish javadoc
public interface CommonAdvancementHelper<E extends CriterionConditions> {
    CommonAdvancementHelper<ConsumeItemCriterion.Conditions> BALANCED_DIET = CommonAdvancementHelperImpl.BALANCED_DIET;

    void registerOne(Identifier id, Item item);

    void registerEach(Identifier id, Item... items);
    void registerEach(Identifier id, Set<Item> items);
    void registerEach(Identifier id, Tag<Item> items);

    void registerTag(Identifier id, Item... items);
    void registerTag(Identifier id, Set<Item> items);
    void registerTag(Identifier id, Tag<Item> items);
}
