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

import java.util.ArrayList;

import org.incava.xumoqi.games.GameParams;
import org.incava.xumoqi.games.Query;
import org.incava.xumoqi.games.QueryList;
import org.incava.xumoqi.games.Response;
import org.incava.xumoqi.games.Results;
import org.incava.xumoqi.utils.Constants;
import org.incava.xumoqi.utils.Util;
import org.incava.xumoqi.words.Word;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TableLayout;

public class StatusActivity extends Activity {
    private GameParams gameParams = null;
    private Query query = null;
    private QueryList queries = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        
        Intent intent = getIntent();
        
        gameParams = intent.getParcelableExtra(Constants.GAME_PARAMS);
        // Util.log("STATUS", "gameParams", gameParams);
        
        queries = intent.getParcelableExtra(Constants.QUERIES);
        log("create.queries", queries);
        
        query = queries.getQuery(-1);
        log("create.query", query);
        
        Word queryWord = query.getWord();
        Util.log("STATUS", "queryWord", queryWord);
        Util.log("STATUS", "queryWord.dotIndex", queryWord.getDotIndex());
        
        String duration = intent.getStringExtra(Constants.DURATION);
        Util.log("STATUS", "duration", duration);
        
        String inputString = intent.getStringExtra(Constants.INPUT_STRING);
        ArrayList<String> matching = intent.getStringArrayListExtra(Constants.MATCHING);
        Response response = new Response(queryWord, inputString);
        Results results = new Results(matching, response.getAll());
        log("create.results", results);
        
        query.addResults(results);
        log("create.query", query);

        log("create(2).queries", queries);

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
        intent.putExtra(Constants.GAME_PARAMS, gameParams);
        
        intent.putExtra(Constants.QUERIES, queries);
        log("next.queries", queries);

        startActivity(intent);
    }
    
    private void log(String what, Object obj) {
    	Util.log(getClass(), what, obj);
    }
    
    private void log(String what, String str) {
    	Util.log(getClass(), what, str);
    }
}
