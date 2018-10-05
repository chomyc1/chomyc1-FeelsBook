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


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FeelsBookActivity extends Activity {

	public static final String RECORD_LIST = "ca.ualberta.cs.feelsbook.RECORDS";
	private EditText bodyText;
	private ListView recordsListView;
	private ArrayAdapter<EmotionRecord> adapter;
	private ArrayList<EmotionRecord> emotionRecordList;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		bodyText = (EditText) findViewById(R.id.body);
		Button saveButton = (Button) findViewById(R.id.save);
		Button historyButton = (Button) findViewById(R.id.viewHistory);
		recordsListView = (ListView) findViewById(R.id.oldTweetsList);

		recordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> selection, View v, int position, long l) {
				//setResult(RESULT_OK);
				//String text = bodyText.getText().toString();
				//saveInFile(text, new Date(System.currentTimeMillis()));
				//finish();

				setResult(RESULT_OK);
                EmotionRecord clickedRecord = (EmotionRecord) selection.getItemAtPosition(position);
				String text = clickedRecord.toString();
				startHistoryActivity(text);
			}
		});

		saveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				String text = bodyText.getText().toString();
				if (text.length() > 140) {
					Context context = getApplicationContext();
					CharSequence message = getString(R.string.message_too_long);
					int duration = Toast.LENGTH_LONG;
					Toast toast = Toast.makeText(context, message, duration);
					toast.show();
				}
				else {
					EmotionRecord newRecord = new Sadness(text, new Date(System.currentTimeMillis()));
					emotionRecordList.add(newRecord);
					adapter.notifyDataSetChanged();
					saveInFile();

					Context context = getApplicationContext();
					CharSequence message = getString(R.string.record_saved);
					int duration = Toast.LENGTH_LONG;
					Toast toast = Toast.makeText(context, message, duration);
					toast.show();
				}
			}
		});

		historyButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				String text = bodyText.getText().toString();
				startHistoryActivity(text);
				//finish();
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

	private void startHistoryActivity(String text) {
		Intent intent = new Intent(this, HistoryActivity.class);
		intent.putExtra(RECORD_LIST, text);
		startActivity(intent);
	}
}

	/*
	private EmotionRecord[] loadFromFile() {
		ArrayList<EmotionRecord> tweets = new ArrayList<EmotionRecord>();
		try {
			FileInputStream fis = openFileInput(getString(R.string.file_name));
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			Gson jsonConverter = new Gson();
			String line = in.readLine();
			while (line != null) {
				tweets.add(line);
				line = in.readLine();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tweets.toArray(new String[tweets.size()]);
	}*/

	/*	private void saveInFile(String text, Date date) {
		try {
			FileOutputStream fos = openFileOutput(getString(R.string.file_name), Context.MODE_APPEND); // Replace this with file_name!
			String saveString = date.toString() + " | " + text;
			saveString += "\n";
			fos.write(saveString.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/