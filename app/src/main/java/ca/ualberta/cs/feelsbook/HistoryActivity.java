package ca.ualberta.cs.feelsbook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class HistoryActivity extends FragmentActivity implements DeleteHistoryDialog.DeleteHistoryDialogListener {

    public static final String SELECTED_MESSAGE = "ca.ualberta.cs.feelsbook.RECORD_MESSAGE";
    public static final String SELECTED_DATE = "ca.ualberta.cs.feelsbook.RECORD_DATE";
    private final int REQUEST_CODE = 1;
    private ListView recordsListView;
    private ArrayAdapter<EmotionRecord> adapter;
    private ArrayList<EmotionRecord> emotionRecordList;
    private EmotionRecord selectedRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Button emotionCountButton = (Button) findViewById(R.id.countButton);
        Button deleteHistoryButton = (Button) findViewById(R.id.deleteHistoryButton);
        recordsListView = (ListView) findViewById(R.id.recordsList);

        emotionCountButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                showEmotionCount();
            }
        });

        deleteHistoryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                deleteHistoryPrompt();
            }
        });

        recordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> selection, View v, int position, long l) {
                setResult(RESULT_OK);
                selectedRecord = (EmotionRecord) selection.getItemAtPosition(position);
                startEditRecordActivity();
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

    private void deleteHistoryPrompt() {
        DialogFragment deleteFragment = new DeleteHistoryDialog();
        FragmentManager manager = getSupportFragmentManager();
        deleteFragment.show(manager, "Delete history");
    }

    @Override
    public void deleteDialogConfirmed(DialogFragment dialog) {
        try {
            FileOutputStream fos = openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson jsonConverter = new Gson();

            emotionRecordList.clear();
            adapter.clear();
            adapter.notifyDataSetChanged();

            jsonConverter.toJson(emotionRecordList, out);
            out.flush();
            out.close();
            fos.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void startEditRecordActivity() {
        Intent intent = new Intent(this, EditRecordActivity.class);
        intent.putExtra(SELECTED_MESSAGE, selectedRecord.getMessage());
        intent.putExtra(SELECTED_DATE, selectedRecord.getDate());
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            boolean delete = data.getBooleanExtra("delete", false);
            if (delete) {
                emotionRecordList.remove(selectedRecord);
                saveInFile();
                adapter.notifyDataSetChanged();
                return;
            }

            int test = emotionRecordList.size();
            String newMessage = data.getStringExtra("editedMessage");
            Date newDate = (Date) data.getSerializableExtra("editedDate");
            selectedRecord.setMessage(newMessage);
            selectedRecord.setDate(newDate);
            /*sort(); I did not implement sorting; ran out of time.*/

            saveInFile();
            adapter.notifyDataSetChanged();

            Context context = getApplicationContext();
            CharSequence message = getString(R.string.record_edited);
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, message, duration);
            toast.show();
        }
    }


    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson jsonConverter = new Gson();

            jsonConverter.toJson(emotionRecordList, out);
            out.flush();
            out.close();
            fos.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}