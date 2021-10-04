package io.github.teddyxlandlee.mcmod.microlib.tag;

import bilibili.teddyxlandlee.microlib.SimpleEvent;
import bilibili.teddyxlandlee.microlib.tag.TagInsertion;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.github.teddyxlandlee.mcmod.microlib.mixin.TagBuilderEntryMixin;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Insert tags / add, modify or remove items from(to) tags without data packs.
 * @see net.minecraft.tag.TagManagerLoader
 */
public class TagInsertionsImpl<E> implements TagInsertion<E> {
    public static final TagInsertionsImpl<Block> BLOCK = new TagInsertionsImpl<>("tags/blocks", Registry.BLOCK::getId);
    public static final TagInsertionsImpl<Item> ITEMS = new TagInsertionsImpl<>("tags/items", Registry.ITEM::getId);
    public static final TagInsertionsImpl<Fluid> FLUIDS = new TagInsertionsImpl<>("tags/fluids", Registry.FLUID::getId);

    private final String dataType;
    private final Function<E, Identifier> getter;
    private final SimpleEvent<TagInsertion.TagModification> events = SimpleEvent.of();

    private TagInsertionsImpl(String dataType, Function<E, Identifier> getter) {
        this.dataType = dataType;
        this.getter = getter;
        DATA_TYPE_MAP.put(dataType, this);
    }

    public void addToTag(Identifier tagId, E element) {
        events.register(map -> getOrThrow(map, tagId).add(getter.apply(element), "MicroLib Generated"));
    }

    @Override
    public void addToTag(Identifier tagId, Identifier elementTag) {
        events.register(map -> getOrThrow(map, tagId).addTag(elementTag, "MicroLib Generated"));
    }

    @Override
    public void removeFromTag(Identifier tagId, E element) {
        events.register(map -> ((TagBuilderEntryMixin) getOrThrow(map, tagId)).getEntries()
                .removeIf(trackedEntry -> {
                    final Tag.Entry entry = trackedEntry.getEntry();
                    return (entry instanceof Tag.ObjectEntry &&
                            entry.toString().equals(getter.apply(element).toString()));
                }));
    }

    @Override
    public void removeFromTag(Identifier tagId, Identifier elementTag) {
        events.register(map -> ((TagBuilderEntryMixin) getOrThrow(map, tagId)).getEntries()
                .removeIf(trackedEntry -> {
                    final Tag.Entry entry = trackedEntry.getEntry();
                    return (entry instanceof Tag.TagEntry &&
                            entry.toString().substring(1).equals(elementTag.toString()));
        }));
    }

    @Override
    public void modify(TagModification modification) {
        events.register(modification);
    }

    @Override
    public void modifySpecifiedTag(Identifier tagId, BiFunction<Tag.Builder, List<Tag.TrackedEntry>, Tag.Builder> consumer) {
        events.register(map -> {
            Tag.Builder builder = getOrThrow(map, tagId);
            map.put(tagId, consumer.apply(builder, ((TagBuilderEntryMixin) builder).getEntries()));
        });
    }

    @Override
    public void modifySpecifiedTagIfExists(Identifier tagId, BiFunction<Tag.Builder, List<Tag.TrackedEntry>, Tag.Builder> consumer) {
        events.register(map -> Optional.ofNullable(map.get(tagId)).ifPresent(builder ->
                map.put(tagId, consumer.apply(builder, ((TagBuilderEntryMixin) builder).getEntries()))));
    }

    @Override
    public void defineTag(Identifier tagId, Set<E> items) {
        events.register(map -> {
            Tag.Builder builder = Tag.Builder.create();
            for (E item : items) builder.add(getter.apply(item), "MicroLib Generated");
            map.put(tagId, builder);
        });
    }

    // -------- //

    private static final BiMap<String, TagInsertionsImpl<?>> DATA_TYPE_MAP = HashBiMap.create(3);

    public static TagInsertionsImpl<?> fromDataType(String dataType) throws IllegalArgumentException {
        return Optional.ofNullable(DATA_TYPE_MAP.get(dataType)).orElseThrow(IllegalArgumentException::new);
    }

    private Tag.Builder getOrThrow(Map<Identifier, Tag.Builder> map, Identifier key) {
        if (map.containsKey(key)) return map.get(key);
        throw new IllegalArgumentException("tag " + key + " does not exist in group " + dataType);
    }

    public SimpleEvent<TagModification> getEvents() {
        return events;
    }
}
