package org.incava.xumoqi;

import java.util.List;
import java.util.Random;

import android.util.Log;

public abstract class Game {
	public static Game createGame(String gameType, int nDots) {
		if ("Ending with \u2026".equals(gameType)) {
			return new GameEndsWithDots(nDots);
		}
		else if ("Starting with \u2026".equals(gameType)) {
			return new GameStartsWithDots(nDots);
		}
		else if ("Random \u2026".equals(gameType)) {
			return new GameRandomDots(nDots);
		}
		return null;
	}
	
	private final int numDots;
	
	public Game(int nDots) {
		this.numDots = nDots;
	}
	
	protected int getNumDots() {
		return numDots;
	}
	
	public String getQueryWord(WordList wordList) {
		String word = getRandomWord(wordList);
		return getAsPattern(word);
	}

	protected String getRandomWord(WordList wordList) {
		List<String> words = wordList.getWords();
		Random random = new Random();
		int idx = random.nextInt(words.size());
		return words.get(idx);
	}

	public abstract String getAsPattern(String word);

	public List<String> getMatching(WordList wordList, String queryString) {
		return wordList.getMatching(queryString);
	}
}
