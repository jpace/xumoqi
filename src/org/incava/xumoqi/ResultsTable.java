package org.incava.xumoqi;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ResultsTable {
	private final static int NUM_COLUMNS = 3;

	private final Activity activity;
	private final TableLayout tableLayout;
	private final Map<Matches.StatusType, String> statusToFontColor;

	public ResultsTable(Activity activity, TableLayout tableLayout) {
		this.tableLayout = tableLayout;
		this.activity = activity;

		this.statusToFontColor = new HashMap<Matches.StatusType, String>();
		this.statusToFontColor.put(Matches.StatusType.CORRECT, "22aa22");	// green
		this.statusToFontColor.put(Matches.StatusType.INVALID, "eead0e");	// orange (yellow is too light)
		this.statusToFontColor.put(Matches.StatusType.MISSED,  "aa2222");	// red
	}

	public void set(Matches matches) {
		setForStatus(matches, 0, Matches.StatusType.CORRECT);
		setForStatus(matches, 1, Matches.StatusType.INVALID);
		setForStatus(matches, 2, Matches.StatusType.MISSED);
	}
	
	public void setForStatus(Matches matches, int column, Matches.StatusType statusType) {
		TreeSet<String> forStatus = matches.getForStatus(statusType);
		setCells(forStatus, column, statusToFontColor.get(statusType));
	}
	
	public void setCells(Set<String> words, int column, String color) {
		int row = 0;
		for (String word : words) {
			setCell(row, column, word, color);
			++row;
		}
	}
	
	private void setCell(int rowNum, int cellNum, String value, String color) {
	    TableRow row = (TableRow)tableLayout.getChildAt(rowNum);
		if (row == null) {
			row = createRow();
		}
	    
	    TextView cell = (TextView)row.getChildAt(cellNum);

    	cell.setText(value);
    	// cell.setBackgroundColor(Color.parseColor("#" + color));
    	cell.setTextColor(Color.parseColor("#" + color));
	}
	
	private TableRow createRow() {
		TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				0,
				1.0f);
		
		TableRow row = new TableRow(activity);
		
		row.requestLayout();
		// row.setBackgroundColor(Color.parseColor("#ffefbf"));
		tableLayout.addView(row, rowParams);
		createCells(row);
		return row;
	}

	private void createCells(TableRow row) {
		TableRow.LayoutParams cellParams = new TableRow.LayoutParams(0,	// has to be zero width to get the text centered (huh?)
				ViewGroup.LayoutParams.WRAP_CONTENT,
				1.0f);
		cellParams.setMargins(1, 1, 1, 1);
		
		for (int i = 0; i < NUM_COLUMNS; ++i) {
			TextView cell = new TextView(activity);
			cell.requestLayout();
			cell.setGravity(Gravity.CENTER);
			cell.setTextSize(20);	// must match the hard-coded value in activity_status.xml
			row.addView(cell, cellParams);
		}
	}
}
