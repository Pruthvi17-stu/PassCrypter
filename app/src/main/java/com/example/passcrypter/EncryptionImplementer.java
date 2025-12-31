package com.example.passcrypter;
import android.content.Context;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import android.util.Base64;

public class EncryptionImplementer {
    private static final String Preferences_file_name="encrypted_password_prefs";
    private static final String Aes_Key_Alias="password_encryption_key";
    private SecretKey secretKey;

    public EncryptionImplementer(Context context)throws GeneralSecurityException, IOException{
        MasterKey masterKey = new MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();
        EncryptedSharedPreferences encryptedSharedPreferences = (EncryptedSharedPreferences) EncryptedSharedPreferences.create(
                context,
                Preferences_file_name,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);

        KeyGenParameterSpec keyGenParameterSpec= new KeyGenParameterSpec.Builder(
                Aes_Key_Alias,
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(256)
                .build();




    }
    /**
     * Encrypts a plain text string.
     * @param plainText The password to encrypt.
     * @return A Base64 encoded string representing the encrypted data.
     */
    public String encrypt(String plainText) throws GeneralSecurityException{
        return plainText;
    }
    /**
     * Decrypts an encrypted string.
     * @param encryptedText The Base64 encoded string to decrypt.
     * @return The original plain text password.
     */
   public String decryptedkey(String encryptedkey)throws GeneralSecurityException{
        return encryptedkey;
   }


}
