package org.incava.xumoqi;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query);
		
		Intent intent = getIntent();
		
		gameParams = intent.getParcelableExtra(Constants.GAME_PARAMS);
		int length = gameParams.getWordLength();
		
		// not an option, for now ...
		int numDots = 1;
		
		Resources resources = getResources();
		Game game = GameFactory.createGame(gameParams.getGameType(), resources, length, numDots);
		queryWord = game.getQueryWord();
		
		fetchMatching(game, queryWord);
		
		setupEditText();
		
		TextView tv = getQueryTextView();
		String asQuery = queryWord.asQuery();
		tv.setText(asQuery);
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
    	Intent intent = new Intent(this, StatusActivity.class);
    	intent.putExtra(Constants.QUERY_WORD, queryWord);
    	
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
		Log.i("QUERY", "gameParams: " + gameParams);

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
