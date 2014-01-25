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
	
	public String getQueryWord() {
		String word = getRandomWord();
		return getAsPattern(word);
	}

	protected String getRandomWord() {
		List<String> words = wordList.getWords();
		Random random = new Random();
		int idx = random.nextInt(words.size());
		return words.get(idx);
	}

	public abstract String getAsPattern(String word);

	public List<String> getMatching(String queryString) {
		return wordList.getMatching(queryString);
	}
}
