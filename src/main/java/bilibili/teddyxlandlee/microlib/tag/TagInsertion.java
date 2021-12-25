package bilibili.teddyxlandlee.microlib.tag;

import com.google.common.collect.ImmutableSet;
import io.github.teddyxlandlee.mcmod.microlib.tag.TagInsertionsImpl;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;

@SuppressWarnings({"unused"})
public interface TagInsertion<E> {
    TagInsertion<Block> BLOCK = TagInsertionsImpl.BLOCK;
    TagInsertion<Fluid> FLUID = TagInsertionsImpl.FLUIDS;
    TagInsertion<Item>  ITEM  = TagInsertionsImpl.ITEMS;

    void addToTag(Identifier tagId, E element);
    void addToTag(Identifier tagId, Identifier elementTag);
    default void addToTag(Identifier tagId, E... elements) {
        for (E element : elements) addToTag(tagId, element);
    }
    default void addToTag(Identifier tagId, Set<E> elements) {
        for (E element : elements) addToTag(tagId, element);
    }

    void removeFromTag(Identifier tagId, E element);
    void removeFromTag(Identifier tagId, Identifier elementTag);
    default void removeFromTag(Identifier tagId, E... elements) {
        for (E element : elements) removeFromTag(tagId, element);
    }
    default void removeFromTag(Identifier tagId, Set<E> elements) {
        for (E element : elements) removeFromTag(tagId, element);
    }

    void modify(TagModification modification);

    void modifySpecifiedTag(Identifier tagId, BiFunction<Tag.Builder, List<Tag.TrackedEntry>, Tag.Builder> consumer);
    void modifySpecifiedTagIfExists(Identifier tagId, BiFunction<Tag.Builder, List<Tag.TrackedEntry>, Tag.Builder> consumer);

    void defineTag(Identifier tagId, Set<E> items);
    default void defineTag(Identifier tagId, E... items) {
        defineTag(tagId, ImmutableSet.copyOf(items));
    }

    @FunctionalInterface
    interface TagModification extends Consumer<Map<Identifier, Tag.Builder>> {
        @Override
        void accept(Map<Identifier, Tag.Builder> map);
    }
}
