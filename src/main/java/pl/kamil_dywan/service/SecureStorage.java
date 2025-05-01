package pl.kamil_dywan.service;

import com.microsoft.credentialstorage.SecretStore;
import com.microsoft.credentialstorage.StorageProvider;
import com.microsoft.credentialstorage.model.StoredCredential;

public class SecureStorage {

    private static final SecretStore<StoredCredential> store = StorageProvider.getCredentialStorage(true, StorageProvider.SecureOption.REQUIRED);

    private static String CREDENTIALS_KEY_PREFIX;

    private SecureStorage(){


    }

    public static void load(){

        CREDENTIALS_KEY_PREFIX = AppProperties.getProperty("secure-store.credentials-key");
    }

    public static void saveCredentials(String credentialsKeyPostfix, String password){

        saveCredentials(credentialsKeyPostfix, credentialsKeyPostfix, password);
    }

    public static void saveCredentials(String credentialsKeyPostfix, String username, String password){

        StoredCredential storedCredential = new StoredCredential(username, password.toCharArray());

        String credentialsKey = getCredentialsKey(credentialsKeyPostfix);

        store.add(credentialsKey, storedCredential);
    }

    public static String getCredentialsPassword(String credentialsKeyPrefix){

        char[] storedPassword = getCredentials(credentialsKeyPrefix).getPassword();

        return new String(storedPassword);
    }

    public static StoredCredential getCredentials(String credentialsKeyPostfix){

        String credentialsKey = getCredentialsKey(credentialsKeyPostfix);

        return store.get(credentialsKey);
    }

    private static String getCredentialsKey(String credentialsKeyPostfix){

        return CREDENTIALS_KEY_PREFIX + credentialsKeyPostfix;
    }

}
