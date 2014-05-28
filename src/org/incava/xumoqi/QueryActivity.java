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
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.incava.xumoqi.games.Game;
import org.incava.xumoqi.games.GameFactory;
import org.incava.xumoqi.games.GameParams;
import org.incava.xumoqi.games.Query;
import org.incava.xumoqi.games.QueryList;
import org.incava.xumoqi.utils.Constants;
import org.incava.xumoqi.utils.Timer;
import org.incava.xumoqi.utils.Util;
import org.incava.xumoqi.words.Word;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.support.v4.app.NavUtils;

public class QueryActivity extends Activity {
	private static final Random random = new Random();

    private ArrayList<String> matching = null;
    private GameParams gameParams = null;
    private Word queryWord = null;
    private Timer timer = null;
    private QueryList queries = null;
    private int queryIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        
        String queryStr = getNextQuery();
        
        setupEditText();
        
        TextView tv = getQueryTextView();
        tv.setText(queryStr);
        
        timer = new Timer();
        // timer.done("onCreate");
    }
    
    protected void onStart() {
        // timer.done("onStart");
        super.onStart();
    }
    
    private void setupEditText() {
        OnEditorActionListener tveal = new OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEND || 
                        (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                        onClickNext(null);
                        return true;
                    }
                    return false;
                }
            };
        EditText et = (EditText)findViewById(R.id.queryInput);
        et.setOnEditorActionListener(tveal);
        et.requestFocus();
        
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
    
    private void fetchMatching(final Game game, final Word queryWord) {
        Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // Timer timer = new Timer("QUERY", "getMatching");
                    matching = game.getMatching(queryWord);
                    log("fetchMatching.matching", matching);
                    // timer.done();
                }
            });
        thread.start();
    }
    
    public void onClickNext(View view) {
        // timer.done("onClickNext");

        long duration = timer.getDuration();
        
        Intent intent = new Intent(this, StatusActivity.class);

        intent.putExtra(Constants.DURATION, String.valueOf(duration));
        
        EditText et = (EditText)findViewById(R.id.queryInput);
        String inputText = et.getText().toString();
        intent.putExtra(Constants.INPUT_STRING, inputText);
        intent.putExtra(Constants.GAME_PARAMS, gameParams);
        intent.putExtra(Constants.QUERIES, queries);
        
        intent.putExtra(Constants.QUERY_INDEX, queryIndex);

        while (matching == null) {
            // waiting for getMatching() to finish; invoked by onCreate() ...
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
            }
        }
        intent.putStringArrayListExtra(Constants.MATCHING, matching);
        
        startActivity(intent);
    }
    
    public void onClickRestart(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private TextView getQueryTextView() {
        return (TextView)findViewById(R.id.queryText);
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
        Intent intent = getIntent();

        gameParams = intent.getParcelableExtra(Constants.GAME_PARAMS);
        int length = gameParams.getWordLength();
        
        queries = intent.getParcelableExtra(Constants.QUERIES);
        // log("next.queries", queries.inspect());
        
        Map<Integer, ArrayList<Query>> queriesByScore = new TreeMap<Integer, ArrayList<Query>>();
        
        for (Query q : queries.getQueries()) {
       		int score = q.getScore();
   			ArrayList<Query> current = queriesByScore.get(score);
   			if (current == null) {
   				current = new ArrayList<Query>();
   				queriesByScore.put(score, current);
   			}
       		current.add(q);
        }
        
        log("queriesByScore", queriesByScore);

		// not an option, for now ...
		final int numDots = 1;
   
		Game game = GameFactory.createGame(gameParams.getGameType(), length, numDots);
		// log("game", game);

    	// log("next.q.badQueries", badQueries);
    	
    	Query query = null;
    	
    	log("next", "****************************************");
    	
    	for (Integer score : queriesByScore.keySet()) {
    		log("score", score);
    		int rnd = random.nextInt(100);
    		log("next.rnd", rnd);
    		if (rnd > score) {
    			ArrayList<Query> forScore = queriesByScore.get(score);
    			log("***** next.forScore", forScore);
    			int sz = forScore.size();
    			log("***** next.sz", sz);
    			int rIdx = random.nextInt(sz);
    			log("***** next.rIdx", rIdx);
        		query = forScore.get(rIdx);
    			log("***** next.query", query);
        		break;
    		}
    	}
    	
    	if (query == null) {
    		queryWord = game.getQueryWord();
        	log("next(NEW).queryWord", queryWord);
    		query = new Query(queryWord);
        	log("next(NEW).query", query);
    		queries.addQuery(query);
        	queryIndex = queries.size() - 1;
    	}
    	else {
    		queryIndex = queries.getQueries().indexOf(query);
        	log("next(RANDOM).queryIndex", queryIndex);
        	
    		queryWord = query.getWord();
        	log("next(RANDOM).queryWord", queryWord);
        	log("next(RANDOM).query", query);
    	}
        
        fetchMatching(game, queryWord);

        return game.getAsQuery(queryWord);
    }
    
    private void log(String what, Object obj) {
    	Util.log(getClass(), what, obj);
    }
    
    private void log(String what, String str) {
    	Util.log(getClass(), what, str);
    }
}
