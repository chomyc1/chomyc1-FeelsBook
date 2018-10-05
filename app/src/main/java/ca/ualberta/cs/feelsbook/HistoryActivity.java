package ca.ualberta.cs.feelsbook;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class HistoryActivity extends Activity {

    public static final String SELECTED_MESSAGE = "ca.ualberta.cs.feelsbook.RECORD_MESSAGE";
    public static final String SELECTED_DATE = "ca.ualberta.cs.feelsbook.RECORD_DATE";
    private ListView recordsListView;
    private ArrayAdapter<EmotionRecord> adapter;
    private ArrayList<EmotionRecord> emotionRecordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        recordsListView = (ListView) findViewById(R.id.recordsList);

        Intent intent = getIntent();
        String message = intent.getStringExtra(FeelsBookActivity.RECORD_LIST);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);
/*
        recordsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> selection, View v, int position, long l) {
                //setResult(RESULT_OK);
                //String text = bodyText.getText().toString();
                //saveInFile(text, new Date(System.currentTimeMillis()));
                //finish();

                setResult(RESULT_OK);
                //String text = (String) selection.getItemAtPosition(position);
                EmotionRecord selectedRecord = (EmotionRecord) selection.getItemAtPosition(position);
                String selectedMessage = selectedRecord.getMessage();
                Date selectedDate = selectedRecord.getDate();
                startEditRecordActivity(selectedMessage, selectedDate);

            }
        });*/
    }

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		loadFromFile();
		adapter = new ArrayAdapter<EmotionRecord>(this, R.layout.list_item, emotionRecordList);
        recordsListView.setAdapter(adapter);
	}

    private void loadFromFile() {
        emotionRecordList = new ArrayList<EmotionRecord>();
        try {
            FileInputStream fis = openFileInput(getString(R.string.file_name));
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson jsonConverter = new Gson();

            //Taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2017-01-24 18:19
            Type listType = new TypeToken<ArrayList<Joy>>(){}.getType();
            emotionRecordList = jsonConverter.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            emotionRecordList = new ArrayList<EmotionRecord>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void startEditRecordActivity(String selectedMessage, Date selectedDate) {
        Intent intent = new Intent(this, EditRecordActivity.class);
        intent.putExtra(SELECTED_MESSAGE, selectedMessage);
        intent.putExtra(SELECTED_DATE, selectedDate);
        startActivity(intent);
    }

}
    /*
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        //String[] records = loadFromFile();
        //adapter = new ArrayAdapter<String>(this, R.layout.list_item, records);
        //recordsList.setAdapter(adapter);
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
*/