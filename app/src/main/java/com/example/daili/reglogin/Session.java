package com.example.daili.reglogin;

import android.content.Context;
import android.content.SharedPreferences;

//izmantojot sharedPreferences objektu, nodrošina sessiju
public class Session {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;

    public Session(Context context) {
        this.ctx = context;
        prefs = context.getSharedPreferences("PrefID", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setLoggedIn(boolean loggedin) {
        editor.putBoolean("loggedInMode", loggedin);
        editor.commit();
    }

    public boolean HasLoggedIn() {

        return prefs.getBoolean("loggedInMode", false);
    }
}