package org.incava.xumoqi;

public abstract class GameDottedWords extends Game {
	private final int numDots;
	private final WordList wordList;
	private final int length;
	
	public GameDottedWords(WordList wordList, int length, int nDots) {
		this.wordList = wordList;
		this.length = length;
		this.numDots = nDots;
	}
	
	public abstract int getDotIndex();
	
	public Word getQueryWord() {
		String word = getRandomWord();
		int dotIdx = getDotIndex();
		return new Word(word, dotIdx);
	}

	protected int getNumDots() {
		return numDots;
	}
	
	protected String getRandomWord() {
		return wordList.getRandomWord();
	}
	
	protected int getLength() {
		return length;
	}
}
