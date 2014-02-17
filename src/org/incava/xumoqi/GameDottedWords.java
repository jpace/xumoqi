package org.incava.xumoqi;

import java.util.ArrayList;

public abstract class GameDottedWords extends Game {
	private final int numDots;
	private final WordList wordList;
	
	public GameDottedWords(WordList wordList, int length, int nDots) {
		this.numDots = nDots;
		this.wordList = wordList;
	}
	
	public Word getQueryWord() {
		String word = getRandomWord();
		return new Word(getAsPattern(word));
	}

	public abstract String getAsPattern(String word);

	public ArrayList<String> getMatching(String queryString) {
		return wordList.getMatching(queryString);
	}
	
	protected int getNumDots() {
		return numDots;
	}
	
	protected String getRandomWord() {
		return wordList.getRandomWord();
	}
}
