package ca.ualberta.cs.feelsbook;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

public class FeelsBookActivity extends Activity {

	public static final String RECORD_LIST = "ca.ualberta.cs.feelsbook.RECORDS";
	private EditText bodyText;
	private ListView oldTweetsList;
	ArrayAdapter<String> adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		bodyText = (EditText) findViewById(R.id.body);
		Button clearButton = (Button) findViewById(R.id.clear);
		Button saveButton = (Button) findViewById(R.id.save);
		oldTweetsList = (ListView) findViewById(R.id.oldTweetsList);

		oldTweetsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> selection, View v, int position, long l) {
				//setResult(RESULT_OK);
				//String text = bodyText.getText().toString();
				//saveInFile(text, new Date(System.currentTimeMillis()));
				//finish();

				setResult(RESULT_OK);
				String text = (String) selection.getItemAtPosition(position);
				startHistoryActivity(text);

			}
		});

		saveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				String text = bodyText.getText().toString();
				saveInFile(text, new Date(System.currentTimeMillis()));
				//finish();

			}
		});

		clearButton.setOnClickListener(new View.OnClickListener() {
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
		String[] tweets = loadFromFile();
		adapter = new ArrayAdapter<String>(this, R.layout.list_item, tweets);
		oldTweetsList.setAdapter(adapter);
	}

	private String[] loadFromFile() {
		ArrayList<String> tweets = new ArrayList<String>();
		try {
			FileInputStream fis = openFileInput(getString(R.string.file_name));
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
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
	}

	private void saveInFile(String text, Date date) {
		try {
			FileOutputStream fos = openFileOutput(getString(R.string.file_name), Context.MODE_APPEND);
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
	}

	private void startHistoryActivity(String text) {
		Intent intent = new Intent(this, HistoryActivity.class);
		intent.putExtra(RECORD_LIST, text);
		startActivity(intent);
	}
}

