package org.incava.xumoqi;

import android.content.res.Resources;

public class GameFactory {
	public static Game createGame(String gameType, Resources resources, int length, int nDots) {
		if (gameType.contains("Starting")) {
			return new GameStartsWithDots(resources, length, nDots);
		}
		else if (gameType.contains("Random")) {
			return new GameRandomDots(resources, length, nDots);
		}
		else if (gameType.contains("Ending")) {
			return new GameEndsWithDots(resources, length, nDots);
		}
		else if (gameType.contains("to-make")) {
			return new GameNToNPlusOne(resources, length);
		}
		return null;
	}
}
