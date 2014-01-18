package org.incava.xumoqi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class ResultsActivity extends Activity {
	private GameParams gameParams = null;
	
	private final Map<Matches.StatusType, String> statusToFontColor;
	
	public ResultsActivity() {
		statusToFontColor = new HashMap<Matches.StatusType, String>();
		statusToFontColor.put(Matches.StatusType.CORRECT, "00aa00");
		statusToFontColor.put(Matches.StatusType.INVALID, "aa0000");
		statusToFontColor.put(Matches.StatusType.MISSED,  "eeee00");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		
		// Show the Up button in the action bar.
		setupActionBar();
		
		int wordLength = getWordLength();
		String gameType = getGameType();
		gameParams = new GameParams(wordLength, gameType);
		
		Matches matchStatus = getMatches();
		Set<String> allWords = matchStatus.getAllWords();
		
    	TableLayout tableLayout = (TableLayout)findViewById(R.id.resultsTable);
    	tableLayout.removeAllViews();
    	
    	for (int i = 0; i < 16; ++i) {
    		createRow();
    	}
    	
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
	
	private int getWordLength() {
		Intent intent = getIntent();
		String queryString = intent.getStringExtra(QueryActivity.QUERY_STRING);
		return queryString.length();
	}
	
	private Matches getMatches() {
		Intent intent = getIntent();
		String inputString = intent.getStringExtra(QueryActivity.INPUT_STRING);
		ArrayList<String> matching = intent.getStringArrayListExtra(QueryActivity.MATCHING);
		return new Matches(matching, inputString);
	}
	
	private String getGameType() {
		Intent intent = getIntent();
		return intent.getStringExtra(MainActivity.GAME_TYPE);
	}
	
	private TableRow createRow() {
    	TableLayout tableLayout = (TableLayout)findViewById(R.id.resultsTable);
		
		TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT,
				1.0f);
		rowParams.setMargins(2, 2, 2, 2);
		
		TableRow row = new TableRow(this);
		row.requestLayout();
		// row.setBackgroundColor(Color.parseColor("#ffefbf"));
		tableLayout.addView(row, rowParams);
		createCells(row);
		return row;
	}
	
	private void createCells(TableRow row) {
		TableRow.LayoutParams cellParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT,
				1.0f);
		cellParams.setMargins(1, 1, 1, 1);
		
		for (int i = 0; i < 2; ++i) {
			TextView cell = new TextView(this);
			cell.setPadding(2, 2, 2, 2);
			cell.requestLayout();
			row.addView(cell, cellParams);
		}
	}
	
	private void setCell(int rowNum, int cellNum, String value, String color) {
    	TableLayout tableLayout = (TableLayout)findViewById(R.id.resultsTable);
	    TableRow row = (TableRow)tableLayout.getChildAt(rowNum);
		if (row == null) {
			row = createRow();
		}
	    
	    TextView cell = (TextView)row.getChildAt(cellNum);

    	cell.setText(value);
    	cell.setBackgroundColor(Color.parseColor("#" + color));
    	cell.setTextColor(Color.parseColor("#333333"));
	}
	
	public void onClickNext(View view) {
    	Intent intent = new Intent(this, QueryActivity.class);
    	intent.putExtra(MainActivity.WORD_LENGTH, gameParams.getWordLength());
    	intent.putExtra(MainActivity.GAME_TYPE, gameParams.getGameType());
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
