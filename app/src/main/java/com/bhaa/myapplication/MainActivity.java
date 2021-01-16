package com.bhaa.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.bhaa.myapplication.Dialogs.ZoomDetailsDialog;
import com.bhaa.myapplication.Dto.Zoom;
import com.bhaa.myapplication.RecyclerView.ZoomAdapter;
import com.bhaa.myapplication.utils.Languge.DeviceProperties;
import com.bhaa.myapplication.utils.Operations;
import com.bhaa.myapplication.utils.SharedPreferencesUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ZoomDetailsDialog.AddZoomDialogListener {
    private ArrayList<Zoom> zoomArrayList;
    private RecyclerView recyclerView;
    private ZoomAdapter zoomAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ZoomDetailsDialog zoomDetailsDialog;
    public int position;
    public static Zoom zoomToSend;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DeviceProperties.setDeviceLanguage(Locale.getDefault().getDisplayLanguage());
        zoomDetailsDialog = new ZoomDetailsDialog();
        sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferencesUtils.getSharedPreferencesUtils(sharedPreferences);
        zoomArrayList = SharedPreferencesUtils.loadData();
        zoomAdapter = Operations.getZoomAdapter(this, zoomArrayList);
        Operations.buildRecyclerView(recyclerView, zoomAdapter, this);
    }

    public void addZoomOpenPopup(View view) {
        zoomDetailsDialog.setEditOrAdd("add");
        zoomDetailsDialog.show(getSupportFragmentManager(), "zoom details dialog");
    }

    @Override
    public void applyTexts(String meetingTitle, final String link, String editOrAdd, Calendar calendar) {
        Date date = calendar.getTime();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String time = timeFormat.format(calendar.getTime());

        if (editOrAdd.equals("edit")){
            zoomArrayList.set(Operations.getPosition(), new Zoom(meetingTitle, date, time, link, false));
            SharedPreferencesUtils.saveData();
            zoomAdapter.notifyDataSetChanged();
        }else {
            zoomArrayList.add(new Zoom(meetingTitle, date, time, link, false));
            SharedPreferencesUtils.saveData();
            zoomAdapter.notifyItemInserted(zoomArrayList.size());
        }
        Operations.startAlarm(this, calendar);
    }
}
