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

        int nCorrect = results.getCount(StatusType.CORRECT);
        int nIncorrect = results.getCount(StatusType.INCORRECT);
        int nMissed = results.getCount(StatusType.MISSED);

        int total = results.getCount();
        Score correctScore = new Score(nCorrect, total);
        Score incorrectScore = new Score(nIncorrect, total);
        Score missedScore = new Score(nMissed, total);
        
        Lo.g("correctScore", correctScore);
        Lo.g("incorrectScore", incorrectScore);
        Lo.g("missedScore", missedScore);
        
        Lo.g("total", total);
        
        int nCells = 20;
        createCells(activity, row, nCells);
        for (int column = 0; column < nCells; ++column) {
            setCell(row, column, Color.BLACK, "");
        }
        
        double correctPct = correctScore.asPercentage();
        double incorrectPct = incorrectScore.asPercentage();
        double missedPct = missedScore.asPercentage();;
        
        Lo.g("correctPct", correctPct);
        Lo.g("incorrectPct", incorrectPct);
        Lo.g("missedPct", missedPct);
        
        int correctCells = (int)(correctPct * nCells);
        int incorrectCells = (int)(incorrectPct * nCells);
        int missedCells = (int)(missedPct * nCells);
        
        Lo.g("correctCells", correctCells);
        Lo.g("incorrectCells", incorrectCells);
        Lo.g("missedCells", missedCells);
        
        setCells(row, correctScore, StatusType.CORRECT, correctCells, 0);
        setCells(row, incorrectScore, StatusType.INCORRECT, incorrectCells, correctCells);
        setCells(row, missedScore, StatusType.MISSED, missedCells, incorrectCells + correctCells);
    }
    
    private static void setCells(TableRow row, Score score, StatusType statusType, int numColumns, int offset) {
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
