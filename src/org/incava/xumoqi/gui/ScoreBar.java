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
import org.incava.xumoqi.query.Score;
import org.incava.xumoqi.util.Lo;

import android.app.Activity;
import android.graphics.Color;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ScoreBar {
    public static void setScoresTable(Results results, Activity activity, TableLayout tableLayout) {
        Lo.g("tableLayout", tableLayout);
        
        TableRow row = (TableRow)tableLayout.getChildAt(0);
        Lo.g("row", row);
        
        row.removeAllViews();

        Score correctScore = results.createScore(StatusType.CORRECT);
        Score incorrectScore = results.createScore(StatusType.INCORRECT);
        Score missedScore = results.createScore(StatusType.MISSED);
        
        setScoresTable(activity, row, correctScore, incorrectScore, missedScore);
    }
 
    public static void setScoresTable(Activity activity, TableRow row, Score correctScore, Score incorrectScore, Score missedScore) {
        Lo.g("correctScore", correctScore);
        Lo.g("incorrectScore", incorrectScore);
        Lo.g("missedScore", missedScore);
        
        int nCells = 20;
        createCells(activity, row, nCells);
        for (int column = 0; column < nCells; ++column) {
            setCell(row, column, Color.BLACK, "");
        }
        
        int currCells = 0;
        currCells += setScoreCells(row, correctScore, nCells, currCells);
        currCells += setScoreCells(row, incorrectScore, nCells, currCells);
        currCells += setScoreCells(row, missedScore, nCells, currCells);
    }
    
    private static int setScoreCells(TableRow row, Score score, int nCells, int offset) {
        Lo.g("score", score);
        double pct = score.asPercentage();
        Lo.g("pct", pct);
        int scoreCells = (int)(pct * nCells);
        Lo.g("scoreCells", scoreCells);
        setCells(row, score, scoreCells, offset);
        return scoreCells;
    }
    
    private static void setCells(TableRow row, Score score, int numColumns, int offset) {
        StatusType statusType = score.getStatusType();
        for (int col = 0; col < numColumns; ++col) {
            String text = col == 0 ? String.valueOf(score.getCount()) : "";
            setCell(row, offset + col, statusType.getColor(), text);
        }
    }
    
    private static void setCell(TableRow row, int column, int backgroundColor, String text) {
        Lo.g("column", column);
        Lo.g("text", text);
        TextView cell = (TextView)row.getChildAt(column);
        cell.setText(text);
        cell.setTextColor(Color.WHITE);
        cell.setBackgroundColor(backgroundColor);
    }

    private static void createCells(Activity activity, TableRow row, int numColumns) {
        final int textSize = 20;
        TableUtil.createCells(activity, row, numColumns, textSize, 0);
    }
}
