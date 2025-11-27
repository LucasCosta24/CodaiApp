package com.example.codaiapp.utils;
import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "UserSession";
    private static final String IS_LOGGED_IN = "IsLoggedIn";
    private static final String KEY_USER_NAME = "UserName";
    private static final String KEY_USER_EMAIL = "UserEmail";

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(String name, String email){
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(KEY_USER_NAME, name);
        editor.putString(KEY_USER_EMAIL, email);
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGGED_IN, false);
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
    }

    public String getUserName(){
        return pref.getString(KEY_USER_NAME, "Visitante");
    }

    public String getUserEmail(){
        return pref.getString(KEY_USER_EMAIL, null);
    }
}