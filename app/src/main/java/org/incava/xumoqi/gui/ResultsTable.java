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

  This program includes code from the GPL program:
  http://sourceforge.net/projects/scrabbledict/
*/

package org.incava.xumoqi.gui;

import java.util.Set;

import org.incava.xumoqi.query.Results;
import org.incava.xumoqi.util.Lo;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ResultsTable {
    private final static int NUM_COLUMNS = 3;

    private final Activity activity;
    private final TableLayout tableLayout;

    public ResultsTable(Activity activity, TableLayout tableLayout) {
        this.tableLayout = tableLayout;
        this.activity = activity;
    }

    public void set(Results results) {
        setCell(0, StatusType.CORRECT, "correct");
        setCell(0, StatusType.INCORRECT, "invalid");
        setCell(0, StatusType.MISSED, "missed");
        
        setCells(results, StatusType.CORRECT);
        setCells(results, StatusType.INCORRECT);
        setCells(results, StatusType.MISSED);

        if (results.isCorrect()) {
            int nRows = tableLayout.getChildCount();
            Lo.g("nRows", nRows);
            for (int rn = 0; rn < nRows; ++rn) {
                TableRow row = (TableRow) tableLayout.getChildAt(rn);
                for (int cn = 0; cn < NUM_COLUMNS; ++cn) {
                    TextView cell = (TextView) row.getChildAt(cn);
                    setCellColor(cell, StatusType.CORRECT);
                }
            }
        }
    }

    private void setCellColor(TextView cell, StatusType statusType) {
        cell.setBackgroundColor(statusType.getColor());
    }

    public void setCells(Results results, StatusType statusType) {
        Set<String> words = results.getWords(statusType);
        int row = 1;
        for (String word : words) {
            setCell(row, statusType, word);
            ++row;
        }
    }
    
    private void setCell(int rowNum, StatusType statusType, String value) {
        TableRow row = (TableRow)tableLayout.getChildAt(rowNum);
        if (row == null) {
            row = createRow();
        }
        
        TextView cell = (TextView)row.getChildAt(statusType.getColumn());

        cell.setText(value);
        // cell.setTextColor(statusType.getColor());
    }
    
    private TableRow createRow() {
        TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f);
        TableRow row = new TableRow(activity);        
        row.requestLayout();
        tableLayout.addView(row, rowParams);
        createCells(row);
        return row;
    }

    private void createCells(TableRow row) {
        // must match the hard-coded value in activity_status.xml  
        final int textSize = 20;
        final int marginSize = 1;
        TableUtil.createCells(activity, row, NUM_COLUMNS, textSize, marginSize);
    }
}
