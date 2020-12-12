package com.bhaa.myapplication.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bhaa.myapplication.Dto.Zoom;
import com.bhaa.myapplication.R;

import java.util.ArrayList;


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

    public static class ZoomViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        public TextView meetingTitle;
        public TextView date;
        public TextView time;
        public TextView link;
        public Button openZoom;
        private Context context;

        public ZoomViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            meetingTitle = itemView.findViewById(R.id.recyclerMeetingTitle);
            date = itemView.findViewById(R.id.recyclerDate);
            time = itemView.findViewById(R.id.recyclerTime);
            openZoom = itemView.findViewById(R.id.openZoomLink);
            context = itemView.getContext();
        }

        public void bindData(ArrayList<Zoom> zoomArrayList, final int position){
            final Zoom currentItem = zoomArrayList.get(position);
            meetingTitle.setText(currentItem.getMeetingTitle());
            date.setText(currentItem.getDate());
            time.setText(currentItem.getTime());
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
        }
    }

    public interface onZoomItemClickListener {
        void onZoomItemClick(Zoom zoom, int position);
    }

    public void setOnItemClickListener(onZoomItemClickListener listener) {
        this.onZoomItemClickListener = listener;
    }
}