package com.bhaa.myapplication.Dialogs;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.bhaa.myapplication.MainActivity;
import com.bhaa.myapplication.R;
import com.bhaa.myapplication.utils.DatePickerFragment;
import com.bhaa.myapplication.utils.TimePickerFragment;

import java.text.DateFormat;
import java.util.Calendar;

public class ZoomDetailsDialog extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private AddZoomDialogListener listener;
    private EditText meetingTitle;
    private TextView date;
    private TextView time;
    private EditText link;

    private Button dateButton;
    private Button timeButton;
    private String editOrAdd = "add";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_zoom_details, null);
        builder.setView(view)
                .setTitle(editOrAdd.equals("add") ? "Insert zoom Details" : "Edit")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Insert", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String meetingTitleString = meetingTitle.getText().toString();
                        String dateOfZoom = date.getText().toString();
                        String timeOfZoom = time.getText().toString();
                        String linkOfZoom = link.getText().toString();
                        listener.applyTexts(meetingTitleString, dateOfZoom, timeOfZoom, linkOfZoom, editOrAdd);

                    }
                });

        meetingTitle = view.findViewById(R.id.meetingTitle);
        date = view.findViewById(R.id.date);
        time = view.findViewById(R.id.time);
        link = view.findViewById(R.id.link);
        dateButton = view.findViewById(R.id.buttonDate);
        timeButton = view.findViewById(R.id.buttonTime);

        if(editOrAdd.equals("edit")) {
            meetingTitle.setText(MainActivity.zoomToSend.getMeetingTitle());
            date.setText(MainActivity.zoomToSend.getDate());
            time.setText(MainActivity.zoomToSend.getTime());
            link.setText(MainActivity.zoomToSend.getLink());
        }

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.setTargetFragment(ZoomDetailsDialog.this, 0);
                datePicker.show(getFragmentManager(), "date picker");
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.setTargetFragment(ZoomDetailsDialog.this, 0);
                timePicker.show(getFragmentManager(), "time picker");
            }
        });

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

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        date.setText(currentDateString);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        time.setText(hourOfDay + " : " + minute);
    }

    public interface AddZoomDialogListener {
        void applyTexts(String meetingTitle, String date, String time, String link, String editOrAdd);
    }

    public void setEditOrAdd(String editOrAdd){
        this.editOrAdd = editOrAdd;
    }
}