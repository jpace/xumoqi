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

package org.incava.xumoqi;

import org.incava.xumoqi.games.Game;
import org.incava.xumoqi.games.GameParameters;
import org.incava.xumoqi.gui.ResultsTable;
import org.incava.xumoqi.query.Query;
import org.incava.xumoqi.query.Results;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TableLayout;

public class StatusActivity extends Activity {
    private Game gameIterations = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        
        Intent intent = getIntent();
        gameIterations = GameParameters.getGameIterations(intent);
        
        Query query = gameIterations.getCurrentQuery();
        String inputText = GameParameters.getInputText(intent);
        
        // long duration = GameParameters.getDuration(intent);
        
        Results results = query.addResults(inputText);

        TableLayout tableLayout = (TableLayout)findViewById(R.id.statusTable);
        ResultsTable rt = new ResultsTable(this, tableLayout);

        rt.set(results);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.status, menu);
        return true;
    }

    public void onClickRestart(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickNext(View view) {
        Intent intent = new Intent(this, QueryActivity.class);
        GameParameters.saveGameIterations(intent, gameIterations);
        startActivity(intent);
    }
}
