package pl.kamil_dywan.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kamil_dywan.api.BasicAuthApi;
import pl.kamil_dywan.api.BearerAuthApi;
import pl.kamil_dywan.external.allegro.own.EncryptedAllegroLoginDetails;
import pl.kamil_dywan.file.read.FileReader;
import pl.kamil_dywan.file.read.JSONFileReader;
import sun.misc.Unsafe;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @ParameterizedTest
    @CsvSource(value = {
        "true, true",
        "false, false",
    })
    void shouldGetIsUserLogged(boolean expectedResult, boolean bearerResult) {

        try(
            MockedStatic<BearerAuthApi> bearerAuthApiMocked = Mockito.mockStatic(BearerAuthApi.class);
        ){

            bearerAuthApiMocked.when(() -> BearerAuthApi.isUserLogged()).thenReturn(bearerResult);

            boolean gotResult = authService.isUserLogged();

            assertEquals(expectedResult, gotResult);

            bearerAuthApiMocked.verify(() -> BearerAuthApi.isUserLogged());
        }
    }

    @ParameterizedTest
    @CsvSource(value = {
        "true, true",
        "false, false",
    })
    void shouldGetDoesUserPassedFirstLoginToApp(boolean expectedResult, boolean secureStorageValue) {

        try(
            MockedStatic<SecureStorage> secureStorageMock = Mockito.mockStatic(SecureStorage.class);
        ){

            secureStorageMock.when(() -> SecureStorage.doesExist(any())).thenReturn(secureStorageValue);

            boolean gotResult = authService.doesUserPassedFirstLoginToApp();

            assertEquals(expectedResult, gotResult);

            secureStorageMock.verify(() -> SecureStorage.doesExist(BasicAuthApi.ALLEGRO_SECRET_POSTFIX));
        }
    }

    @Test
    public void shouldInitWhenItIsFirstLoginOfTheUser() throws URISyntaxException, IOException, NoSuchFieldException, IllegalAccessException {

        FileReader<EncryptedAllegroLoginDetails> fileReaderMock = Mockito.mock(FileReader.class);

        Field field = AuthService.class.getDeclaredField("allegroLoginDetailsFileReader");
        field.setAccessible(true);

        Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);

        Object staticFieldBase = unsafe.staticFieldBase(field);
        long staticFieldOffset = unsafe.staticFieldOffset(field);

        unsafe.putObject(staticFieldBase, staticFieldOffset, fileReaderMock);

        String authDataPath = "./auth-data.json";

        EncryptedAllegroLoginDetails expectedEncryptedAllegroLoginDetails = new EncryptedAllegroLoginDetails(
            "key",
            "key hash",
            "secret"
        );

        Field encryptedAllegroLoginDetailsField = AuthService.class.getDeclaredField("encryptedAllegroLoginDetails");
        encryptedAllegroLoginDetailsField.setAccessible(true);

        Mockito.when(fileReaderMock.loadFromOutside(any())).thenReturn(expectedEncryptedAllegroLoginDetails);

        try(
            MockedStatic<SecureStorage> secureStorageMock = Mockito.mockStatic(SecureStorage.class);
        ){

            secureStorageMock.when(() -> SecureStorage.doesExist(any())).thenReturn(false);

            EncryptedAllegroLoginDetails assignedEncryptedAllegroLoginDetails = (EncryptedAllegroLoginDetails) encryptedAllegroLoginDetailsField.get(authService);

            assertEquals(expectedEncryptedAllegroLoginDetails, assignedEncryptedAllegroLoginDetails);

            boolean didSkipRemovingAuthData = new File(authDataPath).exists();

            assertFalse(didSkipRemovingAuthData);

            secureStorageMock.verify(() -> SecureStorage.doesExist(BasicAuthApi.ALLEGRO_SECRET_POSTFIX));
        }

        Mockito.verify(fileReaderMock).loadFromOutside(authDataPath);
    }

    @Test
    public void shouldInitWhenUserWasLoggedInForTheFirstTime() throws NoSuchFieldException, IllegalAccessException {

        String authDataPath = "./auth-data.json";

        Field encryptedAllegroLoginDetailsField = AuthService.class.getDeclaredField("encryptedAllegroLoginDetails");
        encryptedAllegroLoginDetailsField.setAccessible(true);

        try(
            MockedStatic<SecureStorage> secureStorageMock = Mockito.mockStatic(SecureStorage.class);
        ){

            secureStorageMock.when(() -> SecureStorage.doesExist(any())).thenReturn(true);

            authService.init();

            EncryptedAllegroLoginDetails assignedEncryptedAllegroLoginDetails = (EncryptedAllegroLoginDetails) encryptedAllegroLoginDetailsField.get(authService);

            assertNull(assignedEncryptedAllegroLoginDetails);

            boolean didSkipRemovingAuthData = new File(authDataPath).exists();

            assertTrue(didSkipRemovingAuthData);

            secureStorageMock.verify(() -> SecureStorage.doesExist(BasicAuthApi.ALLEGRO_SECRET_POSTFIX));
        }
    }

    @Test
    void shouldInitAllegroSecret() throws NoSuchFieldException, IllegalAccessException {

        Field encryptedAllegroLoginDetailsField = AuthService.class.getDeclaredField("encryptedAllegroLoginDetails");
        encryptedAllegroLoginDetailsField.setAccessible(true);

        EncryptedAllegroLoginDetails expectedEncryptedAllegroLoginDetails = new EncryptedAllegroLoginDetails(
            "encrypted key",
            "key hash",
            "encrypted secret"
        );

        encryptedAllegroLoginDetailsField.set(authService, expectedEncryptedAllegroLoginDetails);

        String expectedGotDecryptedAes = "got decrypted aes";
        byte[] expectedGotDecryptedAesHashArr = expectedEncryptedAllegroLoginDetails.getKeyHash().getBytes(StandardCharsets.UTF_8);
        String expectedGotAllegroSecret = "got allegro secret";

        try(
            MockedStatic<SecureStorage> secureStorageMock = Mockito.mockStatic(SecureStorage.class);
            MockedStatic<SecurityService> securityServiceMock = Mockito.mockStatic(SecurityService.class);
            MockedStatic<BasicAuthApi> basicAuthApiMock = Mockito.mockStatic(BasicAuthApi.class);
        ){

            secureStorageMock.when(() -> SecureStorage.doesExist(any())).thenReturn(false);
            securityServiceMock.when(() -> SecurityService.decryptAes(any(), any())).thenReturn(expectedGotDecryptedAes);
            securityServiceMock.when(() -> SecurityService.hashSha(any())).thenReturn(expectedGotDecryptedAesHashArr);
            securityServiceMock.when(() -> SecurityService.decryptAes(any(), any())).thenReturn(expectedGotAllegroSecret);

            authService.initAllegroSecret("pasword");

            secureStorageMock.verify(() -> SecureStorage.doesExist(BasicAuthApi.ALLEGRO_SECRET_POSTFIX));
//            securityServiceMock.verify(() -> SecurityService.decryptAes(any(), eq(expectedGotDecryptedAes)));
//            securityServiceMock.verify(() -> SecurityService.hashSha(expectedGotDecryptedAes));
//            securityServiceMock.verify(() -> SecurityService.decryptAes(any(), eq(expectedEncryptedAllegroLoginDetails.getSecret())));
            secureStorageMock.verify(() -> SecureStorage.saveCredentials(BasicAuthApi.ALLEGRO_SECRET_POSTFIX, expectedGotAllegroSecret));
            basicAuthApiMock.verify(() -> BasicAuthApi.init());
        }
    }

    @Test
    void shouldNotInitAllegroSecretWhenGotNoPassword() {

        assertThrows(
            IllegalStateException.class,
            () -> authService.initAllegroSecret(null)
        );
    }

    @Test
    void shouldNotInitAllegroSecretWhenUserPassedFirstLogin() {

        String gotPassword = "kamil-nowak";

        try(
            MockedStatic<SecureStorage> secureStorageMock = Mockito.mockStatic(SecureStorage.class);
        ){

            secureStorageMock.when(() -> SecureStorage.doesExist(any())).thenReturn(true);

            assertThrows(
                IllegalStateException.class,
                () -> authService.initAllegroSecret(gotPassword)
            );

            secureStorageMock.verify(() -> SecureStorage.doesExist(BasicAuthApi.ALLEGRO_SECRET_POSTFIX));
        }
    }

    @Test
    void generateDeviceCodeAndVerificationToAllegro() {
    }

    @Test
    void loginToAllegro() {
    }

    @Test
    void logout() {
    }
}