package com.ale.gttt.Session;

import android.content.Context;
import android.content.SharedPreferences;

import com.ale.gttt.entities.User;

public class SharedPreferenceManager {
    private static final String SHARED_PREFERENCES="SHARED_PREFERENCES";
    private static final String SHARED_PREFERENCES_ID="SHARED_PREFERENCES_ID";
    private static final String SHARED_PREFERENCES_NICK="SHARED_PREFERENCES_NICK";
    private static final String SHARED_PREFERENCES_PASSWORD="SHARED_PREFERENCES_PASSWORD";
    private static final String SHARED_PREFERENCES_EMAIL="SHARED_PREFERENCES_EMAIL";
    private static final String SHARED_PREFERENCES_TOKEN="SHARED_PREFERENCES_TOKEN";
    private SharedPreferences sharedPreferences;
    private static SharedPreferenceManager instance;
    private Context context;
    private SharedPreferences.Editor editor;
    private SharedPreferenceManager(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }
    public static synchronized SharedPreferenceManager getInstance(Context context){
        if (instance==null){
            instance= new SharedPreferenceManager(context);
        }
        return instance;
    }
    public void   SetToken(String t){
        editor.putString(SHARED_PREFERENCES_TOKEN, t);
        editor.apply();
    }
    public void SaveUser(User u){

        editor.putInt(SHARED_PREFERENCES_ID, u.getId());
        editor.putString(SHARED_PREFERENCES_NICK , u.getNick());
        editor.putString(SHARED_PREFERENCES_EMAIL , u.getEmail());
        editor.putString(SHARED_PREFERENCES_PASSWORD , u.getPassword());

        editor.apply();
    }
    public boolean IsLoged(){
        if (sharedPreferences.getInt(SHARED_PREFERENCES_ID,  0) !=0){
            return true;
        }
        return false;
    }
    public User GetUser(){
        User u= new User(
                sharedPreferences.getInt(SHARED_PREFERENCES_ID,  0),
                sharedPreferences.getString(SHARED_PREFERENCES_NICK, null),
                sharedPreferences.getString(SHARED_PREFERENCES_EMAIL, null),
                sharedPreferences.getString(SHARED_PREFERENCES_PASSWORD, null)
        );
        return u;
    }
    public String GetToken(){
       return sharedPreferences.getString(SHARED_PREFERENCES_TOKEN, null);
    }
    public void LogOut(){
        editor.clear();
        editor.apply();
    }
}
