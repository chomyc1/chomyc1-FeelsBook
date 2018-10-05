package ca.ualberta.cs.feelsbook;

import android.app.Dialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    int chosenYear = -1;
    int chosenMonth = -1;
    int chosenDay = -1;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of TimePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        chosenYear = year;
        chosenMonth = month;
        chosenDay = day;
    }

    public int getChosenYear() {
        return chosenYear;
    }

    public int getChosenMonth() {
        return chosenMonth;
    }

    public int getChosenDay() {
        return chosenDay;
    }
}