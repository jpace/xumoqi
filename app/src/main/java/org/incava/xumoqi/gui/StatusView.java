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

import org.incava.xumoqi.R;
import org.incava.xumoqi.query.Query;
import org.incava.xumoqi.query.Results;
import org.incava.xumoqi.util.Lo;

import android.app.Activity;
import android.widget.TableLayout;
import android.widget.TextView;

public class StatusView {
    public static void setUp(Activity activity, Query query, String inputText, long duration) {
        String hint = query.getHint();
        Lo.g("hint", hint);

        activity.setContentView(hint == null ? R.layout.activity_status : R.layout.activity_status_hint);

        Results results = query.addResults(inputText);

        TableLayout scoreTableLayout = (TableLayout)activity.findViewById(R.id.scoreTable);
        ScoreBar.setScoresTable(results, activity, scoreTableLayout);

        TableLayout statusTableLayout = (TableLayout)activity.findViewById(R.id.statusTable);
        ResultsTable rt = new ResultsTable(activity, statusTableLayout);

        TextView tv = (TextView)activity.findViewById(R.id.hintTextView);
        if (tv != null) {
            tv.setText(hint);
        }

        rt.set(results);
    }
}
