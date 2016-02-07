package com.hab.studyspace;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ivy on 2/7/16.
 */
public class StartTimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
        EditText mStartTime = (EditText) getActivity().findViewById(R.id.new_start);
        try {
            Date date = formatter.parse(mStartTime.getText().toString());
            c.setTime(date);
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        SimpleDateFormat formatter = new SimpleDateFormat("kk:mm");
        try {
            Date d = formatter.parse(String.format("%02d:%02d", hourOfDay, minute));
            EditText mStartTime = (EditText) getActivity().findViewById(R.id.new_start);
            formatter = new SimpleDateFormat("hh:mm a");
            mStartTime.setText(formatter.format(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}

