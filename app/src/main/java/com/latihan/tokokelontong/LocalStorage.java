package com.latihan.tokokelontong;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalStorage {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    String data;
    private static final String PREF_NAME = "food";

    public LocalStorage(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getData() {
        data = sharedPreferences.getString( "data", "");
        return data;
    }

    public void setData(String data) {
        editor.putString("data", data);
        editor.apply();
        this.data = data;
    }
}
