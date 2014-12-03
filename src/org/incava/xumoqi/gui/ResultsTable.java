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

package org.incava.xumoqi.gui;

import java.util.Set;

import org.incava.xumoqi.query.Results;

import android.app.Activity;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ResultsTable {
    private final static int NUM_COLUMNS = 3;
    public final static String CORRECT_COLOR = "#22aa22";    // green
    public final static String INCORRECT_COLOR = "#eead0e";  // orange (yellow is too light)
    public final static String MISSED_COLOR = "#aa2222";     // red

    private final Activity activity;
    private final TableLayout tableLayout;

    public ResultsTable(Activity activity, TableLayout tableLayout) {
        this.tableLayout = tableLayout;
        this.activity = activity;
    }

    public void set(Results matches) {
        setCell(0, 0, "correct", StatusType.CORRECT.getColor());
        setCell(0, 1, "invalid", StatusType.INCORRECT.getColor());
        setCell(0, 2, "missed",  StatusType.MISSED.getColor());
        
        setCells(0, matches.getCorrect(), StatusType.CORRECT.getColor());
        setCells(1, matches.getInvalid(), StatusType.INCORRECT.getColor());
        setCells(2, matches.getMissed(),  StatusType.MISSED.getColor());
    }
    
    public void setCells(int column, Set<String> words, String color) {
        int row = 1;
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
        cell.setTextColor(Color.parseColor(color));
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
        // must match the hard-coded value in activity_status.xml  
        final int textSize = 20;
        TableUtil.createCells(activity, row, NUM_COLUMNS, textSize);
    }
}
