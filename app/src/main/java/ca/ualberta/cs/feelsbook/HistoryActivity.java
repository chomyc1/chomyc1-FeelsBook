package ca.ualberta.cs.feelsbook;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class HistoryActivity extends Activity {

    private ListView recordsList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        recordsList = (ListView) findViewById(R.id.recordsList);

        Intent intent = getIntent();
        String message = intent.getStringExtra(FeelsBookActivity.RECORD_LIST);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        String[] records = loadFromFile();
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, records);
        recordsList.setAdapter(adapter);
    }


    private String[] loadFromFile() {
        ArrayList<String> records = new ArrayList<String>();
        try {
            FileInputStream fis = openFileInput(getString(R.string.file_name));
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            String line = in.readLine();
            while (line != null) {
                records.add(line);
                line = in.readLine();
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return records.toArray(new String[records.size()]);
    }

}
