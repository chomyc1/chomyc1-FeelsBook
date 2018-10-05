package ca.ualberta.cs.feelsbook;

import android.app.Activity;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
//import android.app.TimePickerDialog;

import java.util.Date;

//public class EditRecordActivity extends AppCompatActivity {
public class EditRecordActivity extends Activity {
    private EditText messageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent outputIntent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);


        String message = outputIntent.getStringExtra(HistoryActivity.SELECTED_MESSAGE);
        Date date = (Date) outputIntent.getSerializableExtra(HistoryActivity.SELECTED_DATE);

        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText(message);
        messageText = (EditText) findViewById(R.id.messageText);
        Button editButton = (Button) findViewById(R.id.editButton);


        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                String newMessage = messageText.getText().toString();
                saveChanges(newMessage);
            }
        });
    }


    private void saveChanges(String newMessage) {
        Intent outputIntent = getIntent();
        outputIntent.putExtra("editedMessage", newMessage);
        setResult(RESULT_OK, outputIntent);
        finish();
    }
}
