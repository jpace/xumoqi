package org.incava.xumoqi;

import java.util.List;

public abstract class Game {
	private final int length;
	private final WordList wordList;
	
	public Game(WordList wordList, int length) {
		this.wordList = wordList;
		this.length = length;
	}
	
	public abstract String getQueryWord();

	public abstract List<String> getMatching(String queryString);
}
