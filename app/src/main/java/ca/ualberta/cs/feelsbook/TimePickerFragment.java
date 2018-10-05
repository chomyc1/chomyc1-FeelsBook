package ca.ualberta.cs.feelsbook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    int chosenHour = -1;
    int chosenMinute = -1;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //TextView textView = (TextView) findViewByID(R.id.Comment);
        chosenHour = hourOfDay;
        chosenMinute = minute;
    }

    public int getChosenHour() {
        return chosenHour;
    }

    public int getChosenMinute() {
        return chosenMinute;
    }
}

//public interface ChangeTimeFragmentListener {
//public void deleteDialogConfirmed(DialogFragment dialog);
//}

    /*ChangeTimeDialogListener dialogListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dialogListener = (ChangeTimeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("DeleteHistoryDialogListener must be implemented.");
        }
    }*/





            /*AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle(R.string.delete_history);
        dialogBuilder.setMessage(R.string.delete_message);

        dialogBuilder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            //@Override
            public void onClick(DialogInterface dialog, int id) {
                dialogListener.deleteDialogConfirmed(TimePickerFragment.this);
            }
        });
        dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            //@Override
            public void onClick(DialogInterface dialog, int id) {
                // We don't need to do anything in this case (other than closing the dialog)
            }
        });

        AlertDialog displayedDialog = dialogBuilder.create();
        return displayedDialog;*/

//}