package bilibili.teddyxlandlee.microlib;

import org.apiguardian.api.API;

import static java.util.Objects.requireNonNull;

@API(status = API.Status.DEPRECATED)
@Deprecated
@SuppressWarnings("unused")
public class J9Objects {
    public static <T> T requireNonNullElse(T obj, T defaultObj) {
        return (obj != null) ? obj : requireNonNull(defaultObj, "defaultObj");
    }
}
