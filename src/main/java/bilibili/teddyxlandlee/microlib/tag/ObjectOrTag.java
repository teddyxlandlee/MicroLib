package bilibili.teddyxlandlee.microlib.tag;

import net.minecraft.tag.Tag;
import org.apiguardian.api.API;
import org.jetbrains.annotations.Nullable;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * <p>Represents an object or the tag.</p>
 * <p>Thanks to Shurlin's {@code ItemOrTag} for inspiration.</p>
 */
@SuppressWarnings("unused")
@API(status = API.Status.EXPERIMENTAL)
public class ObjectOrTag<T> {
    private final Object value;
    private final boolean isTag;

    protected ObjectOrTag(T item) {
        super();
        this.value = item;
        this.isTag = false;
    }

    protected ObjectOrTag(Tag<T> tag) {
        super();
        this.value = tag;
        this.isTag = true;
    }

    public static <O> ObjectOrTag<O> of(O o) { return new ObjectOrTag<>(o); }
    public static <N> ObjectOrTag<N> of(Tag<N> tag) { return new ObjectOrTag<>(tag); }

    public boolean isTag() {
        return isTag;
    }

    public boolean isObject() {
        return !isTag;
    }

    @SuppressWarnings("unchecked")
    public T getObject() {
        if (!isTag) return (T) value;
        throw new NoSuchElementException("value is tag");
    }

    @SuppressWarnings("unchecked")
    public Tag<T> getTag() {
        if (isTag) return (Tag<T>) value;
        throw new NoSuchElementException("value is object");
    }

    @SuppressWarnings("unchecked")
    public Optional<T> getOptionalObject() {
        if (!isTag) return Optional.ofNullable((T) value);
        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    public Optional<Tag<T>> getOptionalTag() {
        if (isTag) return Optional.ofNullable((Tag<T>) value);
        return Optional.empty();
    }

    public @Nullable T getNullableObject() {
        return getOptionalObject().orElse(null);
    }

    public @Nullable Tag<T> getNullableTag() {
        return getOptionalTag().orElse(null);
    }
}
