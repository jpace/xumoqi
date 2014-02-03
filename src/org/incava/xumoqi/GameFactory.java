package org.incava.xumoqi;

import android.content.res.Resources;

public class GameFactory {
	public static Game createGame(String gameType, Resources resources, int length, int nDots) {
		// String[] gameTypes = resources.getStringArray(R.array.game_types);
		
		if (gameType.contains("Starting")) {
			return new GameStartsWithDots(resources, length, nDots);
		}
		else if (gameType.contains("Random")) {
			return new GameRandomDots(resources, length, nDots);
		}
		else if (gameType.contains("Ending")) {
			return new GameEndsWithDots(resources, length, nDots);
		}
		return null;
	}
}
