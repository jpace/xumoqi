package org.incava.xumoqi;

import java.util.ArrayList;

import android.content.res.Resources;

public class GameEndsWithDots extends GameDottedWords {
	private final WordList wordList;

	public GameEndsWithDots(Resources resources, int length, int nDots) {
		this(WordLists.getWordList(resources, length), length, nDots);
	}
	
	public GameEndsWithDots(WordList wordList, int length, int nDots) {
		super(wordList, length, nDots);
		this.wordList = wordList;
	}
	
	private String getSubstring(String str) {
		int nDots = getNumDots();
		return str.substring(0, str.length() - nDots);
	}

	public String getAsPattern(String word) {
		int nDots = getNumDots();
		return getSubstring(word) + Util.repeat(".", nDots);
	}

	public ArrayList<String> getMatching(String queryString) {
		String str = getSubstring(queryString);
		return wordList.getStartingWith(str);
	}
}
