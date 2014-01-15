package org.incava.xumoqi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.graphics.Color;
import android.inputmethodservice.Keyboard.Row;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.text.Html;

public class ResultsActivity extends Activity {
	private int wordLength = 3;
	private String gameType = null;
	private ArrayList<String> matching = null;
	
	private final Map<Matches.StatusType, String> statusToFontColor;
	
	public ResultsActivity() {
		statusToFontColor = new HashMap<Matches.StatusType, String>();
		statusToFontColor.put(Matches.StatusType.CORRECT, "00aa00");
		statusToFontColor.put(Matches.StatusType.INVALID, "aa0000");
		statusToFontColor.put(Matches.StatusType.MISSED,  "aaaa00");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent intent = getIntent();
		String queryString = intent.getStringExtra(QueryActivity.QUERY_STRING);
		wordLength = queryString.length();
		
		matching = intent.getStringArrayListExtra(QueryActivity.MATCHING);
		Log.i("RESULTS", "matching: " + matching);
		
		// int numDots = 1;
		
		gameType = intent.getStringExtra(MainActivity.GAME_TYPE);
		
		String inputString = intent.getStringExtra(QueryActivity.INPUT_STRING);
		Matches matchStatus = new Matches(matching, inputString);

		StringBuilder sb = new StringBuilder();
		
		Set<String> allWords = matchStatus.getAllWords();
		for (String word : allWords) {
			Matches.StatusType st = matchStatus.getStatus(word);
			String color = statusToFontColor.get(st);
			sb.append("<font color=\"#" + color + "\">" + word + "</font><br/>");
		}
		
		/*
		TextView tv = (TextView)findViewById(R.id.resultsText);
    	tv.setText(Html.fromHtml(sb.toString()));
    	
		 */
    	TableLayout tableLayout = (TableLayout)findViewById(R.id.resultsTable);
    	Log.i("RESULTS", "table.#children: " + tableLayout.getChildCount());
    	tableLayout.removeAllViews();
    	
    	int idx = 0;
    	for (String word : allWords) {
			Matches.StatusType st = matchStatus.getStatus(word);
			String color = statusToFontColor.get(st);
			int rowNum = idx / 2;
			int cellNum = idx % 2;
			setCell(rowNum, cellNum, word, color);
	    	++idx;
    	}
	}
	
	private void setCell(int rowNum, int cellNum, String value, String color) {
		Log.i("RESULTS", "rowNum: " + rowNum);
		Log.i("RESULTS", "cellNum: " + cellNum);
		
    	TableLayout tableLayout = (TableLayout)findViewById(R.id.resultsTable);
		
	    TableRow row = (TableRow)tableLayout.getChildAt(rowNum);
		Log.i("RESULTS", "row: " + row);
		if (row == null) {
			TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT,
					1.0f); 
			row = new TableRow(this);
			tableLayout.addView(row, rowParams);
			
			TableRow.LayoutParams cellParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT,
					1.0f);
			for (int i = 0; i < 2; ++i) {
				TextView cell = new TextView(this);
				row.addView(cell, cellParams);
			}
		}
	    
	    TextView cell = (TextView)row.getChildAt(cellNum);
		Log.i("RESULTS", "cell: " + cell);

    	cell.setText(value);
    	cell.setTextColor(Color.parseColor("#" + color));
	}
	
	public void onClickNext(View view) {
    	Intent intent = new Intent(this, QueryActivity.class);
    	intent.putExtra(MainActivity.WORD_LENGTH, wordLength);
    	intent.putExtra(MainActivity.GAME_TYPE, gameType);
    	startActivity(intent);
	}

	public void onClickQuit(View view) {
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.results, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
