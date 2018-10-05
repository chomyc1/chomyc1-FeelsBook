package ca.ualberta.cs.feelsbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class EditRecordActivity extends FragmentActivity {

    private EditText messageText;
    TimePickerFragment newTimeFragment = null;
    DatePickerFragment newDateFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent outputIntent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);

        String message = outputIntent.getStringExtra(HistoryActivity.SELECTED_MESSAGE);
        final Date date = (Date) outputIntent.getSerializableExtra(HistoryActivity.SELECTED_DATE);

        messageText = (EditText) findViewById(R.id.messageText);
        messageText.setText(message, EditText.BufferType.EDITABLE);
        Button timeButton = (Button) findViewById(R.id.timeButton);
        Button dateButton = (Button) findViewById(R.id.dateButton);
        Button editButton = (Button) findViewById(R.id.editButton);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);

        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                String newMessage = messageText.getText().toString();

                if ((newTimeFragment != null) && (newTimeFragment.getChosenHour() != -1)) { // Means a new time was chosen
                    int newHour = newTimeFragment.getChosenHour();
                    int newMinute = newTimeFragment.getChosenMinute();
                    date.setHours(newHour);
                    date.setMinutes(newMinute);
                }
                if ((newDateFragment != null) && (newDateFragment.getChosenYear() != -1)) { // Means a new date was chosen
                    int newYear = newDateFragment.getChosenYear() - 1900;
                    int newMonth = newDateFragment.getChosenMonth();
                    int newDay = newDateFragment.getChosenDay();
                    date.setYear(newYear);
                    date.setMonth(newMonth);
                    date.setDate(newDay);
                }
                saveChanges(newMessage, date, false);
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newTimeFragment = new TimePickerFragment();
                newTimeFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newDateFragment = new DatePickerFragment();
                newDateFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                String newMessage = "";
                saveChanges(newMessage, date, true);
            }
        });
    }


    private void saveChanges(String newMessage, Date newDate, boolean delete) {
        Intent outputIntent = getIntent();
        outputIntent.putExtra("editedMessage", newMessage);
        outputIntent.putExtra("editedDate", newDate);
        outputIntent.putExtra("delete", delete);
        setResult(RESULT_OK, outputIntent);
        finish();
    }
}
