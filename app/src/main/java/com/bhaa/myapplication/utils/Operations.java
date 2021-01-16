package com.bhaa.myapplication.utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhaa.myapplication.BroadcastReceiver.AlertReceiver;
import com.bhaa.myapplication.Dialogs.ZoomDetailsDialog;
import com.bhaa.myapplication.Dto.Zoom;
import com.bhaa.myapplication.R;
import com.bhaa.myapplication.RecyclerView.ZoomAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Operations  {
    public static void setZoomArrayList(ArrayList<Zoom> zoomArrayList) {
        Operations.zoomArrayList = zoomArrayList;
    }

    static ArrayList<Zoom> zoomArrayList;
    static ZoomAdapter zoomAdapter;
    public static Zoom zoomToSend;
    final static int[] position = new int[1];

    public static int getPosition() {
        return position[0];
    }

    public static void startAlarm(Context context, Calendar c) {
        long timeInMillis = c.getTimeInMillis();
        c.setTimeInMillis(timeInMillis - 15*60*1000);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    private static void cancelAlarm(RecyclerView.ViewHolder viewHolder) {
        zoomArrayList.get(viewHolder.getAdapterPosition()).setDate(new Date(Calendar.getInstance().getTimeInMillis() - 1));
    }

    public static ZoomAdapter getZoomAdapter(final Context context, ArrayList<Zoom> zoomArrayList){
        setZoomArrayList(zoomArrayList);
        final ZoomDetailsDialog zoomDetailsDialog = new ZoomDetailsDialog();;
        zoomAdapter = new ZoomAdapter(zoomArrayList);
        zoomAdapter.setOnItemClickListener(new ZoomAdapter.onZoomItemClickListener() {
            @Override
            public void onZoomItemClick(Zoom zoom, int positionToEdit) {
                position[0] = positionToEdit;
                zoomDetailsDialog.setEditOrAdd("edit");
                zoomToSend = zoom;
                zoomDetailsDialog.show(((FragmentActivity)context).getSupportFragmentManager(), "zoom details dialog");
            }
        });
        return zoomAdapter;
    }

    public static void buildRecyclerView(RecyclerView recyclerView, ZoomAdapter zoomAdapter, Activity activity) {
        RecyclerView.LayoutManager layoutManager;

        recyclerView = activity.findViewById(R.id.recyclerViewZooms);
        layoutManager = new LinearLayoutManager(activity);

        recyclerView.setLayoutManager(layoutManager);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(zoomAdapter);
    }

    static ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            cancelAlarm(viewHolder);
            zoomArrayList.remove(viewHolder.getAdapterPosition());
            SharedPreferencesUtils.saveData();
            zoomAdapter.notifyDataSetChanged();
        }
    };
}
