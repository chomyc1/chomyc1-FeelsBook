package ca.ualberta.cs.feelsbook;

import android.app.Activity;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
//import android.app.TimePickerDialog;

import java.util.Date;

//public class EditRecordActivity extends AppCompatActivity {
public class EditRecordActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);

        Intent intent = getIntent();
        //String message = intent.getStringExtra(HistoryActivity.SELECTED_MESSAGE);
        //Date date = intent.getStringExtra(HistoryActivity.SELECTED_DATE);
        TextView textView = (TextView) findViewById(R.id.textView2);
        //textView.setText(message);
    }
}