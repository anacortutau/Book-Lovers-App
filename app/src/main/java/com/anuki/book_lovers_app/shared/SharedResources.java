package com.anuki.book_lovers_app.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.anuki.book_lovers_app.model.User;

public class SharedResources {
    private Context context;
    private static final String SHARED_PREFERENCES = "SHARED_PREFERENCES";
    private static final String SHARED_PREFERENCES_ID = "SHARED_PREFERENCES_ID";
    private static final String SHARED_PREFERENCES_EMAIL = "SHARED_PREFERENCES_EMAIL";
    private static final String SHARED_PREFERENCES_NAME = "SHARED_PREFERENCES_NAME";
    private static final String SHARED_PREFERENCES_TOKEN = "SHARED_PREFERENCES_TOKEN";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static SharedResources instance;



    private SharedResources(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized SharedResources getInstance(Context context){
        if(instance == null){
            instance = new SharedResources(context);
        }
        return instance;
    }

    public void saveUser(User user){
        editor.putInt(SHARED_PREFERENCES_ID, user.getId());
        editor.putString(SHARED_PREFERENCES_NAME, user.getNombre());
        editor.putString(SHARED_PREFERENCES_EMAIL, user.getEmail());
        editor.putString(SHARED_PREFERENCES_TOKEN, user.getToken());
        editor.apply();
    }

    public boolean isLoggedIn(){
        if(sharedPreferences.getInt(SHARED_PREFERENCES_ID, -1) != -1){
            return true;
        }
        return false;
    }

    public User getUser(){
        User user = new User(
                sharedPreferences.getInt(SHARED_PREFERENCES_ID, -1),
                sharedPreferences.getString(SHARED_PREFERENCES_NAME, null),
                sharedPreferences.getString(SHARED_PREFERENCES_EMAIL, null),
                sharedPreferences.getString(SHARED_PREFERENCES_TOKEN, null)
        );
        return user;
    }

    public void logOut(){
        editor.clear();
        editor.apply();
    }
}
