package com.bhaa.myapplication.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.bhaa.myapplication.R;

public class ZoomDetailsDialog extends AppCompatDialogFragment {
    private AddZoomDialogListener listener;
    private EditText organizer;
    private EditText date;
    private EditText time;
    private EditText link;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_zoom_details, null);
        builder.setView(view)
                .setTitle("Insert zoom Details")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Insert", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String organizerName = organizer.getText().toString();
                        String dateOfZoom = date.getText().toString();
                        String timeOfZoom = time.getText().toString();
                        String linkOfZoom = link.getText().toString();
                        listener.applyTexts(organizerName, dateOfZoom, timeOfZoom, linkOfZoom);

                    }
                });

        organizer = view.findViewById(R.id.organizer);
        date = view.findViewById(R.id.date);
        time = view.findViewById(R.id.time);
        link = view.findViewById(R.id.link);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (AddZoomDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface AddZoomDialogListener {
        void applyTexts(String organizer, String date, String time, String link);
    }
}