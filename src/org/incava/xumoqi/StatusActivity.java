package org.incava.xumoqi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class StatusActivity extends Activity {
	private GameParams gameParams = null;
	
	private final Map<Matches.StatusType, String> statusToFontColor;

	public StatusActivity() {
		statusToFontColor = new HashMap<Matches.StatusType, String>();
		statusToFontColor.put(Matches.StatusType.CORRECT, "00aa00");
		statusToFontColor.put(Matches.StatusType.INVALID, "aa0000");
		statusToFontColor.put(Matches.StatusType.MISSED,  "aaaa00");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);
		
		Intent intent = getIntent();
		gameParams = new GameParams(intent);

		Matches matchStatus = getMatches();
		Set<String> allWords = matchStatus.getAllWords();
		
    	// TableLayout tableLayout = (TableLayout)findViewById(R.id.statusTable);
    	
    	int idx = 0;
    	int nwords = allWords.size();
    	int midpt = (nwords + 1) / 2;
    	
    	for (String word : allWords) {
			Matches.StatusType st = matchStatus.getStatus(word);
			String color = statusToFontColor.get(st);
			int rowNum = idx % midpt;
			int cellNum = idx < midpt ? 0 : 1;
			setCell(rowNum, cellNum, word, color);
	    	++idx;
    	}
	}
	
	private void log(String str, Object val) {
		Log.i("STATUS", str + ": " + val);
	}

	private void setCell(int rowNum, int cellNum, String value, String color) {
    	TableLayout tableLayout = (TableLayout)findViewById(R.id.statusTable);
	    TableRow row = (TableRow)tableLayout.getChildAt(rowNum);
	    TextView cell = (TextView)row.getChildAt(cellNum);

    	cell.setText(value);
    	// cell.setBackgroundColor(Color.parseColor("#334499"));
    	cell.setTextColor(Color.parseColor("#" + color));
	}

	private Matches getMatches() {
		Intent intent = getIntent();
		String inputString = intent.getStringExtra(QueryActivity.INPUT_STRING);
		ArrayList<String> matching = intent.getStringArrayListExtra(QueryActivity.MATCHING);
		return new Matches(matching, inputString);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.status, menu);
		return true;
	}

	public void onClickQuit(View view) {
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
	}

	public void onClickNext(View view) {
    	Intent intent = new Intent(this, QueryActivity.class);
    	intent.putExtra(MainActivity.WORD_LENGTH, gameParams.getWordLength());
    	intent.putExtra(MainActivity.GAME_TYPE, gameParams.getGameType());
    	startActivity(intent);
	}
}
