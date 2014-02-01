package org.incava.xumoqi;

import java.util.List;

import android.content.res.Resources;

public class GameEndsWithDots extends GameDottedWords {
	public GameEndsWithDots(Resources resources, int length, int nDots) {
		super(Dictionary.getWordList(resources, length), length, nDots);
	}
	
	private String getSubstring(String str) {
		int nDots = getNumDots();
		return str.substring(0, str.length() - nDots);
	}

	public String getAsPattern(String word) {
		int nDots = getNumDots();
		return getSubstring(word) + Util.repeat(".", nDots);
	}

	public List<String> getMatching(String queryString) {
		String str = getSubstring(queryString);
		WordList wordList = getWordList();
		return wordList.getStartingWith(str);
	}
}
