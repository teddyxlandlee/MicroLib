package bilibili.teddyxlandlee.microlib.util.collections;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public class AddOnlyArrayList<E> extends ArrayList<E> implements AddOnlyCollection<E> {
    public AddOnlyArrayList() { super(); }
    public AddOnlyArrayList(Collection<? extends E> c) { super(c); }
    public AddOnlyArrayList(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity) { super(initialCapacity); }

    private static UnsupportedOperationException ex() {
        return new UnsupportedOperationException("This list is read only!");
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw ex();
    }

    @Override
    public boolean remove(Object o) {
        throw ex();
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        throw ex();
    }

    @Override
    public E remove(int index) {
        throw ex();
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        throw ex();
    }

    @Override
    public E set(int index, E element) {
        throw ex();
    }
}
