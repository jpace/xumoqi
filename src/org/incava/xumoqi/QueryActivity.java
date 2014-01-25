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
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class QueryActivity extends Activity {
	public final static String QUERY_STRING = "queryString";
	public final static String INPUT_STRING = "inputString";
	public final static String MATCHING = "matching";
	
	private String gameType = null;
	private String queryString = null;
	private ArrayList<String> matching = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query);
		
		Intent intent = getIntent();
		int length = intent.getIntExtra(MainActivity.WORD_LENGTH, 3);
		gameType = intent.getStringExtra(MainActivity.GAME_TYPE);
	
		// not an option, for now ...
		int numDots = 1;
		
		Game game = getGame(length, numDots);
		queryString = game.getQueryWord();
		
		getMatching(game);
		
		addSendListener();
		
		TextView tv = getQueryTextView();
		tv.setText(queryString);
	}
	
	private void addSendListener() {
		TextView.OnEditorActionListener tveal = new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEND) {
					onClickNext(null);
					return true;
				}
				return false;
			}
		};
		EditText et = (EditText)findViewById(R.id.queryInput);
		et.setOnEditorActionListener(tveal);
	}
	
	private void getMatching(final Game game) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				matching = new ArrayList<String>(game.getMatching(queryString));
				Log.i("QUERY", "matching: " + matching);
			}
		});
		t.start();
	}
	
	private Game getGame(int length, int numDots) { 
		Resources resources = getResources();
		WordList wordList = Dictionary.getWordList(resources, length);
		return GameFactory.createGame(gameType, wordList, numDots);
	}
	
	public void onClickNext(View view) {
    	Intent intent = new Intent(this, StatusActivity.class);
    	intent.putExtra(QUERY_STRING, queryString);
    	while (matching == null) {
    		try {
				Thread.sleep(100);
			}
    		catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
		intent.putStringArrayListExtra(MATCHING, matching);
    	
    	EditText et = (EditText)findViewById(R.id.queryInput);
		String inputText = et.getText().toString();
		intent.putExtra(INPUT_STRING, inputText);
		
		intent.putExtra(MainActivity.GAME_TYPE, gameType);

    	startActivity(intent);
	}
	
	public void onClickQuit(View view) {
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
