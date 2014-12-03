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

import org.incava.xumoqi.query.Results;
import org.incava.xumoqi.util.Lo;

import android.graphics.Color;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ScoreBar {

    public ScoreBar() {
        // TODO Auto-generated constructor stub
    }
    
    public static void setScoresTable(Results results, TableLayout tableLayout) {
        Lo.g("tableLayout", tableLayout);
        
        TableRow scoreRow = (TableRow)tableLayout.getChildAt(0);
        Lo.g("scoreRow", scoreRow);

        int column = 0;
        setCell(scoreRow, StatusType.CORRECT,   column++, results.getCorrectCount());
        setCell(scoreRow, StatusType.INCORRECT, column++, results.getInvalidCount());
        setCell(scoreRow, StatusType.MISSED,    column++, results.getMissedCount());
    }
    
    private static void setCell(TableRow row, StatusType statusType, int column, int value) {
        TextView cell = (TextView)row.getChildAt(column);
        cell.setText(String.valueOf(value));
        cell.setTextColor(Color.WHITE);
        cell.setBackgroundColor(Color.parseColor(statusType.getColor()));
    }
}
