package org.incava.xumoqi;

public abstract class GameDottedWords extends Game {
	public GameDottedWords(WordList wordList, int length, int nDots) {
		super(wordList, length, nDots);
	}
	
	public String getQueryWord() {
		String word = getRandomWord();
		return getAsPattern(word);
	}

	public abstract String getAsPattern(String word);
}

