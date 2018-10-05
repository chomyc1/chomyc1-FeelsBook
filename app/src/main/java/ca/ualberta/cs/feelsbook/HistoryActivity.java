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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class HistoryActivity extends FragmentActivity {

    public static final String SELECTED_MESSAGE = "ca.ualberta.cs.feelsbook.RECORD_MESSAGE";
    public static final String SELECTED_DATE = "ca.ualberta.cs.feelsbook.RECORD_DATE";
    private ListView recordsListView;
    private ArrayAdapter<EmotionRecord> adapter;
    private ArrayList<EmotionRecord> emotionRecordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Button emotionCountButton = (Button) findViewById(R.id.countButton);
        Button deleteHistoryButton = (Button) findViewById(R.id.deleteHistoryButton);
        recordsListView = (ListView) findViewById(R.id.recordsList);

        Intent intent = getIntent();
        String message = intent.getStringExtra(FeelsBookActivity.RECORD_LIST);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);

        emotionCountButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                showEmotionCount();
            }
        });

        deleteHistoryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                showEmotionCount();
            }
        });

        recordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        });
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

    private void showEmotionCount() {
        int numEmotions = emotionRecordList.size();
        int angerCount = 0;
        int fearCount = 0;
        int joyCount = 0;
        int loveCount = 0;
        int sadnessCount = 0;
        int surpriseCount = 0;

        for (int i=0; i < numEmotions; i++) {
            EmotionRecord currentEmotion = emotionRecordList.get(i);
            String emotionType = currentEmotion.getEmotionType();

            if (emotionType.equals("Anger")) {
                ++angerCount;
            }

            if (emotionType.equals("Fear")) {
                ++fearCount;
            }

            if (emotionType.equals("Joy")) {
                ++joyCount;
            }

            if (emotionType.equals("Love")) {
                ++loveCount;
            }

            if (emotionType.equals("Sadness")) {
                ++sadnessCount;
            }

            if (emotionType.equals("Surprise")) {
                ++surpriseCount;
            }
        }

        String message = "Anger: " + String.valueOf(angerCount)
                + "\nFear: " + String.valueOf(fearCount)
                + "\nJoy: " + String.valueOf(joyCount)
                + "\nLove: " + String.valueOf(loveCount)
                + "\nSadness: " + String.valueOf(sadnessCount)
                + "\nSurprise: " + String.valueOf(surpriseCount);

        displayCountDialog(message);
    }

    private void displayCountDialog(String message) {
        Bundle argumentsBundle = new Bundle();
        argumentsBundle.putString("countMessageKey", message);

        DialogFragment countFragment = new EmotionCountDialog();
        countFragment.setArguments(argumentsBundle);
        FragmentManager manager = getSupportFragmentManager();
        countFragment.show(manager, "Emotion count");

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