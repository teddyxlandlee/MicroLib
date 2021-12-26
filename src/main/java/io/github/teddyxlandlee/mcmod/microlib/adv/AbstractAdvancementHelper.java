package io.github.teddyxlandlee.mcmod.microlib.adv;

import bilibili.teddyxlandlee.microlib.advancements.CommonAdvancementHelper;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Function;

public abstract class AbstractAdvancementHelper<I, P> implements CommonAdvancementHelper<I> {
    protected final Map<String, P> predicateMap = new Object2ObjectArrayMap<>();

    private final Function<I, P> single2predicate;
    @Nullable
    private final Function<Tag<I>, P> tag2predicate;
    private final Function<I, Identifier> item2id;

    protected AbstractAdvancementHelper(Function<I, P> single2predicate, @Nullable Function<Tag<I>, P> tag2predicate, Function<I, Identifier> item2id) {
        this.single2predicate = single2predicate;
        this.tag2predicate = tag2predicate;
        this.item2id = item2id;
    }

    @Override
    public void registerOne(String id, I item) {
        predicateMap.put('@' + id, single2predicate.apply(item));
    }

    @Override
    public void registerTag(Identifier id, Tag<I> items) {
        if (tag2predicate != null) {
            predicateMap.put("tag@" + id.toString(), tag2predicate.apply(items));
        } else throw new UnsupportedOperationException("Registering tags is currently not supported");
    }

    @Override
    public Identifier idFromItem(I item) {
        return item2id.apply(item);
    }
}
