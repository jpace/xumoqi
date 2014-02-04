package org.incava.xumoqi;

import java.util.List;

public abstract class GameDottedWords extends Game {
	private final int numDots;
	private final WordList wordList;
	
	public GameDottedWords(WordList wordList, int length, int nDots) {
		super(wordList, length);
		this.numDots = nDots;
		this.wordList = wordList;
	}
	
	public String getQueryWord() {
		String word = getRandomWord();
		return getAsPattern(word);
	}

	public abstract String getAsPattern(String word);

	public List<String> getMatching(String queryString) {
		return wordList.getMatching(queryString);
	}
	
	protected int getNumDots() {
		return numDots;
	}
	
	protected String getRandomWord() {
		return wordList.getRandomWord();
	}
}
