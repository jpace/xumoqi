package org.incava.xumoqi;

import java.util.List;

public class GameEndsWithDots extends GameDottedWords {
	public GameEndsWithDots(WordList wordList, int length, int nDots) {
		super(wordList, length, nDots);
	}
	
	private String getSubstring(String str) {
		int nDots = getNumDots();
		return str.substring(0, str.length() - nDots);
	}

	public String getAsPattern(String word) {
		int nDots = getNumDots();
		return getSubstring(word) + Util.repeat(".", nDots);
	}

	public List<String> getMatching(WordList wordList, String queryString) {
		String str = getSubstring(queryString);
		return wordList.getStartingWith(str);
	}
}
