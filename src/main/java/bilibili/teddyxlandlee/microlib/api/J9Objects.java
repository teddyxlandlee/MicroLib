package bilibili.teddyxlandlee.microlib.api;

import static java.util.Objects.requireNonNull;

public class J9Objects {
    public static <T> T requireNonNullElse(T obj, T defaultObj) {
        return (obj != null) ? obj : requireNonNull(defaultObj, "defaultObj");
    }
}
