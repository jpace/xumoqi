package org.incava.xumoqi;

import java.io.InputStream;
import java.util.List;
import java.util.Random;

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
	
	private String gameType = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent intent = getIntent();
		int length = intent.getIntExtra(MainActivity.WORD_LENGTH, 3);
		gameType = intent.getStringExtra(MainActivity.GAME_TYPE);
		
		int numDots = 1;
		
		Resources resources = getResources();
		Dictionary dict = Dictionary.getTWL(resources, length);
		WordList wordList = dict.getWordList(length);
		Game game = Game.createGame(gameType, numDots);
		String str = game.getQueryWord(wordList);
		
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
		
		TextView tv = getQueryTextView();
		tv.setText(str);
		EditText et = (EditText)findViewById(R.id.queryInput);
		et.setOnEditorActionListener(tveal);
	}
	
	public void onClickNext(View view) {
    	Intent intent = new Intent(this, ResultsActivity.class);
    	
    	TextView tv = getQueryTextView();
		String qstr = tv.getText().toString();
		
    	intent.putExtra(QUERY_STRING, qstr);
    	
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

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

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
