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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FeelsBookActivity extends Activity {

	private final int maxCommentLength = 140;
	private EditText bodyText;
	private ArrayList<EmotionRecord> emotionRecordList;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		bodyText = (EditText) findViewById(R.id.body);
		Button historyButton = (Button) findViewById(R.id.viewHistory);

		Button angerButton = (Button) findViewById(R.id.angerButton);
		Button fearButton = (Button) findViewById(R.id.fearButton);
		Button joyButton = (Button) findViewById(R.id.joyButton);
		Button loveButton = (Button) findViewById(R.id.loveButton);
		Button sadnessButton = (Button) findViewById(R.id.sadnessButton);
		Button surpriseButton = (Button) findViewById(R.id.surpriseButton);

		angerButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String text = bodyText.getText().toString();
				if (text.length() > maxCommentLength) {
					notifyCommentLength();
				}
				else {
					EmotionRecord newRecord = new Anger(text, new Date(System.currentTimeMillis()));
					emotionRecordList.add(newRecord);
					saveInFile();
					notifyEmotionSaved(newRecord.getEmotionType());
					setResult(RESULT_OK);
				}
			}
		});

		fearButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String text = bodyText.getText().toString();
				if (text.length() > maxCommentLength) {
					notifyCommentLength();
				}
				else {
					EmotionRecord newRecord = new Fear(text, new Date(System.currentTimeMillis()));
					emotionRecordList.add(newRecord);
					saveInFile();
					notifyEmotionSaved(newRecord.getEmotionType());
					setResult(RESULT_OK);
				}
			}
		});

		joyButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String text = bodyText.getText().toString();
				if (text.length() > maxCommentLength) {
					notifyCommentLength();
				}
				else {
					EmotionRecord newRecord = new Joy(text, new Date(System.currentTimeMillis()));
					emotionRecordList.add(newRecord);
					saveInFile();
					notifyEmotionSaved(newRecord.getEmotionType());
					setResult(RESULT_OK);
				}
			}
		});

		loveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String text = bodyText.getText().toString();
				if (text.length() > maxCommentLength) {
					notifyCommentLength();
				}
				else {
					EmotionRecord newRecord = new Love(text, new Date(System.currentTimeMillis()));
					emotionRecordList.add(newRecord);
					saveInFile();
					notifyEmotionSaved(newRecord.getEmotionType());
					setResult(RESULT_OK);
				}
			}
		});

		sadnessButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String text = bodyText.getText().toString();
				if (text.length() > maxCommentLength) {
					notifyCommentLength();
				}
				else {
					EmotionRecord newRecord = new Sadness(text, new Date(System.currentTimeMillis()));
					emotionRecordList.add(newRecord);
					saveInFile();
					notifyEmotionSaved(newRecord.getEmotionType());
					setResult(RESULT_OK);
				}
			}
		});

		surpriseButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String text = bodyText.getText().toString();
				if (text.length() > maxCommentLength) {
					notifyCommentLength();
				}
				else {
					EmotionRecord newRecord = new Surprise(text, new Date(System.currentTimeMillis()));
					emotionRecordList.add(newRecord);
					saveInFile();
					notifyEmotionSaved(newRecord.getEmotionType());
					setResult(RESULT_OK);
				}
			}
		});

		historyButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				String text = bodyText.getText().toString();
				startHistoryActivity(text);
			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		loadFromFile();
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
            e.printStackTrace();
            emotionRecordList = new ArrayList<EmotionRecord>();
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

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void notifyCommentLength() {
		Context context = getApplicationContext();
		CharSequence message = getString(R.string.message_too_long);
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, message, duration);
		toast.show();
	}

	private void notifyEmotionSaved(String emotion) {
		Context context = getApplicationContext();
		CharSequence message = emotion + getString(R.string.record_saved);
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, message, duration);
		toast.show();
	}

	private void startHistoryActivity(String text) {
		Intent intent = new Intent(this, HistoryActivity.class);
		startActivity(intent);
	}
}