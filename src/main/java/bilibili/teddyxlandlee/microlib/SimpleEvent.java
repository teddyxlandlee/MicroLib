package bilibili.teddyxlandlee.microlib;

import bilibili.teddyxlandlee.microlib.util.collections.AddOnlyArrayList;
import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

@API(status = API.Status.STABLE)
public class SimpleEvent<T> implements Iterable<T> {
    //private final List<T> container = Lists.newArrayList();
    private final List<T> container = new AddOnlyArrayList<>();

    public static <T> SimpleEvent<T> of() { return new SimpleEvent<>(); }

    public void register(T item) {
        synchronized (container) {
            container.add(item);
        }
    }

    public void forEach(Consumer<? super T> c) {
        synchronized (container) {
            container.forEach(c);
        }
    }

    @NotNull
    @Override
    public ListIterator<T> iterator() {
        synchronized (container) {
            return container.listIterator();
        }
    }
}
