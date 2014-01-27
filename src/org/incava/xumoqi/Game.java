package org.incava.xumoqi;

import java.util.List;
import java.util.Random;

public abstract class Game {
	private final int numDots;
	private final int length;
	protected final WordList wordList;
	
	public Game(WordList wordList, int length, int nDots) {
		this.wordList = wordList;
		this.length = length;
		this.numDots = nDots;
	}
	
	protected int getNumDots() {
		return numDots;
	}
	
	public abstract String getQueryWord();

	protected String getRandomWord() {
		return wordList.getRandomWord();
	}

	public List<String> getMatching(String queryString) {
		return wordList.getMatching(queryString);
	}
}
