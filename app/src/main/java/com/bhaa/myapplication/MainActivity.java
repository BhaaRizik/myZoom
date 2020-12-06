package com.bhaa.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.bhaa.myapplication.Dialogs.ZoomDetailsDialog;
import com.bhaa.myapplication.Dto.Zoom;
import com.bhaa.myapplication.RecyclerView.ZoomAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ZoomDetailsDialog.AddZoomDialogListener {
    private ArrayList<Zoom> zoomArrayList;
    private RecyclerView recyclerView;
    private ZoomAdapter zoomAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();
        buildRecyclerView();
    }

    public void addZoomOpenPopup(View view) {
        ZoomDetailsDialog zoomDetailsDialog = new ZoomDetailsDialog();
        zoomDetailsDialog.show(getSupportFragmentManager(), "zoom details dialog");
    }

    @Override
    public void applyTexts(String meetingTitle, String date, String time, final String link) {
        zoomArrayList.add(new Zoom(meetingTitle, date, time, link));
        saveData();
    }

    public void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewZooms);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        zoomAdapter = new ZoomAdapter(zoomArrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(zoomAdapter);
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(zoomArrayList);
        editor.putString("task list", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Zoom>>() {}.getType();
        zoomArrayList = gson.fromJson(json, type);
        if (zoomArrayList == null) {
            zoomArrayList = new ArrayList<>();
        }
    }
}
