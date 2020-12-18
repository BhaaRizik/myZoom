package com.bhaa.myapplication.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
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

    public static class ZoomViewHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {
        private View itemView;
        private ImageButton openMenu;
        public TextView meetingTitle;
        public TextView date;
        public TextView time;
        public TextView link;
        public Button openZoom;
        private Context context;
        private ArrayList<Zoom> zoomArrayList;

        public ZoomViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.openMenu = itemView.findViewById(R.id.recyclerViewMenuButton);
            meetingTitle = itemView.findViewById(R.id.recyclerMeetingTitle);
            date = itemView.findViewById(R.id.recyclerDate);
            time = itemView.findViewById(R.id.recyclerTime);
            openZoom = itemView.findViewById(R.id.openZoomLink);
            context = itemView.getContext();
        }

        public void bindData(ArrayList<Zoom> zoomArrayList, final int position) {
            this.zoomArrayList = zoomArrayList;
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

            openMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openPopupMenuInRecyclerView(view);
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
                    intent.putExtra(Intent.EXTRA_TEXT, zoomArrayList.get(getAdapterPosition()).toString());
                    context.startActivity(Intent.createChooser(intent, " Share via ..."));
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