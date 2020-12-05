package com.bhaa.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.bhaa.myapplication.Dialogs.ZoomDetailsDialog;
import com.bhaa.myapplication.Dto.Zoom;
import com.bhaa.myapplication.RecyclerView.ZoomAdapter;

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

        zoomArrayList = new ArrayList<>();
        buildRecyclerView();
    }

    public void addZoomOpenPopup(View view) {
        ZoomDetailsDialog zoomDetailsDialog = new ZoomDetailsDialog();
        zoomDetailsDialog.show(getSupportFragmentManager(), "zoom details dialog");
    }

    @Override
    public void applyTexts(String organizer, String date, String time, String link) {
        zoomArrayList.add(new Zoom(organizer, date, time,link));
    }

    public void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewZooms);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        zoomAdapter = new ZoomAdapter(zoomArrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(zoomAdapter);
    }
}
