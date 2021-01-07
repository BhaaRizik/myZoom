package com.bhaa.myapplication.utils;

import android.content.SharedPreferences;

import com.bhaa.myapplication.Dto.Zoom;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SharedPreferencesUtils {
    private static SharedPreferencesUtils sharedPreferencesUtils;
    private static SharedPreferences sharedPreferences;

    public static void setZoomArrayList(ArrayList<Zoom> zoomArrayList) {
        SharedPreferencesUtils.zoomArrayList = zoomArrayList;
    }

    private static ArrayList<Zoom> zoomArrayList;

    private SharedPreferencesUtils(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public static SharedPreferencesUtils getSharedPreferencesUtils(SharedPreferences sharedPreferences){
        if (sharedPreferencesUtils == null){
            sharedPreferencesUtils = new SharedPreferencesUtils(sharedPreferences);
        }
        return sharedPreferencesUtils;
    }

    public static void saveData() {
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(zoomArrayList);
        editor.putString("task list", json);
        editor.apply();
    }

    public static ArrayList<Zoom> loadData() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Zoom>>() {}.getType();
        zoomArrayList = gson.fromJson(json, type);
        if (zoomArrayList == null) {
            zoomArrayList = new ArrayList<>();
        }
        return zoomArrayList;
    }
}
