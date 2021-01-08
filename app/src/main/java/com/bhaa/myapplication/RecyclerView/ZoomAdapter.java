package com.bhaa.myapplication.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bhaa.myapplication.Dto.Zoom;
import com.bhaa.myapplication.R;
import com.bhaa.myapplication.utils.Operations;
import com.bhaa.myapplication.utils.SharedPreferencesUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ZoomAdapter extends RecyclerView.Adapter<ZoomAdapter.ZoomViewHolder> {
    private ArrayList<Zoom> zoomArrayList;
    private static onZoomItemClickListener onZoomItemClickListener;

    public ZoomAdapter(ArrayList<Zoom> zooms) {
        zoomArrayList = zooms;
    }

    @Override
    public ZoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zoom_recyclerview, parent, false);
        ZoomViewHolder zoomViewHolder = new ZoomViewHolder(view);
        return zoomViewHolder;
    }

    @Override
    public void onBindViewHolder(ZoomViewHolder holder, int position) {
     holder.bindData(zoomArrayList, position);
    }

    @Override
    public int getItemCount() {
        return zoomArrayList.size();
    }

    public static class ZoomViewHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {
        private View itemView;
        private ImageButton openMenu;
        public TextView meetingTitle;
        public TextView date;
        public TextView time;
        public TextView link;
        public Button openZoom;
        private Context context;
        private ArrayList<Zoom> zoomList;
        private Switch weeklySwitch;
        private long oneWeek = 604800000L;
        SimpleDateFormat dateFormat;

        public ZoomViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.openMenu = itemView.findViewById(R.id.recyclerViewMenuButton);
            meetingTitle = itemView.findViewById(R.id.recyclerMeetingTitle);
            date = itemView.findViewById(R.id.recyclerDate);
            time = itemView.findViewById(R.id.recyclerTime);
            openZoom = itemView.findViewById(R.id.openZoomLink);
            context = itemView.getContext();
            weeklySwitch = itemView.findViewById(R.id.onceOrWeekly);
            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        }

        public void bindData(final ArrayList<Zoom> zoomArrayList, final int position) {
            this.zoomList = zoomArrayList;
            final Zoom currentItem = zoomList.get(position);
            meetingTitle.setText(currentItem.getMeetingTitle());
            date.setText(dateFormat.format(currentItem.getDate()));
            time.setText(currentItem.getTime());
            weeklySwitch.setChecked(currentItem.isWeekly());
            openZoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse(currentItem.getLink());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onZoomItemClickListener.onZoomItemClick(currentItem, position);
                }
            });

            openMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openPopupMenuInRecyclerView(view);
                }
            });

            weeklySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        Calendar calendar = Calendar.getInstance();
                        if (calendar.getTime().after(currentItem.getDate())) {
                            long currentTime = currentItem.getDate().getTime();
                            currentItem.setDate(new Date(currentTime + oneWeek));
                            date.setText(dateFormat.format(currentItem.getDate()));
                            calendar.setTime(currentItem.getDate());
                            Operations.startAlarm(context, calendar);
                            Toast.makeText(context, "Switch true", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "Switch false", Toast.LENGTH_LONG).show();
                    }
                    currentItem.setWeekly(isChecked);
                    SharedPreferencesUtils.setZoomArrayList(zoomList);
                    SharedPreferencesUtils.saveData();
                }
            });
        }

        private void openPopupMenuInRecyclerView(View view){
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.share:
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Zoom Details : ");
                    intent.putExtra(Intent.EXTRA_TEXT, zoomList.get(getAdapterPosition()).toString());
                    context.startActivity(Intent.createChooser(intent, " Share via ..."));
                    return true;
                case R.id.onceOrWeekly:
                    Toast.makeText(context, "Switch button", Toast.LENGTH_LONG).show();
                    return true;
                default:
                    throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
            }
        }
    }

    public interface onZoomItemClickListener {
        void onZoomItemClick(Zoom zoom, int position);
    }

    public void setOnItemClickListener(onZoomItemClickListener listener) {
        this.onZoomItemClickListener = listener;
    }
}