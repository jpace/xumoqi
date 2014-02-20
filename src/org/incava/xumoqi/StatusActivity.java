package org.incava.xumoqi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class StatusActivity extends Activity {
	private GameParams gameParams = null;
	
	private final Map<Matches.StatusType, String> statusToFontColor;

	public StatusActivity() {
		statusToFontColor = new HashMap<Matches.StatusType, String>();
		statusToFontColor.put(Matches.StatusType.CORRECT, "22aa22");	// green
		statusToFontColor.put(Matches.StatusType.INVALID, "eead0e");	// orange (yellow is too light)
		statusToFontColor.put(Matches.StatusType.MISSED,  "aa2222");	// red
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);
		
		Intent intent = getIntent();
		gameParams = intent.getParcelableExtra(Constants.GAME_PARAMS);
		Log.i("STATUS", "gameParams: " + gameParams);
		
		// Word queryWord = intent.getParcelableExtra(Constants.QUERY_WORD);
		// Log.i("STATUS", "queryWord: " + queryWord);

		Matches matchStatus = getMatches();
		Set<String> allWords = matchStatus.getAllWords();
		
		TreeSet<String> correct = new TreeSet<String>();
		TreeSet<String> invalid = new TreeSet<String>();
		TreeSet<String> missed = new TreeSet<String>();
		
		for (String word : allWords) {
			Matches.StatusType st = matchStatus.getStatus(word);
			switch (st) {
			case CORRECT:
				correct.add(word);
				break;
			case INVALID:
				invalid.add(word);
				break;
			case MISSED:
				missed.add(word);
				break;
			}
		}

		setCells(correct, 0, statusToFontColor.get(Matches.StatusType.CORRECT));
		setCells(invalid, 1, statusToFontColor.get(Matches.StatusType.INVALID));
		setCells(missed,  2, statusToFontColor.get(Matches.StatusType.MISSED));
	}

	private void setCells(Set<String> words, int column, String color) {
		int row = 0;
		for (String word : words) {
			setCell(row, column, word, color);
			++row;
		}
	}
	
	private TableLayout getTableLayout() {
		return (TableLayout)findViewById(R.id.statusTable);
	}
	
	private TableRow createRow() {
    	TableLayout tableLayout = getTableLayout();
		
		TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				0,
				1.0f);
		
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
			cell.requestLayout();
			cell.setGravity(Gravity.CENTER);
			cell.setTextSize(20);	// must match the hard-coded value in activity_status.xml
			row.addView(cell, cellParams);
		}
	}
	
	private void setCell(int rowNum, int cellNum, String value, String color) {
    	TableLayout tableLayout = getTableLayout();
	    TableRow row = (TableRow)tableLayout.getChildAt(rowNum);
		if (row == null) {
			row = createRow();
		}
	    
	    TextView cell = (TextView)row.getChildAt(cellNum);

    	cell.setText(value);
    	// cell.setBackgroundColor(Color.parseColor("#" + color));
    	cell.setTextColor(Color.parseColor("#" + color));
	}
	
	private Matches getMatches() {
		Intent intent = getIntent();
		String inputString = intent.getStringExtra(Constants.INPUT_STRING);
		ArrayList<String> matching = intent.getStringArrayListExtra(Constants.MATCHING);
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
    	intent.putExtra(Constants.GAME_PARAMS, gameParams);
    	startActivity(intent);
	}
}
