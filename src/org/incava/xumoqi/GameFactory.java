package org.incava.xumoqi;

import android.content.res.Resources;
import android.util.Log;

public class GameFactory {
	public static Game createGame(String gameType, Resources resources, int length, int nDots) {
		WordList wordList = Dictionary.getWordList(resources, length);
		
		Log.i("GAME", java.util.Arrays.asList(R.array.game_types).toString());
		
		if ("Ending with \u2026".equals(gameType)) {
			return new GameEndsWithDots(wordList, length, nDots);
		}
		else if ("Starting with \u2026".equals(gameType)) {
			return new GameStartsWithDots(wordList, length, nDots);
		}
		else if ("Random \u2026".equals(gameType)) {
			return new GameRandomDots(wordList, length, nDots);
		}
		return null;
	}
}
