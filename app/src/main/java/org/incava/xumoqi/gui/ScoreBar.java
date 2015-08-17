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

import org.incava.xumoqi.query.Results;
import org.incava.xumoqi.query.Score;

import android.app.Activity;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ScoreBar {
    public static void setScoresTable(Results results, Activity activity, TableLayout tableLayout) {
        TableRow row = (TableRow)tableLayout.getChildAt(0);
        
        row.removeAllViews();
        
        StatusType[] stati = new StatusType[] { StatusType.CORRECT, StatusType.INCORRECT, StatusType.MISSED };
        for (StatusType status : stati) {
            Score score = results.createScore(status);
            createScoreCell(activity, row, score);
        }
    }
 
    private static void createScoreCell(Activity activity, TableRow row, Score score) {
        StatusType statusType = score.getStatusType();
        int backgroundColor = statusType.getColor();
        float pct = (float)score.asPercentage();
        String text = String.valueOf(score.getCount());
        // pct == initWeight:
        if (pct > 0.0f) {
            createCell(activity, row, pct, text, backgroundColor);
        }
    }
    
    private static void createCell(Activity activity, TableRow row, float initWeight, String text, int backgroundColor) {
        final int textSize = 20;
        int width = 0;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        int marginSize = 0;
        TableRow.LayoutParams cellParams = new TableRow.LayoutParams(width, height, initWeight);
        cellParams.setMargins(marginSize, marginSize, marginSize, marginSize);
        
        TextView cell = TableUtil.createCell(activity, row, textSize, cellParams);
        cell.setText(text);
        cell.setTextColor(Color.WHITE);
        cell.setBackgroundColor(backgroundColor);
    }
}
