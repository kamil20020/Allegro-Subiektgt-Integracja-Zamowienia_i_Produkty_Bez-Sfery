package pl.kamil_dywan;

import pl.kamil_dywan.api.BasicAuthApi;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public interface TestUtils {

    public static void updatePrivateStaticField(Class<?> type, String fieldName, Object newValue) {

        try {
            Field authHeaderField = type.getDeclaredField(fieldName);
            authHeaderField.setAccessible(true);

            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            Unsafe unsafe = (Unsafe) unsafeField.get(null);

            Object staticFieldBase = unsafe.staticFieldBase(authHeaderField);
            long staticFieldOffset = unsafe.staticFieldOffset(authHeaderField);

            unsafe.putObject(staticFieldBase, staticFieldOffset, newValue);
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }
}
