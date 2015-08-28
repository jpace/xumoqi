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
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ResultsTable {
    private final static int NUM_COLUMNS = 3;
    private final static int BLACK = Color.parseColor("#000000");

    private final Activity activity;
    private final TableLayout tableLayout;

    private final StatusType[] STATUS_TYPES = new StatusType[] {
            StatusType.CORRECT,
            StatusType.INCORRECT,
            StatusType.MISSED
    };

    public ResultsTable(Activity activity, TableLayout tableLayout) {
        this.tableLayout = tableLayout;
        this.activity = activity;
    }

    public void set(Results results) {
        if (results.isCorrect()) {
            setAllCorrect(results);
        }
        else {
            setHeadingRow();
            int startRow = 1;
            for (StatusType statusType : STATUS_TYPES) {
                setCells(startRow, results, statusType, statusType.getColor());
            }
        }
    }

    private void setHeadingRow() {
        int rowNum = 0;
        for (StatusType statusType : STATUS_TYPES) {
            setCell(rowNum, statusType.getColumn(), statusType.toString(), null, statusType.getColor());
        }
    }

    private void setAllCorrect(Results results) {
        int nRows = tableLayout.getChildCount();
        int correctColor = StatusType.CORRECT.getColor();
        Lo.g("nRows", nRows);
        for (int rn = 0; rn < nRows; ++rn) {
            TableRow row = getRow(rn);
            for (int cn = 0; cn < NUM_COLUMNS; ++cn) {
                TextView cell = getCell(row, cn);
                cell.setBackgroundColor(correctColor);
            }
        }
        setCells(0, results, StatusType.CORRECT, BLACK);
    }

    public void setCells(int startRow, Results results, StatusType statusType, Integer textColor) {
        Set<String> words = results.getWords(statusType);
        int row = startRow;
        for (String word : words) {
            setCell(row, statusType.getColumn(), word, null, textColor);
            ++row;
        }
    }

    private TableRow getRow(int rowNum) {
        return (TableRow) tableLayout.getChildAt(rowNum);
    }

    private TableRow fetchRow(int rowNum) {
        TableRow row = getRow(rowNum);
        return row == null ? createRow() : row;
    }

    private TextView getCell(TableRow row, int columnNum) {
        return (TextView)row.getChildAt(columnNum);
    }

    private void setCell(int rowNum, int columnNum, String value, Integer backgroundColor, Integer textColor) {
        TableRow row = fetchRow(rowNum);
        TextView cell = getCell(row, columnNum);
        setCell(cell, value, backgroundColor, textColor);
    }

    private void setCell(TextView cell, String value, Integer backgroundColor, Integer textColor) {
        cell.setText(value);
        if (backgroundColor != null) {
            cell.setBackgroundColor(backgroundColor);
        }
        if (textColor != null) {
            cell.setTextColor(textColor);
        }
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
