package org.incava.xumoqi;

import java.util.List;

public abstract class Game {
	private final int length;
	private final WordList wordList;
	
	public Game(WordList wordList, int length) {
		this.wordList = wordList;
		this.length = length;
	}
	
	protected WordList getWordList() {
		return wordList;
	}
	
	public abstract String getQueryWord();

	protected String getRandomWord() {
		return wordList.getRandomWord();
	}

	public List<String> getMatching(String queryString) {
		return wordList.getMatching(queryString);
	}
}
