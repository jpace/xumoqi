package org.incava.xumoqi;

import java.util.List;
import java.util.Random;

import android.util.Log;

public abstract class Game {
	public static Game createGame(String gameType, WordList wordList, int nDots) {
		Log.i("GAME", java.util.Arrays.asList(R.array.game_types).toString());

		if ("Ending with \u2026".equals(gameType)) {
			return new GameEndsWithDots(wordList, nDots);
		}
		else if ("Starting with \u2026".equals(gameType)) {
			return new GameStartsWithDots(wordList, nDots);
		}
		else if ("Random \u2026".equals(gameType)) {
			return new GameRandomDots(wordList, nDots);
		}
		return null;
	}
	
	private final int numDots;
	protected final WordList wordList;
	
	public Game(WordList wordList, int nDots) {
		this.wordList = wordList;
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
