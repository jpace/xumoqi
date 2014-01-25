package org.incava.xumoqi;

import android.util.Log;

public class GameFactory {
	public static Game createGame(String gameType, WordList wordList, int nDots) {
		Log.i("GAME", java.util.Arrays.asList(R.array.game_types).toString());
		
		if ("Ending with \u2026".equals(gameType)) {
			return new GameEndsWithDots(wordList, 0, nDots);
		}
		else if ("Starting with \u2026".equals(gameType)) {
			return new GameStartsWithDots(wordList, 0, nDots);
		}
		else if ("Random \u2026".equals(gameType)) {
			return new GameRandomDots(wordList, 0, nDots);
		}
		return null;
	}
}
