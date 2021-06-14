package bilibili.teddyxlandlee.microlib.api;

import com.google.common.collect.Lists;

import java.util.LinkedList;
import java.util.function.Consumer;

public class SimpleEvent<T> {
    private final LinkedList<T> container = Lists.newLinkedList();

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
}
