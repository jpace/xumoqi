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

import org.incava.xumoqi.games.Game;
import org.incava.xumoqi.games.GameFactory;
import org.incava.xumoqi.games.GameParams;
import org.incava.xumoqi.games.Query;
import org.incava.xumoqi.games.Results;
import org.incava.xumoqi.utils.Constants;
import org.incava.xumoqi.utils.Timer;
import org.incava.xumoqi.utils.Util;
import org.incava.xumoqi.words.Word;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
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
	private ArrayList<String> matching = null;
	private GameParams gameParams = null;
	private Word queryWord = null;
	private Timer timer = null;
	private Query query = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query);
		
		Intent intent = getIntent();
		
		gameParams = intent.getParcelableExtra(Constants.GAME_PARAMS);
		int length = gameParams.getWordLength();
		
		Results results = intent.getParcelableExtra(Constants.RESULTS);
		Util.log(getClass(), "results", results);
		
		// not an option, for now ...
		int numDots = 1;
		
		Game game = GameFactory.createGame(gameParams.getGameType(), length, numDots);
		query = game.createQuery();
		queryWord = query.getWord();
		
		fetchMatching(game, queryWord);
		
		setupEditText();
		
		TextView tv = getQueryTextView();
		String asQuery = game.getAsQuery(queryWord);
		tv.setText(asQuery);
		
		timer = new Timer();
		timer.done("onCreate");
	}
	
	protected void onStart() {
		timer.done("onStart");
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
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Timer t = new Timer("QUERY", "getMatching");
				matching = game.getMatching(queryWord);
				t.done();
			}
		});
		t.start();
	}
	
	public void onClickNext(View view) {
		timer.done("onClickNext");

		long duration = timer.getDuration();
		
    	Intent intent = new Intent(this, StatusActivity.class);
    	intent.putExtra(Constants.QUERY, query);
    	intent.putExtra(Constants.QUERY_WORD, queryWord);
    	intent.putExtra(Constants.DURATION, String.valueOf(duration));
    	
    	while (matching == null) {
    		// waiting for getMatching() to finish; invoked by onCreate() ...
    		try {
				Thread.sleep(100);
			}
    		catch (InterruptedException e) {
			}
    	}
		intent.putStringArrayListExtra(Constants.MATCHING, matching);
    	
    	EditText et = (EditText)findViewById(R.id.queryInput);
		String inputText = et.getText().toString();
		intent.putExtra(Constants.INPUT_STRING, inputText);

		intent.putExtra(Constants.GAME_PARAMS, gameParams);
		Util.log(getClass(), "gameParams", gameParams);

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

}
