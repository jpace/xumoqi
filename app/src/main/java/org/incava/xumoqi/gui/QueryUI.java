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

import org.incava.xumoqi.MainActivity;
import org.incava.xumoqi.QueryActivity;
import org.incava.xumoqi.StatusActivity;
import org.incava.xumoqi.android.EnterableEditText;
import org.incava.xumoqi.game.Game;
import org.incava.xumoqi.game.GameParameters;
import org.incava.xumoqi.game.GameTypeOptions;
import org.incava.xumoqi.query.Query;
import org.incava.xumoqi.util.Lo;

import android.content.Intent;
import android.content.res.Resources;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class QueryUI {
    private final QueryActivity qa;
    private final Game game;
    private long startTime;
    
    public QueryUI(QueryActivity qa) {
        this.qa = qa;
        Intent intent = qa.getIntent();
        this.game = GameParameters.getGame(intent);
        Resources resources = qa.getResources();
        Query query = game.getNextQuery(resources);
        EnterableEditText.setupEditText(qa, qa, qa.getInputTextView());

        TextView qtv = qa.getQueryTextView();
        String queryStr = query.getQueryString();
        qtv.setText(queryStr);

        GameTypeOptions gto = new GameTypeOptions();

        if (gto.showNumMatching()) {
            ArrayList<String> matching = query.getMatching();
            Lo.g("matching", matching);

            int nMatching = matching.size();
            TextView ntv = qa.getNumMatchingTextView();
            ntv.setText(String.valueOf(nMatching) + " word" + (nMatching == 1 ? "" : "s"));
        }
        else {
            TextView ntv = qa.getNumMatchingTextView();
            ntv.setText("");
        }

        startTime = System.currentTimeMillis();
    }

    public void restart() {
        Intent intent = new Intent(qa, MainActivity.class);
        GameParameters.saveGame(intent, game);
        qa.startActivity(intent);
    }

    public void gotoNext() {
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        Lo.g("startTime", startTime);
        Lo.g("endTime  ", endTime);
        Lo.g("duration", duration);
        
        Intent intent = new Intent(qa, StatusActivity.class);
        GameParameters.saveDuration(intent, duration);
        EditText et = qa.getInputTextView();
        String inputText = et.getText().toString();
        GameParameters.saveInputText(intent, inputText);
        GameParameters.saveGame(intent, game);        
        qa.startActivity(intent);
    }
}
