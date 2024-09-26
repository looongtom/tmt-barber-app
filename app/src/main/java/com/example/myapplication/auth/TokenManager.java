package com.example.myapplication.auth;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class TokenManager {

    private static final String PREFS_NAME = "secure_prefs";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private SharedPreferences encryptedSharedPreferences;

    public TokenManager(Context context) {
        try {
            // Create a master key that will be used to encrypt/decrypt the data in EncryptedSharedPreferences
            MasterKey masterKey = new MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();
            // Initialize EncryptedSharedPreferences
            encryptedSharedPreferences = EncryptedSharedPreferences.create(
                    context,
                    PREFS_NAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);

        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    // Save the access token securely
    public void saveAccessToken(String accessToken) {
        SharedPreferences.Editor editor = encryptedSharedPreferences.edit();
        editor.putString(KEY_ACCESS_TOKEN, accessToken);
        editor.apply();
    }

    // Retrieve the access token securely
    public String getAccessToken() {
        return encryptedSharedPreferences.getString(KEY_ACCESS_TOKEN, null);
    }

    // Clear the access token (e.g., for logging out)
    public void clearAccessToken() {
        SharedPreferences.Editor editor = encryptedSharedPreferences.edit();
        editor.remove(KEY_ACCESS_TOKEN);
        editor.apply();
    }
}