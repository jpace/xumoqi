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

import org.incava.xumoqi.QueryActivity;
import org.incava.xumoqi.android.EnterableEditText;
import org.incava.xumoqi.game.Game;
import org.incava.xumoqi.query.Query;

import android.content.res.Resources;
import android.widget.TextView;

public class QueryUI {
    public static void setUp(QueryActivity qa, Game game) {
        Resources resources = qa.getResources();
        Query query = game.getNextQuery(resources);
        EnterableEditText.setupEditText(qa, qa, qa.getInputTextView());
        TextView tv = qa.getQueryTextView();
        setQueryText(query, tv);
    }

    public static void setQueryText(Query query, TextView tv) {
        String queryStr = query.getQueryString();
        tv.setText(queryStr);
    }
}
