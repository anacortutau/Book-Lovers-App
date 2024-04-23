package com.anuki.book_lovers_app.shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.anuki.book_lovers_app.model.User;

public class SharedResources {
    private static final String SHARED_PREFERENCES = "appPreferences";
    private static final String SHARED_PREFERENCES_ID = "USER_ID";
    private static final String SHARED_PREFERENCES_EMAIL = "USER_EMAIL";
    private static final String SHARED_PREFERENCES_NAME = "USER_NAME";
    private static final String SHARED_PREFERENCES_TOKEN = "TOKEN";
    private static SharedResources instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SharedResources(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized SharedResources getInstance(Context context) {
        if (instance == null) {
            instance = new SharedResources(context);
        }
        return instance;
    }

    public void saveUser(User user) {
        editor.putInt(SHARED_PREFERENCES_ID, user.getId());
        editor.putString(SHARED_PREFERENCES_NAME, user.getNombre());
        editor.putString(SHARED_PREFERENCES_EMAIL, user.getEmail());
        editor.putString(SHARED_PREFERENCES_TOKEN, user.getToken());
        editor.apply();
        Log.d("SharedResources", "Token saved: " + user.getToken()); // Correcto para depuración
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getInt(SHARED_PREFERENCES_ID, -1) != -1;
    }

    public User getUser() {
        return new User(
                sharedPreferences.getInt(SHARED_PREFERENCES_ID, -1),
                sharedPreferences.getString(SHARED_PREFERENCES_NAME, null),
                sharedPreferences.getString(SHARED_PREFERENCES_EMAIL, null),
                sharedPreferences.getString(SHARED_PREFERENCES_TOKEN, null)
        );
    }

    public String getToken() {
        String token = sharedPreferences.getString(SHARED_PREFERENCES_TOKEN, "");
        Log.d("SharedResources", "Retrieved Token: " + token); // Para depuración
        return token;
    }

    public void logOut() {
        editor.clear();
        editor.apply();
    }
}
