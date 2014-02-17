package org.incava.xumoqi;

import java.util.ArrayList;

public abstract class Game {
	public abstract Word getQueryWord();

	public abstract ArrayList<String> getMatching(String queryString);
}
