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

import com.bhaa.myapplication.R;
import com.bhaa.myapplication.utils.DatePickerFragment;
import com.bhaa.myapplication.utils.Languge.DeviceProperties;
import com.bhaa.myapplication.utils.Languge.SpecialLanguage;
import com.bhaa.myapplication.utils.Operations;
import com.bhaa.myapplication.utils.TimePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ZoomDetailsDialog extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private AddZoomDialogListener listener;
    private EditText meetingTitle;
    private TextView date;
    private TextView time;
    private EditText link;
    private Calendar c;

    private Button dateButton;
    private Button timeButton;
    private String editOrAdd = "add";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        boolean isHebrew = DeviceProperties.getDeviceLanguage().equals(SpecialLanguage.עברית.name());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view;
        if (DeviceProperties.getDeviceLanguage().equals(SpecialLanguage.עברית.name()))
            view = inflater.inflate(R.layout.add_zoom_details_he, null);
        else
            view = inflater.inflate(R.layout.add_zoom_details, null);
        builder.setView(view)
                .setTitle(editOrAdd.equals("add") ?
                        isHebrew ? "הוסף מפגש" : "Insert zoom Details" :
                        isHebrew ? "ערוך" : "Edit")
                .setNegativeButton(isHebrew ? "ביטול" : "cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton(isHebrew ? "הוסף" : "Insert", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String meetingTitleString = meetingTitle.getText().toString();
                        String linkOfZoom = link.getText().toString();
                        listener.applyTexts(meetingTitleString, linkOfZoom, editOrAdd, c);

                    }
                });

        meetingTitle = view.findViewById(R.id.meetingTitle);
        date = view.findViewById(R.id.date);
        time = view.findViewById(R.id.time);
        link = view.findViewById(R.id.link);
        dateButton = view.findViewById(R.id.buttonDate);
        timeButton = view.findViewById(R.id.buttonTime);
        c = Calendar.getInstance();

        if (editOrAdd.equals("edit")) {
            meetingTitle.setText(Operations.zoomToSend.getMeetingTitle());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            date.setText(dateFormat.format(Operations.zoomToSend.getDate()));
            time.setText(Operations.zoomToSend.getTime());
            link.setText(Operations.zoomToSend.getLink());
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
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateString = dateFormat.format(c.getTime());
        date.setText(currentDateString);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String currentTime = timeFormat.format(c.getTime());
        time.setText(currentTime);
    }

    public interface AddZoomDialogListener {
        void applyTexts(String meetingTitle, String link, String editOrAdd, Calendar calendar);
    }

    public void setEditOrAdd(String editOrAdd) {
        this.editOrAdd = editOrAdd;
    }
}