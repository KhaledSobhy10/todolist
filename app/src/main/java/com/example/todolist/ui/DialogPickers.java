package com.example.todolist.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DialogPickers extends DialogFragment {

    public static final String PICKER_TYPE = "picker_type";
    public static final String DATE_PICKER = "date_picker";
    public static final String TIME_PICKER = "time_picker";


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog mDialog = super.onCreateDialog(savedInstanceState);
        if (getArguments() != null) {
            String pickerType = getArguments().getString(PICKER_TYPE);
            if (DATE_PICKER.equals(pickerType)) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getContext(), year, month, day);
            } else {
                final Calendar c = Calendar.getInstance();
                int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                return new TimePickerDialog(getContext(), (TimePickerDialog.OnTimeSetListener) getContext(), hourOfDay, minute, true);
            }

        }
        return mDialog;
    }
}
