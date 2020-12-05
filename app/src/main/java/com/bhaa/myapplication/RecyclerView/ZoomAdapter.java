package com.bhaa.myapplication.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bhaa.myapplication.Dto.Zoom;
import com.bhaa.myapplication.R;

import java.util.ArrayList;


public class ZoomAdapter extends RecyclerView.Adapter<ZoomAdapter.ZoomViewHolder> {
    private ArrayList<Zoom> zoomArrayList;

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

    public static class ZoomViewHolder extends RecyclerView.ViewHolder {
        public TextView organizer;
        public TextView date;
        public TextView time;
        public TextView link;

        public ZoomViewHolder(View itemView) {
            super(itemView);
            organizer = itemView.findViewById(R.id.recyclerOrganizer);
            date = itemView.findViewById(R.id.recyclerDate);
            time = itemView.findViewById(R.id.recyclerTime);
            link = itemView.findViewById(R.id.recyclerLink);
        }

        public void bindData(ArrayList<Zoom> zoomArrayList, int position){
            Zoom currentItem = zoomArrayList.get(position);
            organizer.setText(currentItem.getOrganizer());
            date.setText(currentItem.getDate());
            time.setText(currentItem.getTime());
            link.setText(currentItem.getLink());
        }
    }
}