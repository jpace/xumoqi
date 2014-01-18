package org.incava.xumoqi;

import android.content.Intent;

public class GameParams {
	private final int wordLength;
	private final String gameType;
	
	public GameParams(Intent intent) {
		String queryString = intent.getStringExtra(QueryActivity.QUERY_STRING);
		wordLength = queryString.length();
		gameType = intent.getStringExtra(MainActivity.GAME_TYPE);
	}

	public int getWordLength() {
		return wordLength;
	}

	public String getGameType() {
		return gameType;
	}
}
