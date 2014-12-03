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
    public static void setScoresTable(Results results, TableLayout tableLayout) {
        Lo.g("tableLayout", tableLayout);
        
        TableRow scoreRow = (TableRow)tableLayout.getChildAt(0);
        Lo.g("scoreRow", scoreRow);

        setCell(scoreRow, results, StatusType.CORRECT);
        setCell(scoreRow, results, StatusType.INCORRECT);
        setCell(scoreRow, results, StatusType.MISSED);
    }
    
    private static void setCell(TableRow row, Results results, StatusType statusType) {
        TextView cell = (TextView)row.getChildAt(statusType.getColumn());
        int count = results.getCount(statusType);
        cell.setText(String.valueOf(count));
        cell.setTextColor(Color.WHITE);
        cell.setBackgroundColor(statusType.getColor());
    }
}
