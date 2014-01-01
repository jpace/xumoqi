package org.incava.xumoqi;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.text.Html;

public class ResultsActivity extends Activity {
	private int wordLength = 3;
	private String gameType = null;
	
	private final Map<Matches.StatusType, String> statusToFontColor;
	
	public ResultsActivity() {
		statusToFontColor = new HashMap<Matches.StatusType, String>();
		statusToFontColor.put(Matches.StatusType.CORRECT, "00aa00");
		statusToFontColor.put(Matches.StatusType.INVALID, "aa0000");
		statusToFontColor.put(Matches.StatusType.MISSED,  "aaaa00");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent intent = getIntent();
		String queryString = intent.getStringExtra(QueryActivity.QUERY_STRING);
		wordLength = queryString.length();
		
		int numDots = 1;
		
		gameType = intent.getStringExtra(MainActivity.GAME_TYPE);
		Game game = Game.createGame(gameType, numDots);
		
		Resources resources = getResources();
		Dictionary dict = Dictionary.getTWL(resources, wordLength);
		WordList wordList = dict.getWordList(wordLength);
		
		List<String> matching = game.getMatching(wordList, queryString);
		Log.i("RESULTS", "matching: " + matching);
		
		String inputString = intent.getStringExtra(QueryActivity.INPUT_STRING);
		Matches matchStatus = new Matches(matching, inputString);

		StringBuilder sb = new StringBuilder();
		
		Set<String> allWords = matchStatus.getAllWords();
		for (String word : allWords) {
			Matches.StatusType st = matchStatus.getStatus(word);
			String color = statusToFontColor.get(st);
			sb.append("<font color=\"#" + color + "\">" + word + "</font><br/>");
		}
		
		TextView tv = (TextView)findViewById(R.id.resultsText);
    	tv.setText(Html.fromHtml(sb.toString()));
	}
	
	public void onClickNext(View view) {
    	Intent intent = new Intent(this, QueryActivity.class);
    	intent.putExtra(MainActivity.WORD_LENGTH, wordLength);
    	intent.putExtra(MainActivity.GAME_TYPE, gameType);
    	startActivity(intent);
	}

	public void onClickQuit(View view) {
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
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
		getMenuInflater().inflate(R.menu.results, menu);
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
