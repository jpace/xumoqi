/*
  XuMoQi - word game program
  Copyright (C) 2014  Jeff Pace

  This program is free software; you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation; either version 2 of the License, or
  (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License along
  with this program; if not, write to the Free Software Foundation, Inc.,
  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.

  See https://raw.github.com/jpace/xumoqi/master/LICENSE for the full license.

  The full source code for this program is available at:
  http://github.com/jpace/xumoqi

  This program includes code from the GPL'd program:
  http://sourceforge.net/projects/scrabbledict/
*/

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
	private final static int NUM_COLUMNS = 3;
	
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
		
		Matches matches = getMatches();
		
		setForStatus(matches, 0, Matches.StatusType.CORRECT);
		setForStatus(matches, 1, Matches.StatusType.INVALID);
		setForStatus(matches, 2, Matches.StatusType.MISSED);
	}
	
	private void setForStatus(Matches matches, int column, Matches.StatusType statusType) {
		TreeSet<String> forStatus = matches.getForStatus(statusType);
		setCells(forStatus, column, statusToFontColor.get(statusType));
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
		TableRow.LayoutParams cellParams = new TableRow.LayoutParams(0,
				ViewGroup.LayoutParams.WRAP_CONTENT,
				1.0f);
		cellParams.setMargins(1, 1, 1, 1);
		
		for (int i = 0; i < NUM_COLUMNS; ++i) {
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

	public void onClickRestart(View view) {
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
	}

	public void onClickNext(View view) {
    	Intent intent = new Intent(this, QueryActivity.class);
    	intent.putExtra(Constants.GAME_PARAMS, gameParams);
    	startActivity(intent);
	}
}
