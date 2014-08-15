package org.incava.xumoqi.games;

import org.incava.xumoqi.query.QueryList;
import org.incava.xumoqi.utils.Constants;

import android.content.Intent;

public class GameParameters {
	public static QueryList getQueryList(Intent intent) {
		return intent.getParcelableExtra(Constants.QUERIES);
	}
	
	public static void saveQueryList(Intent intent, QueryList queryList) {
		intent.putExtra(Constants.QUERIES, queryList);
	}

	public static GameIterations getGameIterations(Intent intent) {
	    return intent.getParcelableExtra(Constants.GAME_ITERATIONS);
	}

	public static void saveGameIterations(Intent intent, GameIterations gameIterations) {
	    intent.putExtra(Constants.GAME_ITERATIONS, gameIterations);
	}
}
