package pl.kamil_dywan.service;

import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class SecurityServiceTest {

    @Test
    void shouldDecryptAes() throws Exception {

        byte[] key = "1234567890123456".getBytes();

        byte[] rawText = "raw text".getBytes();

        String encryptedValueWithBase64 = "74KoNtbxkg7NlkXzoLVDBA==";
        byte[] encryptedValue = Base64.getDecoder().decode(encryptedValueWithBase64);

        byte[] decryptedValue = SecurityService.decryptAes(key, encryptedValue);

        assertArrayEquals(rawText, decryptedValue);
    }

    @Test
    void shouldNotDecryptAesWhenValueIsNull() throws Exception{

        byte[] gotValue = SecurityService.decryptAes(null, null);

        assertNull(gotValue);
    }

    @Test
    void shouldHashSha() {
    }
}