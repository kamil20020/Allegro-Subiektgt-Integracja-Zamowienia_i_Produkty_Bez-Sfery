package pl.kamil_dywan;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public interface TestUtils {

    static void updatePrivateStaticField(Class<?> type, String fieldName, Object newValue) {

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

    static<T> void updatePrivateInstanceField(Class<T> type, String fieldName, T instance, Object newValue){

        try {
            Field authHeaderField = type.getDeclaredField(fieldName);
            authHeaderField.setAccessible(true);

            authHeaderField.set(instance, newValue);

        }
        catch (Exception e){

            e.printStackTrace();
        }
    }

    static<T> T getPrivateStaticField(Class<?> type, String fieldName, Class<T> returnType){

        return getPrivateInstanceField(type, fieldName, null, returnType);
    }

    static<T, K> K getPrivateInstanceField(Class<T> type, String fieldName, T instance, Class<K> returnType){

        try {
            Field authHeaderField = type.getDeclaredField(fieldName);
            authHeaderField.setAccessible(true);

            Object gotValue = authHeaderField.get(instance);

            return returnType.cast(gotValue);

        }
        catch (Exception e){

            e.printStackTrace();
        }

        return null;
    }
}
