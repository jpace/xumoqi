package org.incava.xumoqi;

import android.content.res.Resources;

public class GameFactory {
	public static Game createGame(String gameType, Resources resources, int length, int nDots) {
		String[] gameTypes = resources.getStringArray(R.array.game_types);
		
		if (gameTypes[0].equals(gameType)) {
			return new GameStartsWithDots(resources, length, nDots);
		}
		else if (gameTypes[1].equals(gameType)) {
			return new GameRandomDots(resources, length, nDots);
		}
		else if (gameTypes[2].equals(gameType)) {
			return new GameEndsWithDots(resources, length, nDots);
		}
		return null;
	}
}
