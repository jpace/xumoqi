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
import java.util.List;

import org.incava.xumoqi.games.Game;
import org.incava.xumoqi.games.GameIterations;
import org.incava.xumoqi.games.GameParameters;
import org.incava.xumoqi.games.GameType;
import org.incava.xumoqi.gui.Enterable;
import org.incava.xumoqi.gui.EnterableEditText;
import org.incava.xumoqi.query.Query;
import org.incava.xumoqi.query.QueryList;
import org.incava.xumoqi.query.Response;
import org.incava.xumoqi.utils.*;
import org.incava.xumoqi.words.Word;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class QueryActivity extends Activity implements Enterable {
    private ArrayList<String> matching = null;
    private Timer timer = null;
    private QueryList queries = null;
    private int queryIndex = -1;
    private GameIterations gameIterations = null;
    private Word queryWord = null; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        
        Intent intent = getIntent();

        gameIterations = GameParameters.getGameIterations(intent);
        queries = gameIterations.getQueries();
        
        Lo.g(this, "gameIterations", gameIterations);
        Lo.g(this, "queries", queries);
        
        String queryStr = getNextQuery();
        
        EnterableEditText.setupEditText(this, this, getInputTextView());
        
        TextView tv = getQueryTextView();
        tv.setText(queryStr);
        
        timer = new Timer(getClass(), "");
        timer.done("onCreate");

        logTime("onCreate:currTime");
    }
    
    protected void onStart() {
        // timer.done("onStart");
        logTime("onStart:currTime");
        super.onStart();
    }
    
    public void onEnter() {
        onClickNext(null);
    }

    private void fetchMatching(final Game game) {
        Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // Timer timer = new Timer("QUERY", "getMatching");
                    matching = game.getMatching(queryWord);
                    // Lo.g(this, "fetchMatching.matching", matching);
                    // timer.done();
                }
            });
        thread.start();
    }
    
    private void logTime(String msg) {
        // long currTime = System.currentTimeMillis();
        // Lo.g(this, msg, currTime);
    }
    
    public void onClickNext(View view) {
        timer.done("onClickNext");

        logTime("onClickNext:currTime");

        Intent intent = new Intent(this, StatusActivity.class);

        logTime("onClickNext:currTime2");

        saveDuration(intent);
        saveQuery(intent);
        saveMatching(intent);
        
        startActivity(intent);
    }
    
    public void onClickRestart(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private TextView getQueryTextView() {
        return (TextView)findViewById(R.id.queryText);
    }
    
    private EditText getInputTextView() {
        return (EditText)findViewById(R.id.queryInput);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.query, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getNextQuery() {
        Lo.g(this, "getNextQuery() ............................... ");
        
        Game game = getGame();
        Query randomQuery = queries.getRandomQuery();
        
        int qIdx = queries.indexOf(randomQuery);
        Lo.g(this, "qIdx", qIdx);
        
        List<Integer> prevIndices = gameIterations.getQueryIndices();
        Lo.g(this, "prevIndices", prevIndices);
        
        int prevQueryIndex = ListUtil.get(prevIndices, -1, -1);
        Lo.g(this, "prevQueryIndex", prevQueryIndex);
        
        queryWord = null;

        // don't repeat the previous query:
        if (randomQuery == null || qIdx == prevQueryIndex) {
            queryWord = game.getQueryWord();
            Query newQuery = new Query(queryWord);
            queries.addQuery(newQuery);
            queryIndex = queries.size() - 1;
        }
        else {
            queryIndex = qIdx;
            queryWord = randomQuery.getWord();
        }

        gameIterations.setQueryIndex(queryIndex);
        fetchMatching(game);

        return queryWord.asQuery();
    }

    private Game getGame() {
        // numDots is not an option, for now ...
        final int numDots = 1;
        Resources resources = getResources();
        GameType gameType = gameIterations.getGameType();
        return gameType.createGame(resources, numDots);
    }
    
    private void saveDuration(Intent intent) {
        logTime("saveDuration:currTime");
        long duration = timer.getDuration();
        GameParameters.saveDuration(intent, duration);
    }
    
    private void saveQuery(Intent intent) {
        EditText et = getInputTextView();
        String inputText = et.getText().toString();
        
        GameParameters.saveResponse(intent, new Response(queryWord, inputText));
        GameParameters.saveGameIterations(intent, gameIterations);
    }

    private void saveMatching(Intent intent) {
        while (matching == null) {
            // waiting for getMatching() to finish; invoked by onCreate() ...
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
            }
        }
        GameParameters.saveMatching(intent, matching);
    }
}
