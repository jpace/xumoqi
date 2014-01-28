package org.incava.xumoqi;

public abstract class GameDottedWords extends Game {
	private final int numDots;
	
	public GameDottedWords(WordList wordList, int length, int nDots) {
		super(wordList, length);
		this.numDots = nDots;
	}
	
	protected int getNumDots() {
		return numDots;
	}
	
	public String getQueryWord() {
		String word = getRandomWord();
		return getAsPattern(word);
	}

	public abstract String getAsPattern(String word);
}

