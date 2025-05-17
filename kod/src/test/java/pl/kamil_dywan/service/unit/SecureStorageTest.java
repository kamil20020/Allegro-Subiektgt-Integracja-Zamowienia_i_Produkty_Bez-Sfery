package pl.kamil_dywan.service.unit;

import com.microsoft.credentialstorage.SecretStore;
import com.microsoft.credentialstorage.model.StoredCredential;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import pl.kamil_dywan.service.AppProperties;
import pl.kamil_dywan.service.SecureStorage;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class SecureStorageTest {

    private static final SecretStore<StoredCredential> secretStoreMock = Mockito.mock(SecretStore.class);

    @BeforeAll
    public static void setUp() throws Exception{

        Field field = SecureStorage.class.getDeclaredField("store");
        field.setAccessible(true);

        Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);

        Object staticFieldBase = unsafe.staticFieldBase(field);
        long staticFieldOffset = unsafe.staticFieldOffset(field);

        unsafe.putObject(staticFieldBase, staticFieldOffset, secretStoreMock);
    }

    private static void setUpCredentialsKeyPrefix(String credentialsKeyPrefix){

        try {

            Field field = SecureStorage.class.getDeclaredField("CREDENTIALS_KEY_PREFIX");
            field.setAccessible(true);

            field.set(null, credentialsKeyPrefix + "-");
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Test
    void shouldLoad() throws Exception{

        String expectedCredentialsPrefix = "expected credentials prefix";
        String expectedCredentialsPrefixKey = "secure-store.credentials-key";
        String expectedValue = expectedCredentialsPrefix + "-";

        Field field = SecureStorage.class.getDeclaredField("CREDENTIALS_KEY_PREFIX");
        field.setAccessible(true);

        try(
            MockedStatic<AppProperties> appPropertiesMock = Mockito.mockStatic(AppProperties.class);
        ){

            appPropertiesMock.when(() -> AppProperties.getProperty(any())).thenReturn(expectedCredentialsPrefix);

            SecureStorage.load();

            String gotValue = (String) field.get(null);

            assertEquals(expectedValue, gotValue);

            appPropertiesMock.verify(() -> AppProperties.getProperty(expectedCredentialsPrefixKey));
        }
    }

    @Test
    void shouldGetDoesExistWhenExist(){

        String expectedKeyPrefix = "prefix";
        String expectedKeyPostfix = "key";
        String expectedKey = "prefix-key";
        StoredCredential expectedValue = new StoredCredential("", new char[0]);

        setUpCredentialsKeyPrefix(expectedKeyPrefix);

        Mockito.when(secretStoreMock.get(any())).thenReturn(expectedValue);

        boolean doestExist = SecureStorage.doesExist(expectedKeyPostfix);

        assertTrue(doestExist);

        Mockito.verify(secretStoreMock).get(expectedKey);
    }

    @Test
    void shouldNotGetDoesExistWhenDoesNotExist(){

        String expectedKeyPrefix = "prefix";
        String expectedKeyPostfix = "key";
        String expectedKey = "prefix-key";

        setUpCredentialsKeyPrefix(expectedKeyPrefix);

        Mockito.when(secretStoreMock.get(any())).thenReturn(null);

        boolean doestExist = SecureStorage.doesExist(expectedKeyPostfix);

        assertFalse(doestExist);

        Mockito.verify(secretStoreMock).get(expectedKey);
    }

    @Test
    void shouldSaveCredentials(){

        String expectedKeyPrefix = "prefix";
        String expectedKeyPostfix = "key";
        String expectedKey = "prefix-key";

        String expectedUsername = "username";
        String expectedPassword = "password";

        setUpCredentialsKeyPrefix(expectedKeyPrefix);

        SecureStorage.saveCredentials(expectedKeyPostfix, expectedUsername, expectedPassword);

        ArgumentCaptor<StoredCredential> storedCredentialCaptor = ArgumentCaptor.forClass(StoredCredential.class);

        Mockito.verify(secretStoreMock).add(eq(expectedKey), storedCredentialCaptor.capture());

        StoredCredential gotStoredCredentials = storedCredentialCaptor.getValue();

        assertEquals(expectedUsername, gotStoredCredentials.getUsername());
        assertArrayEquals(expectedPassword.toCharArray(), gotStoredCredentials.getPassword());
    }

    @Test
    void shouldSaveCredentialsPassword(){

        String expectedKeyPrefix = "prefix";
        String expectedKeyPostfix = "key";
        String expectedKey = "prefix-key";

        String expectedPassword = "password";

        setUpCredentialsKeyPrefix(expectedKeyPrefix);

        SecureStorage.saveCredentials(expectedKeyPostfix, expectedPassword);

        ArgumentCaptor<StoredCredential> storedCredentialCaptor = ArgumentCaptor.forClass(StoredCredential.class);

        Mockito.verify(secretStoreMock).add(eq(expectedKey), storedCredentialCaptor.capture());

        StoredCredential gotStoredCredentials = storedCredentialCaptor.getValue();

        assertEquals(expectedKeyPostfix, gotStoredCredentials.getUsername());
        assertArrayEquals(expectedPassword.toCharArray(), gotStoredCredentials.getPassword());
    }

    @Test
    void shouldGetCredentialsPassword(){

        String expectedKeyPrefix = "prefix";
        String expectedKeyPostfix = "key";
        String expectedKey = "prefix-key";

        String expectedPassword = "password";

        setUpCredentialsKeyPrefix(expectedKeyPrefix);

        StoredCredential expectedStoredCredential = new StoredCredential(
            "username",
            expectedPassword.toCharArray()
        );

        Mockito.when(secretStoreMock.get(any())).thenReturn(expectedStoredCredential);

        String gotPassword = SecureStorage.getCredentialsPassword(expectedKeyPostfix);

        assertEquals(expectedPassword, gotPassword);

        Mockito.verify(secretStoreMock).get(expectedKey);
    }

    @Test
    void shouldNotGetCredentialsPasswordWhenKeyPostfixDoestNotExist(){

        String expectedKeyPrefix = "prefix";
        String expectedKeyPostfix = "key";
        String expectedKey = "prefix-key";

        setUpCredentialsKeyPrefix(expectedKeyPrefix);

        Mockito.when(secretStoreMock.get(any())).thenReturn(null);

        assertThrows(
            IllegalArgumentException.class,
            () -> SecureStorage.getCredentialsPassword(expectedKeyPostfix)
        );

        Mockito.verify(secretStoreMock).get(expectedKey);
    }

    @Test
    void shouldDelete() {

        String expectedKeyPrefix = "prefix";
        String expectedKeyPostfix = "key";
        String expectedKey = "prefix-key";

        setUpCredentialsKeyPrefix(expectedKeyPrefix);

        String expectedPassword = "password";

        StoredCredential expectedStoredCredential = new StoredCredential(
            "username",
            expectedPassword.toCharArray()
        );

        Mockito.when(secretStoreMock.get(any())).thenReturn(expectedStoredCredential);
        Mockito.when(secretStoreMock.delete(any())).thenReturn(true);

        boolean wasDeleted = SecureStorage.delete(expectedKeyPostfix);

        assertTrue(wasDeleted);

        Mockito.verify(secretStoreMock).get(expectedKey);
        Mockito.verify(secretStoreMock).delete(expectedKey);
    }

    @Test
    void shouldNotDeleteWhenKeyPostfixDoestNotExist() {

        String expectedKeyPrefix = "prefix";
        String expectedKeyPostfix = "key";
        String expectedKey = "prefix-key";

        setUpCredentialsKeyPrefix(expectedKeyPrefix);

        Mockito.when(secretStoreMock.get(any())).thenReturn(null);

        boolean wasDeleted = SecureStorage.delete(expectedKeyPostfix);

        assertFalse(wasDeleted);

        Mockito.verify(secretStoreMock).get(expectedKey);
    }
}