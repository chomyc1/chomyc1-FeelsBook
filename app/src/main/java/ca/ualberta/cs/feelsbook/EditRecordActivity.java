package ca.ualberta.cs.feelsbook;

import android.content.Context;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
//import android.app.TimePickerDialog;

import java.util.Date;

public class EditRecordActivity extends FragmentActivity {

    private EditText messageText;
    // We might want 2 fragments, one for date and one for time
    TimePickerFragment newFragment = null;

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

                if ((newFragment != null) && (newFragment.getChosenHour() != -1)) { // Means a new time was chosen
                    int newHour = newFragment.getChosenHour();
                    int newMinute = newFragment.getChosenMinute();
                    date.setHours(newHour);
                    date.setMinutes(newMinute);
                }

                saveChanges(newMessage, date, false);
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
                //TextView textView = (TextView) findViewById(R.id.Comment);
                //textView.setText(String.valueOf(response));
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
